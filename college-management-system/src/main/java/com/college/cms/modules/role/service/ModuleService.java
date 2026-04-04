package com.college.cms.modules.role.service;

import com.college.cms.common.base.constant.ErrorCodes;
import com.college.cms.exception.CustomException;
import com.college.cms.modules.role.dto.ModuleDto;
import com.college.cms.modules.role.entity.ModuleName;
import com.college.cms.modules.role.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleName createModule(ModuleDto param) {
        String name = param.getName().trim().toLowerCase();
        String code = param.getCode() != null ? param.getCode().trim().toUpperCase() : null;

        if (moduleRepository.existsByName(name)) {
            throw new CustomException(
                    "Module name already exists",
                    HttpStatus.CONFLICT,
                    "MODULE_001"
            );
        }

        ModuleName parent = null;
        if (param.getParentId() != null) {
            parent = moduleRepository.findById(param.getParentId())
                    .orElseThrow(() -> new CustomException(
                            "Parent module not found",
                            HttpStatus.NOT_FOUND,
                            "MODULE_404"
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

    public List<ModuleName> getAllModules() {
        return moduleRepository.findAll();
    }

    public ModuleName getModuleById(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Module not found",
                        HttpStatus.NOT_FOUND,
                        "MODULE_404"
                ));
    }

    public ModuleName updateModule(Long id, ModuleDto param) {
        ModuleName module = getModuleById(id);

        String name = param.getName().trim().toLowerCase();
        String code = param.getCode() != null ? param.getCode().trim().toUpperCase() : null;

        // Check if name exists for another module
        if (!module.getName().equals(name) && moduleRepository.existsByName(name)) {
            throw new CustomException(
                    "Module name already exists",
                    HttpStatus.CONFLICT,
                    "MODULE_001"
            );
        }

        ModuleName parent = null;
        if (param.getParentId() != null) {
            if (param.getParentId().equals(id)) {
                throw new CustomException(
                        "Module cannot be its own parent",
                        HttpStatus.BAD_REQUEST,
                        "MODULE_002"
                );
            }
            parent = moduleRepository.findById(param.getParentId())
                    .orElseThrow(() -> new CustomException(
                            "Parent module not found",
                            HttpStatus.NOT_FOUND,
                            "MODULE_404"
                    ));
        }

        module.setName(name);
        module.setCode(code);
        module.setRoutePath(param.getRoutePath());
        module.setIcon(param.getIcon());
        module.setDisplayOrder(param.getDisplayOrder());
        module.setParent(parent);

        return moduleRepository.save(module);
    }

    public void deleteModule(Long id) {
        ModuleName module = getModuleById(id);
        moduleRepository.delete(module);
    }
}
