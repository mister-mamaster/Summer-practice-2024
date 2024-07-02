package ru.opencode.bankinfo.messages.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import ru.opencode.bankinfo.util.PaginatedResponseDTO;
import ru.opencode.bankinfo.messages.dto.subDTO.EMessageNameDTO;
import ru.opencode.bankinfo.messages.entity.EMessageEntity;
import ru.opencode.bankinfo.messages.service.MessageService;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/emessages")
public class MessageController {

    @Autowired
    private MessageService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PaginatedResponseDTO getManualsByInfoId(@RequestParam(defaultValue = "1", name = "page") Integer pageNo,
                                                   @RequestParam(defaultValue = "10", name = "size") Integer pageSize,
                                                   @RequestParam(defaultValue = "", name = "emessage_name") String messageName,
                                                   @RequestParam(defaultValue = "-99999999-01-01T00:00:00", name = "date_start")LocalDateTime localDateTimeStart,
                                                   @RequestParam(defaultValue = "+99999999-12-31T23:59:59.999999999", name = "date_end")LocalDateTime localDateTimeEnd,
                                                   @RequestParam(defaultValue = "false", name = "deleted") Boolean isDeleted) {

        List<Object> eMessagePageWithPaginateConfig = service.getMessages(messageName, localDateTimeStart, localDateTimeEnd, isDeleted, pageNo, pageSize);
        return new PaginatedResponseDTO(eMessagePageWithPaginateConfig.get(0), eMessagePageWithPaginateConfig.get(1));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}")
    public void updateMessageName
            (
                    @PathVariable @Min(1) Long id,
                    @RequestBody @Valid EMessageNameDTO eMessageNameDTO
            ) {
        service.updateMessageName(id, eMessageNameDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteMessage(@PathVariable @Min(1) Long id) {
        service.deleteMessage(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{id}")
    public void restoreMessage(@PathVariable @Min(1) Long id) {
        service.restoreMessage(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/bics")
    public PaginatedResponseDTO getEntries(@PathVariable(value = "id") @Min(1) Long emessageId,
                                           @RequestParam(defaultValue = "1", name = "page") Integer pageNo,
                                           @RequestParam(defaultValue = "10", name = "size") Integer pageSize,
                                           @RequestParam(name = "participant_type", required = false) Byte participantType,
                                           @RequestParam(defaultValue = "", name = "name_p") String nameP,
                                           @RequestParam(defaultValue = "", name = "bic") String bic) {

        List<Object> entryPageWithPaginateConfig = service.getEntriesByEMessageId(emessageId, participantType, nameP, bic, pageNo, pageSize);
        return new PaginatedResponseDTO(entryPageWithPaginateConfig.get(0), entryPageWithPaginateConfig.get(1));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/xml")
    public EMessageEntity createMessage(
            @RequestPart("file") MultipartFile file
    ) throws JAXBException, IOException, ParserConfigurationException, SAXException {
        return service.createEMessageByXml(file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EMessageEntity createEMessageFromBank() throws JAXBException, IOException, ParserConfigurationException, SAXException {
        return service.addEMessageFromBank();
    }
}
