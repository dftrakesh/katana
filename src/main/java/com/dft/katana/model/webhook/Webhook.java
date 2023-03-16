package com.dft.katana.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Webhook {

    private Long id;
    private String token;
    private String description;
    private List<String> subscribedEvents;
    private String url;
    private String enabled;
    private String createdAt;
    private String updatedAt;
}