package com.example.yeogiwa.config;

import com.example.yeogiwa.domain.user.dto.JsonFormUrlEncodedConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class JsonFormUrlEncodedConverterConfiguration implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        JsonFormUrlEncodedConverter<?> converter = new JsonFormUrlEncodedConverter<>();
        MediaType mediaType = new MediaType(MediaType.APPLICATION_FORM_URLENCODED, StandardCharsets.UTF_8);
        converter.setSupportedMediaTypes(List.of(mediaType));
        converters.add(converter);
    }
}
