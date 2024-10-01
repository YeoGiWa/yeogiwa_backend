package com.example.yeogiwa.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QRUtil {
    private final String baseUrl;

    public QRUtil(
        @Value("${spring.application.base-url}") String baseUrl
    ) {
        this.baseUrl = baseUrl;
    }

    public byte[] createAmbassadorQR() {

        return null;
    }
}
