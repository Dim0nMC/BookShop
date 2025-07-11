package com.example.bookshop.controller.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class JacksonObjectMapper extends ObjectMapper {
    private static final ObjectMapper MAPPER = new JacksonObjectMapper();

    private JacksonObjectMapper() {
        //    https://stackoverflow.com/a/46947975/548473
        registerModule(new Hibernate5JakartaModule()); //don`t serialize lazy collections
        registerModule(new JavaTimeModule()); //serialize DateTime as normal format ('yyyy-MM-dd') ???
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE); //disable all accessors ???
        setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY); //enable only access via getters ???
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
