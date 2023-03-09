
package com.dft.katana.model.inventory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Inventory {

    private Integer variantId;
    private Integer locationId;
    private Double reorderPoint;
    private Double averageCost;
    private Double valueInStock;
    private Double quantityInStock;
    private Double quantityCommitted;
    private Double quantityExpected;
    private Double quantityMissingOrExcess;
}