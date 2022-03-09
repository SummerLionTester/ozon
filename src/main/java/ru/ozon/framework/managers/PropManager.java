package ru.ozon.framework.managers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropManager {
    private final Properties properties= new Properties();
    private static PropManager propManager;

    private PropManager() {
        loadApplicationProperties();
        loadCustomProperties();
    }

    public static PropManager getInstance() {
        if (propManager == null) {
            propManager = new PropManager();
        }

        return propManager;
    }

    private void loadApplicationProperties() {
        try {
            properties.load(new FileInputStream("src/main/resources/" +
                    System.getProperty("propFile", "application") + ".properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomProperties() {
        properties.forEach((key, value) -> System.getProperties()
                .forEach((customUserKey, customUserValue) -> {
                    if (key.toString().equals(customUserKey.toString()) &&
                            !value.toString().equals(customUserValue.toString())) {
                        properties.setProperty(key.toString(), customUserValue.toString());
                    }
                }));
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
