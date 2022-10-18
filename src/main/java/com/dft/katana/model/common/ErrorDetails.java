package com.dft.katana.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDetails {
    private String path;
    private String code;
    private String message;
}
