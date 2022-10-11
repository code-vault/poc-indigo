package com.avis.qa.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * JacksonUtils class -
 * deserializeJson - deserializes json to POJO object
 * convertPOJOToDataProviderObject - converts POJO to DataProvider Object
 *
 * @author ikumar
 */
public class JacksonUtils {

    public static <T> T deserializeJson(String fileName, Class<T> T) throws IOException {
        InputStream is = JacksonUtils.class.getClassLoader().getResourceAsStream(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(is, T);
    }

    public static Object[][] convertPOJOToDataProviderObject(Object[] objects) {
        Object[][] returnObject = new Object[objects.length][1];
        for (int i = 0; i < objects.length; i++) {
            returnObject[i][0] = objects[i];
        }
        return returnObject;
    }
}
