package com.anchuk.citylist.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private String message;
    private List<String> details;
}
