package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    public static Properties prop;

    public static void loadConfig() throws IOException {

        prop = new Properties();

        // Read environment from JVM argument, default = dev
        // dev , hotfix , preprod , prod
        String env = System.getProperty("env", "preprod").toLowerCase();

        // Example file names: config-dev.properties, config-test.properties
        String fileName = "config-" + env + ".properties";

        System.out.println("Loading Environment File: " + fileName);

        // Read from classpath: src/test/resources/
        try (InputStream is = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (is == null) {
                throw new FileNotFoundException(fileName + " not found under src/test/resources/");
            }

            prop.load(is);
        }

        // Print loaded values
        System.out.println("---- Loaded Properties ----");
        prop.forEach((k, v) -> System.out.println(k + " = " + v));
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }
}
