package com.dft.katana;

import com.dft.katana.handler.JsonBodyHandler;
import com.dft.katana.model.recipe.RecipeWrapper;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class KatanaRecipe extends KatanaSDK {

    public KatanaRecipe(String accessToken) {
        super(accessToken);
    }

    @SneakyThrows
    public RecipeWrapper getAllRecipes(HashMap<String, String> params) {
        URI uri = addParameters(baseUrl("/v1/recipes"), params);
        HttpRequest request = get(uri);
        HttpResponse.BodyHandler<RecipeWrapper> handler = new JsonBodyHandler<>(RecipeWrapper.class);
        return getRequestWrapped(request, handler);
    }
}