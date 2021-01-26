package com.demo.quartz.service.impl;

import com.demo.quartz.service.ObjectMapperWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ObjectMapperWrapperImpl implements ObjectMapperWrapper {

    @Override
    public <T> String encodeToJson(T plainObject) {
        try {
            return new ObjectMapper().writeValueAsString(plainObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T decodeFromJson(String jsonText, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(jsonText, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
