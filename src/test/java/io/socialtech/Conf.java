package io.socialtech;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Conf {
    public static Properties prop = new Properties();
    public static String WEB_URL;
    public static String API_URL;

    public static void readProperties(){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("config.properties");
        try {
            prop.load(stream);
            WEB_URL = prop.getProperty("webUrl");
            API_URL = prop.getProperty("apiUrl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
