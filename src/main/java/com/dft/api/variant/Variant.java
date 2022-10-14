package com.dft.api.variant;

import java.time.ZonedDateTime;
import java.util.List;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Variant {

    public Integer id;
    public Integer productId;
    public String materialId;
    public String sku;
    public Double salesPrice;
    public Integer purchasePrice;
    public List<ConfigAttribute> configAttributes;
    public String type;
    public ZonedDateTime deletedAt;
    public String internalBarcode;
    public List<String> supplierItemCodes;
    public String registeredBarcode;
    public ZonedDateTime createdAt;
    public ZonedDateTime updatedAt;

}
