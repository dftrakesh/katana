package com.dft.api.salesorder;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SalesOrderRow {

    public Integer salesOrderId;
    private Integer id;
    private Integer quantity;
    private Integer variantId;
    private Integer taxRateId;
    private String pricePerUnit;
    private Double pricePerUnitInBaseCurrency;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime deletedAt;
    private Double total;
    private Double totalInBaseCurrency;
    private Integer conversionRate;
    private ZonedDateTime conversionDate;
    private List<Attribute> attributes;
    private String linkedManufacturingOrderId;
    private List<BatchTransaction> batchTransactions;

}
