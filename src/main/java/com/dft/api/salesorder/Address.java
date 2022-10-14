package com.dft.api.salesorder;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Address {

    private Integer id;
    private Integer salesOrderId;
    private String entityType;
    private String firstName;
    private String lastName;
    private String company;
    private String phone;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private ZonedDateTime updatedAt;
    private ZonedDateTime createdAt;
    private ZonedDateTime deletedAt;

}
