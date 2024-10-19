package de.hhn.aib3.data;

public enum UrlExtensions {
    DATASET("/v1/dataset"),
    RESULT("/v1/result"),
    HEALTH("/health");

    public final String path;

    UrlExtensions(String extension) {
        this.path = extension;
    }
}
