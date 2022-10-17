package com.dft.katana.salesorder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrderRow {

    public Integer salesOrderId;
    private Integer id;
    private Integer quantity;
    private Integer variantId;
    private Integer taxRateId;
    private String pricePerUnit;
    private Double pricePerUnitInBaseCurrency;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private Double total;
    private Double totalInBaseCurrency;
    private Integer conversionRate;
    private String conversionDate;
    private List<Attribute> attributes;
    private String linkedManufacturingOrderId;
    private List<BatchTransaction> batchTransactions;

}
