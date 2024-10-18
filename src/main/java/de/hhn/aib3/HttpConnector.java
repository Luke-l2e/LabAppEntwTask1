package de.hhn.aib3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class HttpConnector {
    public String requestDataset() {
        //URL url = new URL("http://localhost:8080/v1/dataset");
        try (InputStream inputStream = new URI("https://httpbin.org/get").toURL().openStream()) {
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
