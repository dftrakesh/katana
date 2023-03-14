package com.dft.katana;

import com.dft.katana.handler.JsonBodyHandler;
import com.dft.katana.model.variant.Variant;
import com.dft.katana.model.variant.VariantList;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class KatanaVariant extends KatanaSDK {

    public KatanaVariant(String accessToken) {
        super(accessToken);
    }

    @SneakyThrows
    public VariantList getVariant(HashMap<String, String> params) {
        URI uri = addParameters(baseUrl("/v1/variants"), params);

        HttpRequest request = get(uri);
        HttpResponse.BodyHandler<VariantList> handler = new JsonBodyHandler<>(VariantList.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public Variant getVariantById(Integer id) {
        URI uri = baseUrl("/v1/variants/" + id);

        HttpRequest request = get(uri);
        HttpResponse.BodyHandler<Variant> handler = new JsonBodyHandler<>(Variant.class);
        return getRequestWrapped(request, handler);
    }
}