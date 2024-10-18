package de.hhn.aib3.data;

public record Event(String customerId, String workloadId, long timestamp, String eventType) {
}
