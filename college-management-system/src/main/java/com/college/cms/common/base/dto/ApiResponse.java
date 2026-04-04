package com.college.cms.common.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private int status;
    private String message;
    private String errorCode;
    private T data;
    private Map<String, String> fieldErrors;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private String path;
}