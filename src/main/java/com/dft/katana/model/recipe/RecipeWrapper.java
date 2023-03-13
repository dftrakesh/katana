
package com.dft.katana.model.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeWrapper {

    private List<Recipe> data;
}