package com.college.cms.modules.role.service;

import com.college.cms.exception.CustomException;
import com.college.cms.modules.role.dto.ModuleDto;
import com.college.cms.modules.role.entity.ModuleName;
import com.college.cms.modules.role.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleName createModule(ModuleDto param){

        String name = param.getName().trim().toLowerCase();
        String code = param.getCode() != null ? param.getCode().trim().toUpperCase() : null;

        if(moduleRepository.existsByName(name)){
            throw new CustomException(
                    "Module name already exist",
                    HttpStatus.CONFLICT,
                    "Module_001"
            );

        }

        ModuleName parent = null;
        if(param.getParentId() != null){
            parent = moduleRepository.findById(param.getParentId())
                    .orElseThrow(() -> new CustomException(
                            "Parent module not found",
                            HttpStatus.NOT_FOUND,
                            "MODULE_003"
                    ));

        }

        ModuleName moduleName = ModuleName.builder()
                .name(name)
                .code(code)
                .routePath(param.getRoutePath())
                .icon(param.getIcon())
                .displayOrder(param.getDisplayOrder())
                .parent(parent)
                .build();

        return moduleRepository.save(moduleName);
    }
}
