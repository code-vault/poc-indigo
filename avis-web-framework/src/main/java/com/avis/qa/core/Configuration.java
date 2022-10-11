package com.avis.qa.core;

import lombok.extern.log4j.Log4j2;
import org.testng.xml.XmlTest;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Class contains all the properties required to configure the driver
 *
 * @author ikumar
 */
@Log4j2
public class Configuration {

    /* Browser Configuration */
    public static String BROWSER;
    public static String ENVIRONMENT;
    public static String BRAND;
    public static String DOMAIN;
    public static String URL;
    public static String DOCKER;
    private static Properties prop;
    /* Driver Configuration */
    public static final long DEFAULT_IMPLICIT_TIMEOUT = Long.parseLong(getValue("timeout.implicit"));
    public static final long DEFAULT_EXPLICIT_TIMEOUT = Long.parseLong(getValue("timeout.explicit"));

    private static Properties getProp() {
        if (prop == null) {
            prop = new Properties();
            InputStream input;
            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                input = loader.getResourceAsStream("config.properties");
                //input = new FileInputStream(new File("config.properties"));
                prop.load(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return prop;
    }

    /**
     * Initially checks for any environment variable. If null
     * Checks for the property value which is set either from the terminal/console. If null
     * Gets the default value from the config file
     *
     * @param propertyName Property Key set from Environment variables/Maven command line/config
     * @return propertyValue
     */
    public static String getValue(String propertyName) {
        String propertyValue = System.getenv(propertyName);
        log.debug("Environment variable for [" + propertyName + "] = [" + propertyValue + "]");

        if (Objects.isNull(propertyValue)) {
            propertyValue = Objects.isNull(System.getProperty(propertyName)) ? getProp().getProperty(propertyName) : System.getProperty(propertyName);
        }
        log.info("[" + propertyName + "] value set to [" + propertyValue + "]");
        return propertyValue;
    }

    public static void setTestNGParameters(XmlTest xmlTest) {
        Map<String, String> XML_PARAMS_MAP = xmlTest.getAllParameters();
        // Assigning all the XML parameters to the Base Class Global variables
        BRAND = XML_PARAMS_MAP.get("Brand") != null ? XML_PARAMS_MAP.get("brand") : getValue("brand");
        ENVIRONMENT = XML_PARAMS_MAP.get("environment") != null ? XML_PARAMS_MAP.get("environment") : getValue("environment");
        DOMAIN = XML_PARAMS_MAP.get("domain") != null ? XML_PARAMS_MAP.get("domain") : getValue("domain");
        BROWSER = XML_PARAMS_MAP.get("browser") != null ? XML_PARAMS_MAP.get("browser") : getValue("browser");
        DOCKER = XML_PARAMS_MAP.get("docker") != null ? XML_PARAMS_MAP.get("docker") : getValue("docker");
    }

    public static void setURL() {
        URL = getValue(ENVIRONMENT + "_" + DOMAIN + "_url");
    }
}
