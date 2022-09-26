package com.company.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesObtainer {
    public static Properties getProperties() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        InputStream resourceStream = loader.getResourceAsStream("application.properties");
        properties.load(resourceStream);
        return properties;
    }
}
