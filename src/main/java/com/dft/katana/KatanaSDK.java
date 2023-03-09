package com.dft.katana;

import com.dft.katana.exception.NotFoundException;
import com.dft.katana.exception.UnProcessableEntityException;
import com.dft.katana.handler.JsonBodyHandler;
import com.dft.katana.model.salesorder.SalesOrderList;
import com.dft.katana.model.salesorder.SalesOrderRow;
import com.dft.katana.model.variant.UpdateVariantRequest;
import com.dft.katana.model.variant.Variant;
import com.dft.katana.model.variant.VariantList;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class KatanaSDK {

    protected final String accessToken;
    protected final HttpClient client;
    protected final ObjectMapper objectMapper = new ObjectMapper();
    int MAX_ATTEMPTS = 50;
    int TIME_OUT_DURATION = 60000;

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
    public SalesOrderList getSalesOrderByECommerceId(String ecommerceOrderId, String eCommerceStoreName) {
        URIBuilder uriBuilder = baseUrl(new URIBuilder(), "/v1/sales_orders");
        uriBuilder.setParameter("ecommerce_order_id", ecommerceOrderId);
        uriBuilder.setParameter("ecommerce_store_name", eCommerceStoreName);

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
        URIBuilder uriBuilder = baseUrl(new URIBuilder(), "/v1/sales_order_rows/" + id);

        UpdateVariantRequest updateVariantRequest = new UpdateVariantRequest(variantId);
        String jsonBody = objectMapper.writeValueAsString(updateVariantRequest);

        HttpRequest request = HttpRequest.newBuilder(uriBuilder.build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken)
            .header(HttpHeaders.CONTENT_TYPE, "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
        HttpResponse.BodyHandler<SalesOrderRow> handler = new JsonBodyHandler<>(SalesOrderRow.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public Void deleteSalesOrderRow(Integer id) {
        URIBuilder uriBuilder = baseUrl(new URIBuilder(), "/v1/sales_order_rows/" + id);

        HttpRequest request = HttpRequest.newBuilder(uriBuilder.build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken)
            .DELETE()
            .build();
        return getRequestWrapped(request, HttpResponse.BodyHandlers.discarding());
    }

    protected URIBuilder baseUrl(URIBuilder uriBuilder, String path) {
        return uriBuilder
            .setScheme("https")
            .setHost("api.katanamrp.com")
            .setPath(path);
    }

    @SneakyThrows
    public <T> T getRequestWrapped(HttpRequest request, HttpResponse.BodyHandler<T> handler) {

        return client
            .sendAsync(request, handler)
            .thenComposeAsync(response -> handleUnknownError(client, request, handler, response))
            .get();
    }

    @SneakyThrows
    public <T> CompletableFuture<T> handleUnknownError(HttpClient client,
                                                       HttpRequest request,
                                                       HttpResponse.BodyHandler<T> handler,
                                                       HttpResponse<T> resp) {
        if (resp.statusCode() == HttpStatus.SC_OK) {
            return CompletableFuture.completedFuture(resp.body());
        }
        if (resp.statusCode() == HttpStatus.SC_NO_CONTENT) {
            return CompletableFuture.completedFuture(resp.body());
        }
        if (resp.statusCode() == HttpStatus.SC_NOT_FOUND) {
            throw new NotFoundException(objectMapper.writeValueAsString(resp.body()));
        }
        if (resp.statusCode() == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
            throw new UnProcessableEntityException(objectMapper.writeValueAsString(resp.body()));
        }
        if (resp.statusCode() == 429) {
            return CompletableFuture.completedFuture(tryResend(client, request, handler, resp, 1).get().body());
        }

        throw new Exception();
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
