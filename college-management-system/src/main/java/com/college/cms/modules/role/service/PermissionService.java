package com.college.cms.modules.role.service;
import com.college.cms.exception.CustomException;
import com.college.cms.modules.role.dto.PermissionDto;
import com.college.cms.modules.role.entity.Permission;
import com.college.cms.modules.role.repository.PermissionRepositrory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepositrory permissionRepositrory;

    public Permission createPermission(PermissionDto param){

        Permission permission = Permission.builder()
                .name(param.getName())
                .code(param.getCode())
                .build();
        return permissionRepositrory.save(permission);
    }
}
