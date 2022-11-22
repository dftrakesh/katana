package com.dft.katana.model.salesorder;

import com.dft.katana.model.common.Errors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrderRow {

    public String salesOrderId;
    private String id;
    private Integer quantity;
    private String variantId;
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
    private Errors error;
}
