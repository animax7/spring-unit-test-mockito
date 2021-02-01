package com.bharatpe.tests.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class JsonUtils {

    public static <T> T jsonFile2Object(String fileName, Class<T> clazz)
            throws  IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(new File(concatenate(fileName)), clazz);
    }

    private static String concatenate(String fileName) {
        return "src/test/resources/"+fileName;
    }

}
