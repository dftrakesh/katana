package com.dft.katana;

import com.dft.katana.handler.JsonBodyHandler;
import com.dft.katana.model.salesorder.SalesOrderList;
import com.dft.katana.model.salesorder.SalesOrderRow;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class KatanaSalesOrder extends KatanaSDK {

    public KatanaSalesOrder(String accessToken) {
        super(accessToken);
    }

    @SneakyThrows
    public SalesOrderList getSalesOrders(HashMap<String, String> params) {
        URI uri = addParameters(baseUrl("/v1/sales_orders"), params);
        HttpRequest request = get(uri);
        HttpResponse.BodyHandler<SalesOrderList> handler = new JsonBodyHandler<>(SalesOrderList.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public SalesOrderRow getSalesOrderById(Integer id) {
        URI uri = baseUrl("/v1/sales_orders/" + id);
        HttpRequest request = get(uri);
        HttpResponse.BodyHandler<SalesOrderRow> handler = new JsonBodyHandler<>(SalesOrderRow.class);
        return getRequestWrapped(request, handler);
    }
}