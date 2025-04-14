package com.example.survey.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class QRCodeService {

    @Autowired
    private SurveyService surveyService;

    public byte[] generateQRCode(String surveyName) throws Exception {
        String ipAddress = surveyService.getIpAddress();
        // URL encode surveyName to handle non-ASCII characters (e.g., Chinese)
        String encodedSurveyName = URLEncoder.encode(surveyName, StandardCharsets.UTF_8.toString());
        String url = "http://" + ipAddress + ":8080/survey.html?name=" + encodedSurveyName;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}