
package com.dft.katana.model.inventory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryWrapper {

    private List<Inventory> data;
}