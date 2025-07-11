package com.example.bookshop.config;

import com.example.bookshop.controller.json.JacksonObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
//@EnableWebMvc !!!
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        messageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        messageConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("text", "plain", StandardCharsets.UTF_8),
                new MediaType("text", "html", StandardCharsets.UTF_8),
                MediaType.APPLICATION_JSON, MediaType.ALL));
        converters.add(messageConverter);

        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>(jsonConverter.getSupportedMediaTypes());
        supportedMediaTypes.add(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
        jsonConverter.setObjectMapper(JacksonObjectMapper.getMapper());
        converters.add(jsonConverter);
    }
}