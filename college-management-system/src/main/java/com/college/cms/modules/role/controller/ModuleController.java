package com.college.cms.modules.role.controller;

import com.college.cms.common.base.dto.ApiResponse;
import com.college.cms.modules.role.dto.ModuleDto;
import com.college.cms.modules.role.entity.ModuleName;
import com.college.cms.modules.role.service.ModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modules")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping
    public ResponseEntity<ApiResponse<ModuleName>> createModule(
            @Valid @RequestBody ModuleDto param
    ) {
        ModuleName moduleName = moduleService.createModule(param);

        ApiResponse<ModuleName> response = ApiResponse.<ModuleName>builder()
                .success(true)
                .status(HttpStatus.CREATED.value())
                .message("Module created successfully")
                .data(moduleName)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ModuleName>>> getAllModules() {
        List<ModuleName> modules = moduleService.getAllModules();

        ApiResponse<List<ModuleName>> response = ApiResponse.<List<ModuleName>>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Modules fetched successfully")
                .data(modules)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ModuleName>> getModuleById(@PathVariable Long id) {
        ModuleName module = moduleService.getModuleById(id);

        ApiResponse<ModuleName> response = ApiResponse.<ModuleName>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Module fetched successfully")
                .data(module)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ModuleName>> updateModule(
            @PathVariable Long id,
            @Valid @RequestBody ModuleDto param
    ) {
        ModuleName module = moduleService.updateModule(id, param);

        ApiResponse<ModuleName> response = ApiResponse.<ModuleName>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Module updated successfully")
                .data(module)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Module deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }
}
