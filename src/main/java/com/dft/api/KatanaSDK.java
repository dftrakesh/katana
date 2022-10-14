package com.dft.api;

import com.dft.api.exception.BadRequestException;
import com.dft.api.exception.NotFoundException;
import com.dft.api.exception.TooManyRequestsException;
import com.dft.api.exception.UnauthorizedException;
import com.dft.api.salesorder.SalesOrderList;
import com.dft.api.salesorder.SalesOrderRow;
import com.dft.api.variant.Variant;
import com.dft.api.variant.VariantList;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

@Log4j2
public class KatanaSDK {

    private final String accessToken;

    WebClient webClient = WebClient.builder()
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024))
            .build()).build();

    public KatanaSDK(String accessToken) {
        this.accessToken = accessToken;
    }

    public Mono<VariantList> getVariantBySku(String sku) {
        log.debug("API call for get variant with sku: {}", sku);

        final WebClient.RequestHeadersSpec<?> spec = webClient.get()
            .uri(uriBuilder -> baseUrl(uriBuilder, "/v1/variants")
                .queryParam("sku", sku)
                .build());
        return authHeader(spec)
            .exchangeToMono(clientResponse -> handleResponse(clientResponse, VariantList.class));
    }

    public Mono<Variant> getVariantById(Integer id) {
        log.debug("API call for get variant with id: {}", id);

        final WebClient.RequestHeadersSpec<?> spec = webClient.get()
            .uri(uriBuilder -> baseUrl(uriBuilder, "/v1/variants/{id}")
                .build(id));
        return authHeader(spec)
            .exchangeToMono(clientResponse -> handleResponse(clientResponse, Variant.class));
    }

    public Mono<SalesOrderList> getSalesOrderByNumber(String orderNumber) {
        log.debug("API call for get order with number: {}", orderNumber);

        final WebClient.RequestHeadersSpec<?> spec = webClient.get()
            .uri(uriBuilder -> baseUrl(uriBuilder, "/v1/sales_orders")
                .queryParam("order_no", orderNumber)
                .build());
        return authHeader(spec)
            .exchangeToMono(clientResponse -> handleResponse(clientResponse, SalesOrderList.class));
    }

    public Mono<SalesOrderRow> getSalesOrderRow(Integer id) {
        log.debug("API call for get order-item with id: {}", id);

        final WebClient.RequestHeadersSpec<?> spec = webClient.get()
            .uri(uriBuilder -> baseUrl(uriBuilder, "/v1/sales_order_rows/{id}")
                .build(id));
        return authHeader(spec)
            .exchangeToMono(clientResponse -> handleResponse(clientResponse, SalesOrderRow.class));
    }

    public Mono<SalesOrderRow> updateSalesOrderRow(Integer id, Integer variantId) {
        log.debug("API call for update order-item with id: {}", id);

        final WebClient.RequestHeadersSpec<?> spec = webClient.patch()
            .uri(uriBuilder -> baseUrl(uriBuilder, "/v1/sales_order_rows/{id}")
                .queryParam("variant_id", variantId)
                .build(id));
        return authHeader(spec)
            .exchangeToMono(clientResponse -> handleResponse(clientResponse, SalesOrderRow.class));
    }

    public Mono<SalesOrderRow> deleteSalesOrderRow(Integer id, Integer variantId) {
        log.debug("API call for delete order-item with id: {}", id);

        final WebClient.RequestHeadersSpec<?> spec = webClient.delete()
            .uri(uriBuilder -> baseUrl(uriBuilder, "/v1/sales_order_rows/{id}")
                .build(id));
        return authHeader(spec)
            .exchangeToMono(clientResponse -> handleResponse(clientResponse, SalesOrderRow.class));
    }

    private <T> Mono<T> handleResponse(ClientResponse clientResponse, Class<T> clazz) {
        if (clientResponse.statusCode().is2xxSuccessful()) {
            return clientResponse.bodyToMono(clazz);
        }
        return handleUnknownError(clientResponse);
    }

    private <T> Mono<T> handleUnknownError(ClientResponse clientResponse) {
        log.debug("Handle unknown error {}", clientResponse.statusCode().getReasonPhrase());

        return clientResponse
            .bodyToMono(String.class)
            .flatMap(responseBody -> {
                log.debug("Response status code {} body {}", clientResponse.rawStatusCode(), responseBody);

                if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                    return Mono.error(new NotFoundException());
                }
                if (clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
                    return Mono.error(new UnauthorizedException());
                }
                if (clientResponse.statusCode().equals(HttpStatus.TOO_MANY_REQUESTS)) {
                    return Mono.error(new TooManyRequestsException());
                }
                if (clientResponse.statusCode().equals(HttpStatus.BAD_REQUEST)) {
                    return Mono.error(new BadRequestException());
                }
                return Mono.error(new IllegalStateException(responseBody));
            });
    }

    private UriBuilder baseUrl(UriBuilder uriBuilder, String path) {
        return uriBuilder
            .scheme("https")
            .host("api.katanamrp.com")
            .path(path);
    }

    private WebClient.RequestHeadersSpec<?> authHeader(WebClient.RequestHeadersSpec<?> headersSpec) {
        return headersSpec.header(
            "Authorization",
            "Bearer " + this.accessToken);
    }
}
