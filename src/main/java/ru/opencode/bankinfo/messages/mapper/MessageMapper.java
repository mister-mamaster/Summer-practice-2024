package ru.opencode.bankinfo.messages.mapper;

import ru.opencode.bankinfo.messages.dto.MessageDTO;
import ru.opencode.bankinfo.messages.dto.subDTO.*;
import ru.opencode.bankinfo.messages.entity.*;
import ru.opencode.bankinfo.messages.entity.subClass.AccRstr;
import ru.opencode.bankinfo.messages.entity.subClass.Rstr;

import java.util.ArrayList;
import java.util.List;

public class MessageMapper {

    public EMessageEntity DTOToMessage(MessageDTO dto){
        EMessageEntity message = new EMessageEntity(dto.getEdNo(), dto.getEdDate(), dto.getEdAuthor(),
                dto.getCreationReason(), dto.getCreationTime(), dto.getInfoTypeCode(), dto.getBusinessDay());
        message.setEMessageName(dto.getEMessageName());
        message.setEdReceiver(dto.getEdReceiver());

        return message;
    };

    public Entry DTOToEntry(EntryDTO dto, Long messageId){
        Entry entry = new Entry(
                messageId,
                dto.getBIC(),
                DTOToParticipant(dto.getParticipantInfoDTO()));

        if(dto.getAccounts() != null) {
            entry.setAccounts(dto.getAccounts()
                    .stream()
                    .map(a -> DTOToAccount(a, entry)).toList());
        }

        if(dto.getSwbics() != null) {
            entry.setSwbics(dto.getSwbics()
                    .stream()
                    .map(s -> DTOToSWBIC(s, entry)).toList());
        }

        entry.setChangeType(dto.getChangeType());
        setEntryForRstrs(entry, entry.getParticipantInfo().getRstrList());

        return entry;
    }

    public SWBIC DTOToSWBIC(SWBICDTO dto, Entry entry){
        return new SWBIC(entry, dto.getSWBIC(), dto.getDefaultSWBIC());
    }

    public Account DTOToAccount(AccountDTO dto, Entry entry){
        Account account = new Account(entry,
                dto.getAccount(),
                dto.getRegulationAccountType(),
                dto.getAccountCBRBIC(),
                dto.getDateIn());

        account.setAccountStatus(dto.getAccountStatus());
        account.setDateOut(dto.getDateOut());
        account.setControlKey(dto.getControlKey());
        account.setAccRstrList(DTOToAccRstrList(dto.getAccRstrLists(), account));

        return account;
    }

    public Participant DTOToParticipant(ParticipantInfoDTO dto){
        Participant participant = new Participant(
                dto.getNameP(),
                dto.getRgn(),
                dto.getDateIn(),
                dto.getPtType(),
                dto.getSrvcs(),
                dto.getXchType(),
                dto.getUID());

        participant.setAdr(dto.getAdr());
        participant.setParticipantStatus(dto.getParticipantStatus());
        participant.setNnp(dto.getNnp());
        participant.setInd(dto.getInd());
        participant.setDateOut(dto.getDateOut());
        participant.setCntrCd(dto.getCntrCd());
        participant.setRegN(dto.getRegN());
        participant.setTnp(dto.getTnp());
        participant.setPrntBic(dto.getPrntBic());
        participant.setRstrList(DTOToRstrList(dto.getRstrList(), participant));

        return participant;
    }

    public List<AccRstr> DTOToAccRstrList(List<AccRstrListDto> dtos, Account account){
        List<AccRstr> list = new ArrayList<>();

        if(dtos != null) {
            dtos.stream().map(d -> new AccRstr(d.getAccRstr(),
                    d.getAccRstrDate(),
                    d.getSuccessorBIC(), account)).forEach(list::add);
        }

        return list;
    }

    public List<Rstr> DTOToRstrList(List<RstrListDto> dtos, Participant participant){
        List<Rstr> list = new ArrayList<>();

        if(dtos != null) {
            dtos.stream().map(d -> new Rstr(null,
                    d.getAccRstr(),
                    d.getAccRstrDate())).forEach(list::add);
        }

        return list;
    }

    private void setEntryForRstrs(Entry entry, List<Rstr> rstrs){
        rstrs.forEach(r -> r.setEntry(entry));
    }
}
