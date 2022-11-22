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
public class SalesOrder {

    private String id;
    private String customerId;
    private String orderNo;
    private String source;
    private String orderCreatedDate;
    private String deliveryDate;
    private Integer locationId;
    private String pickedDate;
    private String invoicingStatus;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String ecommerceOrderType;
    private String ecommerceStoreName;
    private String ecommerceOrderId;
    private String status;
    private String currency;
    private Integer conversionRate;
    private Double total;
    private Double totalInBaseCurrency;
    private String conversionDate;
    private String productAvailability;
    private String productExpectedDate;
    private String ingredientAvailability;
    private String ingredientExpectedDate;
    private String productionStatus;
    private String additionalInfo;
    private List<SalesOrderRow> salesOrderRows;
    private String trackingNumber;
    private String trackingNumberUrl;
    private Integer billingAddressId;
    private Integer shippingAddressId;
    private List<Address> addresses;
    private Errors error;
}
