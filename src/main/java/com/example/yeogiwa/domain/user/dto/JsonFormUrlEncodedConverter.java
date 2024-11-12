package com.example.yeogiwa.domain.user.dto;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings("all")
public class JsonFormUrlEncodedConverter<T> extends AbstractHttpMessageConverter<T> {
    /** JSON 매핑 안된 속성 오류 무시 */
    private static final ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final FormHttpMessageConverter converter = new FormHttpMessageConverter();

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.isAnnotationPresent(JsonFormData.class);
    }

    @Override
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException {
        Map<String, String> vals = converter.read(null, inputMessage).toSingleValueMap();

        return objectMapper.convertValue(vals, clazz);
    }

    @Override
    protected void writeInternal(T clazz, HttpOutputMessage outputMessage)
        throws HttpMessageNotWritableException {}
}
