package ru.opencode.bankinfo.manuals.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.opencode.bankinfo.manuals.dto.InfoCreationDTO;
import ru.opencode.bankinfo.util.PaginatedResponseDTO;
import ru.opencode.bankinfo.manuals.entity.Info;
import ru.opencode.bankinfo.manuals.mapper.InfoMapper;
import ru.opencode.bankinfo.manuals.service.InfoService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/info")
public class InfoController {

    private InfoService infoService;
    private InfoMapper infoMapper;

    @Autowired
    public InfoController(InfoService infoService, InfoMapper infoMapper) {
        this.infoService = infoService;
        this.infoMapper = infoMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public PaginatedResponseDTO getAllInfo(@RequestParam(defaultValue = "1", name = "page") Integer pageNo,
                                 @RequestParam(defaultValue = "10", name = "size") Integer pageSize,
                                 @RequestParam(defaultValue = "", name = "name") String name,
                                 @RequestParam(defaultValue = "false", name = "deleted") Boolean isDeleted) {

        List<Object> infoPageWithPaginateConfig = infoService.getAllInfo(name, isDeleted, pageNo,pageSize);
        return new PaginatedResponseDTO(infoPageWithPaginateConfig.get(0), infoPageWithPaginateConfig.get(1));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long createInfo(@Valid @RequestBody InfoCreationDTO infoCreationDTO) {
        Info info = infoMapper.infoCreationDTOToInfo(infoCreationDTO);
        return infoService.createInfo(info);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInfo(@PathVariable(value = "id") Long id, @Valid @RequestBody InfoCreationDTO infoCreationDTO) {
        infoService.updateInfo(id, infoCreationDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInfo(@PathVariable(value = "id") Long id) {
        infoService.deleteInfo(id);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void restoreInfo(@PathVariable(value = "id") Long id) {
        infoService.restoreInfo(id);
    }
}
