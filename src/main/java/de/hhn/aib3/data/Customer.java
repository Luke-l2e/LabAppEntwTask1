package de.hhn.aib3.data;

public class Customer {
    private String customerId;
    private long consumption;

    public Customer(String customerId, long consumption) {
        this.customerId = customerId;
        this.consumption = consumption;
    }

    public long getConsumption() {
        return consumption;
    }

    public void setConsumption(long consumption) {
        this.consumption = consumption;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", consumption=" + consumption +
                '}';
    }
}
