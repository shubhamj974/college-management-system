package com.college.cms.modules.auth.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private UserInfo user;
    private List<RoleInfo> roles;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String profilePicture;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleInfo {
        private Long id;
        private String name;
        private String code;
        private List<ModulePermission> permissions;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModulePermission {
        private Long moduleId;
        private String moduleName;
        private String moduleCode;
        private String icon;
        private String routePath;
        private List<String> permissions;
    }
}