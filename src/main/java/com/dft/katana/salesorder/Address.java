package com.dft.katana.salesorder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private String updatedAt;
    private String createdAt;
    private String deletedAt;

}
