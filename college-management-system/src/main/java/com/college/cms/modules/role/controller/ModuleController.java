package com.college.cms.modules.role.controller;

import com.college.cms.common.base.dto.ApiResponse;
import com.college.cms.modules.role.dto.ModuleDto;
import com.college.cms.modules.role.entity.ModuleName;
import com.college.cms.modules.role.service.ModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/modules")
@RestController
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping
    public ApiResponse<ModuleName> createModule(
            @Valid
            @RequestBody ModuleDto param
    ){
        ModuleName moduleName = moduleService.createModule(param);
            return ApiResponse.<ModuleName>builder()
                    .success(true)
                    .message("Module created successfully")
                    .errorCode(null)
                    .data(moduleName)
                    .build();

    }

}
