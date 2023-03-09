package com.dft.katana;

import com.dft.katana.handler.JsonBodyHandler;
import com.dft.katana.model.recipe.RecipeWrapper;
import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class KatanaRecipe extends KatanaSDK {

    public KatanaRecipe(String accessToken) {
        super(accessToken);
    }

    @SneakyThrows
    public RecipeWrapper getAllRecipes(HashMap<String, String> params) {

        URIBuilder uriBuilder = baseUrl(new URIBuilder(), "/v1/recipes");

        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                uriBuilder.addParameter(key, params.get(key));
            }
        }

        HttpRequest request = HttpRequest.newBuilder(uriBuilder.build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .GET()
            .build();

        HttpResponse.BodyHandler<RecipeWrapper> handler = new JsonBodyHandler<>(RecipeWrapper.class);
        return getRequestWrapped(request, handler);
    }
}