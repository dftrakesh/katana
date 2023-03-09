
package com.dft.katana.model.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Recipe {

    private Integer recipeRowId;
    private Integer recipeId;
    private Integer productId;
    private Integer productVariantId;
    private Integer ingredientVariantId;
    private Integer quantity;
    private String notes;
    private String createdAt;
    private String updatedAt;
}