package com.demo.quartz.service;


public interface ObjectMapperWrapper {

    <T> String encodeToJson (T plainObject);

    <T> T decodeFromJson (String jsonText, Class<T> clazz);

}
