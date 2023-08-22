package com.epam;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class StaticData {

    public static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper1 = new ObjectMapper();
        return objectMapper1.writeValueAsString(obj);
    }
    public static  <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper1 = new ObjectMapper();
        return objectMapper1.readValue(json, clazz);
    }


}
