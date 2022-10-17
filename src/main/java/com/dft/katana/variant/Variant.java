package com.dft.katana.variant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Variant {

    public Integer id;
    public Integer productId;
    public String materialId;
    public String sku;
    public Double salesPrice;
    public Integer purchasePrice;
    public List<ConfigAttribute> configAttributes;
    public String type;
    public String deletedAt;
    public String internalBarcode;
    public List<String> supplierItemCodes;
    public String registeredBarcode;
    public String createdAt;
    public String updatedAt;

}
