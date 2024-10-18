package de.hhn.aib3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hhn.aib3.data.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();
        String test = "{\n" +
                "\"events\": [\n" +
                "{\n" +
                "\"customerId\": \"342a55a3-b138-4665-8351-111b28833d34\",\n" +
                "\"workloadId\": \"53ddf5cf-7a12-4b7f-8751-48757774c0c5\",\n" +
                "\"timestamp\": 1699872883000,\n" +
                "\"eventType\": \"start\"\n" +
                "},\n" +
                "{\n" +
                "\"customerId\": \"342a55a3-b138-4665-8351-111b28833d34\",\n" +
                "\"workloadId\": \"53ddf5cf-7a12-4b7f-8751-48757774c0c5\",\n" +
                "\"timestamp\": 1700401984000,\n" +
                "\"eventType\": \"stop\"\n" +
                "}\n]\n}";
        Gson gson = new Gson();
        Dataset dataset = gson.fromJson(test, Dataset.class);

        HashMap<String, Customer> result = new HashMap<>();

        dataset.events().forEach(event -> {
            result.computeIfPresent(event.customerId(), (s, customer) -> {
                if (event.eventType().equals("start")) {
                    customer.setConsumption(customer.getConsumption() - event.timestamp());
                } else {
                    customer.setConsumption(customer.getConsumption() + event.timestamp());
                }
                return customer;
            });
            result.putIfAbsent(event.customerId(), event.eventType().equals("start") ? new Customer(event.customerId(), -event.timestamp()) : new Customer(event.customerId(), event.timestamp()));
        });

        System.out.println("Keys: ");
        for (String s : result.keySet()) {
            System.out.println(s);
        }
        System.out.println("Customers: ");
        for (Customer value : result.values()) {
            System.out.println(value.toString());
        }

        Test test1 = new Test(result.values());
        gson.toJson(test1, System.out);
    }
}