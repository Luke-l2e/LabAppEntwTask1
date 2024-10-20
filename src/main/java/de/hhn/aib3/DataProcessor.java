package de.hhn.aib3;

import com.google.gson.Gson;
import de.hhn.aib3.data.Customer;
import de.hhn.aib3.data.Dataset;
import de.hhn.aib3.data.Result;

import java.util.HashMap;

public class DataProcessor {

    /**
     * Takes a JSON as String and creates a Dataset with it.
     *
     * @param json The JSON file in a String
     * @return Dataset
     */
    public Dataset convertJsonToDataset(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Dataset.class);
    }

    /**
     * Takes a Result as String and creates a JSON file as String with it.
     *
     * @param result The object to be converted to json
     * @return JSON file as String
     */
    public String convertResultToJson(Result result) {
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    /**
     * Takes a Dataset, processes it and returns a Result
     *
     * @return Result
     */
    public Result processDatasetToResult(Dataset dataset) {
        HashMap<String, Customer> results = new HashMap<>();
        dataset.events().forEach(event -> {
            results.computeIfPresent(event.customerId(), (s, customer) -> {
                if (event.eventType().equals("start")) {
                    customer.setConsumption(customer.getConsumption() - event.timestamp());
                } else {
                    customer.setConsumption(customer.getConsumption() + event.timestamp());
                }
                return customer;
            });
            results.putIfAbsent(event.customerId(), new Customer(event.customerId(), event.eventType().equals("start") ? -event.timestamp() : event.timestamp()));
        });
        return new Result(results.values());
    }
}
