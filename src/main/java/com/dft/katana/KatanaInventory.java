package com.dft.katana;

import com.dft.katana.handler.JsonBodyHandler;
import com.dft.katana.model.inventory.InventoryWrapper;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class KatanaInventory extends KatanaSDK {

    public KatanaInventory(String accessToken) {
        super(accessToken);
    }

    @SneakyThrows
    public InventoryWrapper getInventory(HashMap<String, String> params) {
        URI uri = addParameters(baseUrl("/v1/inventory"), params);
        HttpRequest request = get(uri);
        HttpResponse.BodyHandler<InventoryWrapper> handler = new JsonBodyHandler<>(InventoryWrapper.class);
        return getRequestWrapped(request, handler);
    }
}