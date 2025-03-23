package org.krmdemo.sandbox.ipinfo;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * A simple test to verify several predefined REST-endpoints
 */
public class HttpClientTest {

    private static final String IP_INFO_TOKEN = "8c94ca9ba5169a";

    @Test
    void testPostmanEcho() {
        HttpResponse<String> response = httpGetAsString("https://postman-echo.com/get");
        System.out.printf("response.body for '%s' --> %s%n", response.uri(), response.body());
    }

    @Test
    void testMyIP() {
        HttpResponse<String> response = httpGetAsString("https://httpbin.org/ip");
        System.out.printf("response.body for '%s' --> %s%n", response.uri(), response.body());
    }

    @Test
    void testMyIPInfo() {
        HttpResponse<String> response = httpGetAsString("https://ipinfo.io/");
        System.out.printf("response.body for '%s' --> %s%n", response.uri(), response.body());
    }

    private static HttpResponse<String> httpGetAsString(String endpoint) {
        HttpRequest httpRequest = httpRequestGet(endpoint);
        try {
            return HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new IllegalStateException(
                String.format("could not invoke REST-endpoint '%s'", endpoint),
                ex);
        }
    }

    private static HttpRequest httpRequestGet(String endpoint) {
        return HttpRequest.newBuilder()
            .uri(enrpointURI(endpoint))
            .header("Accept", "application/xml")
            .header("Authorization", "Bearer " + IP_INFO_TOKEN)
            .timeout(Duration.of(3, SECONDS))
            .GET()
            .build();
    }

    private static URI enrpointURI(String endpoint) {
        try {
            return new URI(endpoint);
        } catch (URISyntaxException ursEx) {
            throw new IllegalArgumentException(
                String.format("invalid endpoint '%s'", endpoint),
                ursEx);
        }
    }
}
