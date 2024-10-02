package com.example.yeogiwa.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Component
public class QRUtil {
    private final int width = 200;
    private final int height = 200;
    private final String baseUrl;

    public QRUtil(
        @Value("${spring.application.base-url}") String baseUrl
    ) {
        this.baseUrl = baseUrl;
    }

    public byte[] createAmbassadorQR(Long ambassadorId) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(baseUrl + "/ambassador/" + ambassadorId + "/promote", BarcodeFormat.QR_CODE, width, height);
            BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", stream);
            stream.flush();

            byte[] qrCodeBytes = stream.toByteArray();
            stream.close();
            return qrCodeBytes;
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400));
        }
    }
}
