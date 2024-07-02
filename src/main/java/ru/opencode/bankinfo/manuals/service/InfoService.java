package ru.opencode.bankinfo.manuals.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.opencode.bankinfo.manuals.dto.InfoCreationDTO;
import ru.opencode.bankinfo.config.PaginationConfig;
import ru.opencode.bankinfo.manuals.entity.Info;
import ru.opencode.bankinfo.manuals.entity.Manual;
import ru.opencode.bankinfo.manuals.exception.InfoNotFoundException;
import ru.opencode.bankinfo.manuals.mapper.InfoMapper;
import ru.opencode.bankinfo.manuals.repository.InfoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfoService {
    private InfoRepository infoRepository;
    private InfoMapper infoMapper;

    @Autowired
    public InfoService(InfoRepository infoRepository, InfoMapper infoMapper) {
        this.infoRepository = infoRepository;
        this.infoMapper = infoMapper;
    }

    public Info getInfo(Long id) {
        return infoRepository.findById(id).orElseThrow(() -> new InfoNotFoundException("Info with id: " + id + " not found."));
    }

    public List<Object> getAllInfo(String name, Boolean isDeleted, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id"));
        Page<Info> infoPage = infoRepository.findByIsDeletedEqualsAndNameContains(isDeleted, name, pageable);

        List<Object> infoPageWithPaginateConfig = new ArrayList<>();
        infoPageWithPaginateConfig.add(infoPage.getContent());
        infoPageWithPaginateConfig.add(new PaginationConfig(infoPage.getTotalPages(),infoPage.getTotalElements()));
        return infoPageWithPaginateConfig;
    }

    public Long createInfo(Info info) {
       Info savedInfo = infoRepository.save(info);
       return savedInfo.getId();
    }

    public void updateInfo(Long id, InfoCreationDTO infoCreationDTO) {
        Info info = getInfo(id);
        infoMapper.updateInfoFromInfoCreationDTO(infoCreationDTO, info);
        infoRepository.save(info);
    }

    public void deleteInfo(Long id) {
        Info info = getInfo(id);
        info.setIsDeleted(true);
        for (Manual manual : info.getManuals()) {
            manual.setIsDeleted(true);
        }
        infoRepository.save(info);
    }

    public void restoreInfo(Long id) {
        Info info = getInfo(id);
        info.setIsDeleted(false);
        for (Manual manual : info.getManuals()) {
            manual.setIsDeleted(false);
        }
        infoRepository.save(info);
    }
}
