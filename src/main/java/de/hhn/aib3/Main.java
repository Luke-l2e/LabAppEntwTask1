package de.hhn.aib3;

import de.hhn.aib3.data.*;

public class Main {
    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();
        DataProcessor dataProcessor = new DataProcessor();

        Dataset dataset = dataProcessor.convertJsonToDataset(httpConnector.fetchDatasetAsJson());
        Result result = dataProcessor.processDatasetToResult(dataset);
        String resultJson = dataProcessor.convertResultToJson(result);
        httpConnector.sendResult(resultJson);
    }
}