package pl.rafzab.githubreposervice.config.http;

import pl.rafzab.githubreposervice.config.logger.Logger;
import pl.rafzab.githubreposervice.exception.http.HttpClientSenderException;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestClient {
    public static HttpResponse<String> trySendRequest(HttpRequest request) {
        Logger.debug("send request to {}", request.uri().toString());
        try (HttpClient httpClient = HttpClient.newBuilder().build()) {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new HttpClientSenderException(e.getMessage());
        }
    }
}
