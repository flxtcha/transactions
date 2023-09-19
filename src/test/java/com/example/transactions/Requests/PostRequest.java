package com.example.transactions.Requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PostRequest implements Request {

    private final HttpClient client;

    public PostRequest() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public HttpResponse<String> send(String uri, String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to send POST request", e);
        }
    }

    @Override
    public HttpResponse<String> send(String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to send POST request", e);
        }
    }
}
