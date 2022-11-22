package com.dft.katana.model.variant;

import com.dft.katana.model.common.Errors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Variant {

    private String id;
    private String productId;
    private String materialId;
    private String sku;
    private Double salesPrice;
    private Integer purchasePrice;
    private List<ConfigAttribute> configAttributes;
    private String type;
    private String deletedAt;
    private String internalBarcode;
    private List<String> supplierItemCodes;
    private String registeredBarcode;
    private String createdAt;
    private String updatedAt;
    private Errors error;
}
