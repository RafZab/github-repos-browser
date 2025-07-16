package pl.rafzab.githubreposervice.config.http;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestClient {
    public static HttpResponse<String> trySendRequest(HttpRequest request) {
        try (HttpClient httpClient = HttpClient.newBuilder().build()) {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
