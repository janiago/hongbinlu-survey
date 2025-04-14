package com.example.survey.controller;

import com.example.survey.model.Config;
import com.example.survey.model.Survey;
import com.example.survey.model.SurveyResponse;
import com.example.survey.service.QRCodeService;
import com.example.survey.service.SurveyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping("/survey")
    public String uploadSurvey(@RequestParam("file") MultipartFile file) throws Exception {
        surveyService.saveSurvey(file);
        return "Survey uploaded successfully";
    }

    @GetMapping("/surveys")
    public List<String> getSurveys() throws Exception {
        return surveyService.getSurveyNames();
    }

    @GetMapping("/survey/{name}")
    public Survey getSurvey(@PathVariable String name) throws Exception {
        return surveyService.getSurvey(name);
    }

    @PostMapping("/response")
    public String submitResponse(@RequestBody SurveyResponse response) throws Exception {
        surveyService.saveResponse(response);
        return "Response submitted successfully";
    }

    @GetMapping("/responses/{surveyName}")
    public List<SurveyResponse> getResponses(@PathVariable String surveyName) throws Exception {
        return surveyService.getResponses(surveyName);
    }

    @GetMapping("/responses")
    public List<SurveyResponse> getAllResponses() throws Exception {
        return surveyService.getAllResponses();
    }

    @GetMapping(value = "/download/{surveyName}")
    public ResponseEntity<Resource> downloadResponses(@PathVariable String surveyName) throws Exception {
        List<SurveyResponse> responses = surveyService.getResponses(surveyName);
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonBytes = objectMapper.writeValueAsBytes(responses);

        // URL encode filename to support non-ASCII characters
        // String encodedFileName = java.net.URLEncoder.encode(surveyName +
        // "_responses.json", "UTF-8").replace("+",
        // "%20");
        // String disposition = String.format("attachment; filename=\"%s\";
        // filename*=UTF-8''%s",
        // surveyName + "_responses.json", encodedFileName);

        ByteArrayResource resource = new ByteArrayResource(jsonBytes);
        return ResponseEntity.ok()
                // .header(HttpHeaders.CONTENT_DISPOSITION, disposition)
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }

    @PostMapping("/config/ip")
    public String saveIpAddress(@RequestBody Config config) throws Exception {
        surveyService.saveIpAddress(config.getIpAddress());
        return "IP address saved successfully";
    }

    @GetMapping("/config/ip")
    public Config getIpAddress() throws Exception {
        return new Config(surveyService.getIpAddress());
    }

    @GetMapping(value = "/qrcode/{surveyName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getQRCode(@PathVariable String surveyName) throws Exception {
        return qrCodeService.generateQRCode(surveyName);
    }

    @DeleteMapping("/survey/{surveyName}")
    public String deleteSurvey(@PathVariable String surveyName) throws Exception {
        surveyService.deleteSurvey(surveyName);
        return "Survey deleted successfully";
    }

}