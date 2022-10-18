package com.dft.katana.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Errors {
    private String statusCode;
    private String name;
    private String message;
    private List<ErrorDetails> details;
}
