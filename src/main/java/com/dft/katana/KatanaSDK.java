package com.dft.katana;

import com.dft.katana.handler.JsonBodyHandler;
import com.dft.katana.salesorder.SalesOrderList;
import com.dft.katana.salesorder.SalesOrderRow;
import com.dft.katana.variant.Variant;
import com.dft.katana.variant.VariantList;
import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class KatanaSDK {

    private final String accessToken;
    private final HttpClient client;
    int MAX_ATTEMPTS = 50;
    int TIME_OUT_DURATION = 15000;

    public KatanaSDK(String accessToken) {
        this.accessToken = accessToken;
        client = HttpClient.newHttpClient();
    }

    @SneakyThrows
    public VariantList getVariantBySku(String sku) {
        URIBuilder uriBuilder = baseUrl(new URIBuilder(), "/v1/variants");
        uriBuilder.addParameter("sku", sku);

        HttpRequest request = HttpRequest.newBuilder(uriBuilder.build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken)
            .GET()
            .build();
        HttpResponse.BodyHandler<VariantList> handler = new JsonBodyHandler<>(VariantList.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public Variant getVariantById(Integer id) {
        URIBuilder uriBuilder = baseUrl(new URIBuilder(), "/v1/variants/" + id);

        HttpRequest request = HttpRequest.newBuilder(uriBuilder.build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken)
            .GET()
            .build();
        HttpResponse.BodyHandler<Variant> handler = new JsonBodyHandler<>(Variant.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public SalesOrderList getSalesOrderByNumber(String orderNumber) {
        URIBuilder uriBuilder = baseUrl(new URIBuilder(), "/v1/sales_orders");
        uriBuilder.setParameter("order_no", orderNumber);

        HttpRequest request = HttpRequest.newBuilder(uriBuilder.build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken)
            .GET()
            .build();
        HttpResponse.BodyHandler<SalesOrderList> handler = new JsonBodyHandler<>(SalesOrderList.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public SalesOrderRow getSalesOrderRow(Integer id) {
        URIBuilder uriBuilder = baseUrl(new URIBuilder(), "/v1/sales_orders/" + id);

        HttpRequest request = HttpRequest.newBuilder(uriBuilder.build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken)
            .GET()
            .build();
        HttpResponse.BodyHandler<SalesOrderRow> handler = new JsonBodyHandler<>(SalesOrderRow.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public SalesOrderRow updateSalesOrderRow(Integer id, Integer variantId) {
        URIBuilder uriBuilder = baseUrl(new URIBuilder(), "/v1/sales_orders/" + id);
        uriBuilder.setParameter("variant_id", variantId.toString());
        HttpRequest.BodyPublisher publisher = null;

        HttpRequest request = HttpRequest.newBuilder(uriBuilder.build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken)
            .method("PATCH", null)
            .build();
        HttpResponse.BodyHandler<SalesOrderRow> handler = new JsonBodyHandler<>(SalesOrderRow.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public SalesOrderRow deleteSalesOrderRow(Integer id) {
        URIBuilder uriBuilder = baseUrl(new URIBuilder(), "/v1/sales_orders/" + id);

        HttpRequest request = HttpRequest.newBuilder(uriBuilder.build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken)
            .DELETE()
            .build();
        HttpResponse.BodyHandler<SalesOrderRow> handler = new JsonBodyHandler<>(SalesOrderRow.class);
        return getRequestWrapped(request, handler);
    }

    private URIBuilder baseUrl(URIBuilder uriBuilder, String path) {
        return uriBuilder
            .setScheme("https")
            .setHost("api.katanamrp.com")
            .setPath(path);
    }

    @SneakyThrows
    public <T> T getRequestWrapped(HttpRequest request, HttpResponse.BodyHandler<T> handler) {

        return client
            .sendAsync(request, handler)
            .thenComposeAsync(response -> tryResend(client, request, handler, response, 1))
            .get()
            .body();
    }

    @SneakyThrows
    public <T> CompletableFuture<HttpResponse<T>> tryResend(HttpClient client,
                                                            HttpRequest request,
                                                            HttpResponse.BodyHandler<T> handler,
                                                            HttpResponse<T> resp, int count) {

        if (resp.statusCode() == 429 && count < MAX_ATTEMPTS) {
            Thread.sleep(TIME_OUT_DURATION);
            return client.sendAsync(request, handler)
                .thenComposeAsync(response -> tryResend(client, request, handler, response, count + 1));
        }
        return CompletableFuture.completedFuture(resp);
    }
}
