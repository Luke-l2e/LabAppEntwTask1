package de.hhn.aib3;

import de.hhn.aib3.data.UrlExtensions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpConnector {
    private final String BASE_URL = "http://assessment:8080";

    /**
     * Returns the json file from the website as String
     *
     * @return String
     */
    public String fetchDatasetAsJson() {
        try (InputStream inputStream = new URI(getWebsiteUrl(UrlExtensions.DATASET)).toURL().openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().reduce("", (acc, line) -> acc + line);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Send the result with Http POST to the website.result
     *
     * @param json The JSON file as String to be sent
     */
    public void sendResult(String json) {
        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getWebsiteUrl(UrlExtensions.RESULT)))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException(response.body());
        }
        System.out.println("Request response: " + response.body());
    }

    /**
     * Returns the website url.
     *
     * @param extension The url extension to be added to the base url
     * @return String
     */
    private String getWebsiteUrl(UrlExtensions extension) {
        if (null != extension) {
            return BASE_URL + extension.path;
        }
        return BASE_URL;
    }
}
