
package com.dft.katana.model.inventory;

import com.dft.katana.model.common.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryWrapper {

    private List<Inventory> data;
    private Pagination pagination;
}