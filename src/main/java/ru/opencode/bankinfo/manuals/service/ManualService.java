package ru.opencode.bankinfo.manuals.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.opencode.bankinfo.manuals.dto.ManualCreationDTO;
import ru.opencode.bankinfo.config.PaginationConfig;
import ru.opencode.bankinfo.manuals.entity.Info;
import ru.opencode.bankinfo.manuals.entity.Manual;
import ru.opencode.bankinfo.manuals.exception.ManualNotFoundException;
import ru.opencode.bankinfo.manuals.mapper.ManualMapper;
import ru.opencode.bankinfo.manuals.repository.ManualRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManualService {
    private ManualRepository manualRepository;

    private ManualMapper manualMapper;
    private InfoService infoService;

    @Autowired
    public ManualService(ManualRepository manualRepository, ManualMapper manualMapper, InfoService infoService) {
        this.manualRepository = manualRepository;
        this.manualMapper = manualMapper;
        this.infoService = infoService;
    }

    public Manual getManual(Long id) {
        return manualRepository.findById(id).orElseThrow(() -> new ManualNotFoundException("Manual with id: " + id + " not found."));
    }

    public List<Manual> getAllManuals() {
        return manualRepository.findAll();
    }

    public List<Object> getManualsByInfoId(Long infoId, Boolean isDeleted, String code, String description, Integer pageNo, Integer pageSize) {
        infoService.getInfo(infoId);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id"));
        Page<Manual> manualPage = manualRepository.findByInfo_idAndIsDeletedEqualsAndCodeContainsAndDescriptionContains(
                infoId,
                isDeleted,
                code,
                description,
                pageable
        );
        List<Object> manualPageWithPaginateConfig = new ArrayList<>();
        manualPageWithPaginateConfig.add(manualPage.getContent());
        manualPageWithPaginateConfig.add(new PaginationConfig(manualPage.getTotalPages(),manualPage.getTotalElements()));
        return manualPageWithPaginateConfig;
    }

    public Long addManual(Manual manual, Long infoId) {
        Info info = infoService.getInfo(infoId);
        manual.setInfo(info);
        Manual savedManual = manualRepository.save(manual);
        return savedManual.getId();
    }

    public void updateManual(Long id, ManualCreationDTO manualCreationDTO) {
        Manual manual = getManual(id);
        manualMapper.updateManualFromManualCreationDTO(manualCreationDTO, manual);
        manualRepository.save(manual);
    }

    public void deleteManual(Long id) {
        Manual manual = getManual(id);
        manual.setIsDeleted(true);
        manualRepository.save(manual);
    }

    public void restoreManual(Long id) {
        Manual manual = getManual(id);
        manual.setIsDeleted(false);
        manualRepository.save(manual);
    }


}
