package com.dft.katana;

import com.dft.katana.model.common.Pagination;
import com.dft.katana.model.inventory.InventoryWrapper;
import com.dft.katana.model.recipe.RecipeWrapper;
import com.dft.katana.model.variant.VariantList;
import com.dft.katana.model.webhook.WebhookWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.dft.katana.constantcodes.ConstantCode.ACCEPT;
import static com.dft.katana.constantcodes.ConstantCode.AUTHORIZATION;
import static com.dft.katana.constantcodes.ConstantCode.BASE_ENDPOINT;
import static com.dft.katana.constantcodes.ConstantCode.BEARER;
import static com.dft.katana.constantcodes.ConstantCode.CONTENT_TYPE;
import static com.dft.katana.constantcodes.ConstantCode.HTTPS;

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
    protected HttpRequest get(URI uri) {
        return HttpRequest.newBuilder(uri)
            .header(AUTHORIZATION, BEARER + accessToken)
            .header(CONTENT_TYPE, "application/json")
            .header(ACCEPT, "application/json")
            .GET()
            .build();
    }

    @SneakyThrows
    protected HttpRequest patch(URI uri, String jsonBody) {
        return HttpRequest.newBuilder(uri)
            .header(AUTHORIZATION, BEARER + accessToken)
            .header(CONTENT_TYPE, "application/json")
            .header(ACCEPT, "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
    }

    @SneakyThrows
    protected HttpRequest delete(URI uri) {
        return HttpRequest.newBuilder(uri)
            .header(AUTHORIZATION, BEARER + accessToken)
            .header(CONTENT_TYPE, "application/json")
            .header(ACCEPT, "application/json")
            .DELETE()
            .build();
    }

    @SneakyThrows
    protected HttpRequest post(URI uri, String jsonBody) {
        return HttpRequest.newBuilder(uri)
            .header(AUTHORIZATION, BEARER + accessToken)
            .header(CONTENT_TYPE, "application/json")
            .header(ACCEPT, "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
    }

    @SneakyThrows
    protected URI addParameters(URI uri, HashMap<String, String> params) {

        if (params == null) return uri;
        String query = uri.getQuery();
        StringBuilder builder = new StringBuilder();
        if (query != null)
            builder.append(query);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String keyValueParam = entry.getKey() + "=" + entry.getValue();
            if (!builder.toString().isEmpty())
                builder.append("&");
            builder.append(keyValueParam);
        }
        return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), builder.toString(), uri.getFragment());
    }

    @SneakyThrows
    protected URI baseUrl(String path) {
        return new URI(new StringBuilder().append(HTTPS)
            .append(BASE_ENDPOINT)
            .append(path)
            .toString());
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
        setPagination(resp);
        return CompletableFuture.completedFuture(resp);
    }

    @SneakyThrows
    private void setPagination(HttpResponse resp) {
        List<String> headers = resp.headers().allValues("X-Pagination");
        if (headers.size() != 0) {
            Pagination pagination = objectMapper.readValue(headers.get(0), Pagination.class);

            if (resp.body() instanceof VariantList) ((VariantList) resp.body()).setPagination(pagination);
            if (resp.body() instanceof InventoryWrapper) ((InventoryWrapper) resp.body()).setPagination(pagination);
            if (resp.body() instanceof RecipeWrapper) ((RecipeWrapper) resp.body()).setPagination(pagination);
            if (resp.body() instanceof WebhookWrapper) ((WebhookWrapper) resp.body()).setPagination(pagination);
        }
    }
}
