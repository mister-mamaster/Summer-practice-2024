package ru.opencode.bankinfo.messages.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.opencode.bankinfo.config.PaginationConfig;

import ru.opencode.bankinfo.exception.InvalidParametersException;
import ru.opencode.bankinfo.exception.NotFoundException;
import ru.opencode.bankinfo.messages.dto.MessageDTO;
import ru.opencode.bankinfo.messages.dto.subDTO.EMessageNameDTO;
import ru.opencode.bankinfo.messages.dto.subDTO.EntryDTO;
import ru.opencode.bankinfo.messages.entity.Account;
import ru.opencode.bankinfo.messages.entity.EMessageEntity;
import ru.opencode.bankinfo.messages.entity.Entry;
import ru.opencode.bankinfo.messages.entity.SWBIC;
import ru.opencode.bankinfo.messages.entity.subClass.AccRstr;
import ru.opencode.bankinfo.messages.entity.subClass.Rstr;
import ru.opencode.bankinfo.messages.exception.MessageConflictException;
import ru.opencode.bankinfo.messages.mapper.MessageMapper;
import ru.opencode.bankinfo.messages.repository.*;
import ru.opencode.bankinfo.parser.XmlToPOJO;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class MessageService {

    @Autowired
    private EntryRepository entryRepo;

    @Autowired
    private MessageRepository messageRepo;
    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private SWBICSRepository swbicsRepo;

    @Autowired
    private RstrRepository rstrRepo;

    @Autowired
    private AccRstrRepository accRstrRepo;

    private final MessageMapper mapper = new MessageMapper();

    public EMessageEntity getMessageById(Long id) {
        return messageRepo.findById(id).orElseThrow(() -> new NotFoundException("Message with id:" + id + " not found"));
    }

    public List<Object> getMessages(String messageName, LocalDateTime localDateTimeStart,LocalDateTime localDateTimeEnd,Boolean isDeleted, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id"));
        Page<EMessageEntity> eMessagePage = messageRepo.findAllByeMessageNameContainsAndCreateDateTimeGreaterThanEqualAndCreateDateTimeLessThanEqualAndIsDeletedEquals(
                messageName,
                localDateTimeStart,
                localDateTimeEnd,
                isDeleted,
                pageable
        );
        List<Object> eMessagePageWithPaginateConfig = new ArrayList<>();
        eMessagePageWithPaginateConfig.add(eMessagePage.getContent());
        eMessagePageWithPaginateConfig.add(new PaginationConfig(eMessagePage.getTotalPages(),eMessagePage.getTotalElements()));
        return eMessagePageWithPaginateConfig;
    }


    public EMessageEntity createMessage(MessageDTO dto) {
        try {

            EMessageEntity message = mapper.DTOToMessage(dto);
            messageRepo.save(message);
            List<Entry> entries = createEntriesForMessage(dto, message);
            entryRepo.saveAll(entries);

            List<Account> accounts = new ArrayList<>();
            List<SWBIC> swbics = new ArrayList<>();
            List<Rstr> rstrs = new ArrayList<>();
            List<AccRstr> accRstrs = new ArrayList<>();
            for (Entry entry : entries) {
                if (entry.getAccounts() != null) {
                    accounts.addAll(entry.getAccounts());
                    for(Account account: entry.getAccounts()) {
                        if(account.getAccRstrList() != null &&
                                !account.getAccRstrList().isEmpty()) {
                            accRstrs.addAll(account.getAccRstrList());
                        }
                    }
                }
                if (entry.getSwbics() != null) {
                    swbics.addAll(entry.getSwbics());
                }
                if(entry.getParticipantInfo().getRstrList() != null &&
                        !entry.getParticipantInfo().getRstrList().isEmpty()) {
                    rstrs.addAll(entry.getParticipantInfo().getRstrList());
                }
            }
            accountRepo.saveAll(accounts);
            swbicsRepo.saveAll(swbics);
            rstrRepo.saveAll(rstrs);
            accRstrRepo.saveAll(accRstrs);

            fillMessage(entries, message);
            messageRepo.save(message);
            return message;
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new InvalidParametersException("Invalid parameters for creating message");
        }
    }

    public void updateMessageName(Long id, EMessageNameDTO eMessageNameDTO) {
        EMessageEntity message = getMessageById(id);
        message.setEMessageName(eMessageNameDTO.getName());
        messageRepo.save(message);
    }

    public void deleteMessage(Long id) {
        EMessageEntity message = getMessageById(id);
        message.setIsDeleted(true);
        messageRepo.save(message);
    }

    public void restoreMessage(Long id) {
        EMessageEntity message = getMessageById(id);
        message.setIsDeleted(false);
        messageRepo.save(message);
    }

    public Entry getEntry(Long id) {
        return entryRepo.findById(id).orElseThrow(() -> new NotFoundException("Entry not found"));
    }

    public List<Object> getEntriesByEMessageId(Long emessageId, Byte participantType, String nameP, String bic , Integer pageNo, Integer pageSize) {
        getMessageById(emessageId);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id"));
        Page<Entry> entryPage;
        if (participantType != null){
        entryPage = entryRepo.findEntryByMessageIdAndParticipantInfo_PtTypeEqualsAndParticipantInfo_NamePContainsAndBICContains(
                emessageId,
                participantType,
                nameP,
                bic,
                pageable
        );}
        else {
            entryPage = entryRepo.findEntryByMessageIdAndParticipantInfo_NamePContainsAndBICContains(
                    emessageId,
                    nameP,
                    bic,
                    pageable
            );
        }
        List<Object> entryPageWithPaginateConfig = new ArrayList<>();
        entryPageWithPaginateConfig.add(entryPage.getContent());
        entryPageWithPaginateConfig.add(new PaginationConfig(entryPage.getTotalPages(),entryPage.getTotalElements()));
        return entryPageWithPaginateConfig;
    }

    public EMessageEntity createEMessageByXml(MultipartFile multifile) throws JAXBException, IOException, ParserConfigurationException, SAXException {
        File file = XmlToPOJO.convertMultipartFileToFile(multifile);
        return createEMessageByDocument(XmlToPOJO.fileToDocument(file), Path.of(file.getPath())) ;
    }

    public EMessageEntity createEMessageByDocument(Document document, Path path) throws JAXBException, IOException, ParserConfigurationException, SAXException {
        MessageDTO dto = XmlToPOJO.xmlToPOJO(XmlToPOJO.documentToString(document));
        dto.setEMessageName(path.getFileName().toString());
        XmlToPOJO.deleteFile(path);
        return createMessage(dto);
    }
    public Boolean isMessageWithDate(LocalDate date){
        return messageRepo.existsByBusinessDayEqualsAndIsDeletedEquals(date, false);
    }
    public EMessageEntity addEMessageFromBank() throws JAXBException, IOException, ParserConfigurationException, SAXException{
        LocalDate localDate = LocalDate.now();
        XmlToPOJO.downoloadXML(localDate);
        Path path = Path.of(String.format("backend/src/main/resources/%s_ED807_full.xml", XmlToPOJO.getFormattedDate(localDate)));

        if (isMessageWithDate(localDate)){
            XmlToPOJO.deleteFile(path);
            throw new MessageConflictException("Message from Bank is actual, and updated today!");
        }
        Document document = XmlToPOJO.getDocument(path.toString());

        return createEMessageByDocument(document,path);

    }

    private List<Entry> createEntriesForMessage(MessageDTO dto, EMessageEntity message) {
        Set<EntryDTO> entriesDTO = dto.getEntries();
        List<Entry> entries = new LinkedList<>();

        entriesDTO.stream().map(d -> mapper.DTOToEntry(d, message.getId())).forEach(entries::add);

            return entries;
    }

    private void fillMessage(List<Entry> entries, EMessageEntity message) {
        message.setEntriesId(entries.stream().map(Entry::getId).toList());
    }
}
