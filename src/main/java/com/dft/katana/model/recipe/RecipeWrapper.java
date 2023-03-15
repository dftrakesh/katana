
package com.dft.katana.model.recipe;

import com.dft.katana.model.common.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeWrapper {

    private List<Recipe> data;
    private Pagination pagination;
}