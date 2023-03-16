package com.dft.katana;

import com.dft.katana.handler.JsonBodyHandler;
import com.dft.katana.model.webhook.Webhook;
import com.dft.katana.model.webhook.WebhookRequest;
import com.dft.katana.model.webhook.WebhookWrapper;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class KatanaWebhook extends KatanaSDK {

    public KatanaWebhook(String accessToken) {
        super(accessToken);
    }

    @SneakyThrows
    public Webhook createWebhook(WebhookRequest webhookRequest) {
        URI uri = baseUrl("/v1/webhooks");
        String jsonBody = objectMapper.writeValueAsString(webhookRequest);
        HttpRequest request = post(uri, jsonBody);
        HttpResponse.BodyHandler<Webhook> handler = new JsonBodyHandler<>(Webhook.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public WebhookWrapper getWebhook(HashMap<String, String> params) {
        URI uri = addParameters(baseUrl("/v1/webhooks"), params);
        HttpRequest request = get(uri);
        HttpResponse.BodyHandler<WebhookWrapper> handler = new JsonBodyHandler<>(WebhookWrapper.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public Void deleteWebhook(Integer id) {
        URI uri = baseUrl("/v1/webhooks/" + id);
        HttpRequest request = delete(uri);
        return getRequestWrapped(request, HttpResponse.BodyHandlers.discarding());
    }
}