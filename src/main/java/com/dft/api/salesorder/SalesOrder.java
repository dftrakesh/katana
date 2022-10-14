package com.dft.api.salesorder;

import java.time.ZonedDateTime;
import java.util.List;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SalesOrder {

    private Integer id;
    private Integer customerId;
    private String orderNo;
    private String source;
    private ZonedDateTime orderCreatedDate;
    private ZonedDateTime deliveryDate;
    private Integer locationId;
    private ZonedDateTime pickedDate;
    private String invoicingStatus;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime deletedAt;
    private String ecommerceOrderType;
    private String ecommerceStoreName;
    private String ecommerceOrderId;
    private String status;
    private String currency;
    private Integer conversionRate;
    private Double total;
    private Double totalInBaseCurrency;
    private ZonedDateTime conversionDate;
    private String productAvailability;
    private ZonedDateTime productExpectedDate;
    private String ingredientAvailability;
    private ZonedDateTime ingredientExpectedDate;
    private String productionStatus;
    private String additionalInfo;
    private List<SalesOrderRow> salesOrderRows;
    private String trackingNumber;
    private String trackingNumberUrl;
    private Integer billingAddressId;
    private Integer shippingAddressId;
    private List<Address> addresses;

}
