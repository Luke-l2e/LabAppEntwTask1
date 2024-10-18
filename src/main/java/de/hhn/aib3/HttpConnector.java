package de.hhn.aib3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;

public class HttpConnector {
    /**
     * Returns the json file from the website as String
     *
     * @return String
     */
    public String fetchDatasetAsJson() {
        try (InputStream inputStream = new URI(getWebsiteUrl("website.dataset")).toURL().openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().reduce("", (acc, line) -> acc + line);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
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
                    .uri(URI.create(getWebsiteUrl("website.result")))
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
    }

    /**
     * Returns the website url.
     *
     * @param site An url ending from the connection.properties file to be added to the base url
     * @return String
     */
    private String getWebsiteUrl(String site) {
        String url = "";
        try {
            url += PropertyManager.getInstance().getProperty("website");
            if (null != site && !site.isBlank()) {
                url += PropertyManager.getInstance().getProperty(site);
            }
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        if (url.isBlank()) {
            throw new RuntimeException("website dataset is incorrect");
        }
        return url;
    }
}
