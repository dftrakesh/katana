package com.dft.katana.model.webhook;

import com.dft.katana.model.common.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookWrapper {

    private List<Webhook> data;
    private Pagination pagination;
}