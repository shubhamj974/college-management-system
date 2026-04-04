package com.college.cms.common.base.constant;

public final class ErrorCodes {

    private ErrorCodes() {} // Prevent instantiation

    // Authentication & Authorization
    public static final String AUTH_INVALID = "AUTH_001";
    public static final String BAD_CREDENTIALS = "AUTH_002";
    public static final String ACCESS_DENIED = "AUTH_403";
    public static final String TOKEN_EXPIRED = "AUTH_003";
    public static final String TOKEN_INVALID = "AUTH_004";

    // User
    public static final String USER_NOT_FOUND = "USER_404";
    public static final String USER_ALREADY_EXISTS = "USER_001";

    // Role
    public static final String ROLE_NOT_FOUND = "ROLE_404";

    // Validation
    public static final String VALIDATION_ERROR = "VAL_001";

    // Database
    public static final String DUPLICATE_FOUND = "DB_001";

    // Resource
    public static final String RESOURCE_NOT_FOUND = "RES_404";
    public static final String METHOD_NOT_ALLOWED = "RES_405";

    // General
    public static final String INTERNAL_ERROR = "GEN_500";
}
