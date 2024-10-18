package de.hhn.aib3;


import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Properties;

/**
 * Represents a property manager to get and set properties from .properties - files
 */
public class PropertyManager {
    private static PropertyManager instance;
    private final Properties properties;
    private static final String CONNECTION_PROPERTIES_PATH = "src\\main\\resources\\connection.properties";

    /**
     * Returns the only instance of this class
     *
     * @return PropertyManager
     */
    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    /**
     * Loads the property-files
     */
    private PropertyManager() {
        properties = new Properties();
        try {
            properties.load(new FileReader(CONNECTION_PROPERTIES_PATH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the value of a given property key
     *
     * @param key the property to get
     * @return The value of the given property
     * @throws InvalidKeyException If the property does not exist
     */
    public String getProperty(String key) throws InvalidKeyException {
        if (!properties.containsKey(key)) {
            throw new InvalidKeyException("Property \"" + key + "\" does not exist.");
        }
        return properties.getProperty(key);
    }
}