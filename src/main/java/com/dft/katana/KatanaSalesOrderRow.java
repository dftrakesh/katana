package com.dft.katana;

import com.dft.katana.handler.JsonBodyHandler;
import com.dft.katana.model.salesorder.SalesOrderRow;
import com.dft.katana.model.variant.UpdateVariantRequest;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KatanaSalesOrderRow extends KatanaSDK {

    public KatanaSalesOrderRow(String accessToken) {
        super(accessToken);
    }

    @SneakyThrows
    public SalesOrderRow updateSalesOrderRow(Integer id, Integer variantId) {
        URI uri = baseUrl("/v1/sales_order_rows/" + id);
        UpdateVariantRequest updateVariantRequest = new UpdateVariantRequest(variantId);
        String jsonBody = objectMapper.writeValueAsString(updateVariantRequest);
        HttpRequest request = patch(uri, jsonBody);
        HttpResponse.BodyHandler<SalesOrderRow> handler = new JsonBodyHandler<>(SalesOrderRow.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public Void deleteSalesOrderRow(Integer id) {
        URI uri = baseUrl("/v1/sales_order_rows/" + id);
        HttpRequest request = delete(uri);
        return getRequestWrapped(request, HttpResponse.BodyHandlers.discarding());
    }
}