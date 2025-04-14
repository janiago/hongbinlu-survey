package com.example.survey.service;

import com.example.survey.model.Config;
import com.example.survey.model.Survey;
import com.example.survey.model.SurveyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {
    private final String SURVEY_DIR = "surveys/";
    private final String RESPONSE_DIR = "responses/";
    private final String CONFIG_DIR = "config/";
    private final String IP_CONFIG_FILE = CONFIG_DIR + "ip.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SurveyService() {
        // Ensure directories exist
        new File(SURVEY_DIR).mkdirs();
        new File(RESPONSE_DIR).mkdirs();
        new File(CONFIG_DIR).mkdirs();
    }

    public void saveSurvey(MultipartFile file) throws IOException {
        // String fileName = file.getOriginalFilename();
        Survey survey = objectMapper.readValue(file.getInputStream(), Survey.class);
        objectMapper.writeValue(new File(SURVEY_DIR + survey.getName() + ".json"), survey);
    }

    public List<String> getSurveyNames() throws IOException {
        // File dir = new File(SURVEY_DIR);
        return Files.list(Paths.get(SURVEY_DIR))
                .map(Path::getFileName)
                .map(Path::toString)
                .map(name -> name.replace(".json", ""))
                .collect(Collectors.toList());
    }

    public Survey getSurvey(String name) throws IOException {
        File file = new File(SURVEY_DIR + name + ".json");
        if (!file.exists()) {
            throw new FileNotFoundException("Survey not found: " + name);
        }
        return objectMapper.readValue(file, Survey.class);
    }

    public void saveResponse(SurveyResponse response) throws IOException {
        File dir = new File(RESPONSE_DIR + response.getSurveyName());
        dir.mkdirs();
        String fileName = RESPONSE_DIR + response.getSurveyName() + "/" + System.currentTimeMillis() + ".json";
        objectMapper.writeValue(new File(fileName), response);
    }

    public List<SurveyResponse> getResponses(String surveyName) throws IOException {
        List<SurveyResponse> responses = new ArrayList<>();
        File dir = new File(RESPONSE_DIR + surveyName);
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                responses.add(objectMapper.readValue(file, SurveyResponse.class));
            }
        }
        return responses;
    }

    public List<SurveyResponse> getAllResponses() throws IOException {
        List<SurveyResponse> responses = new ArrayList<>();
        File dir = new File(RESPONSE_DIR);
        for (File surveyDir : dir.listFiles()) {
            if (surveyDir.isDirectory()) {
                for (File file : surveyDir.listFiles()) {
                    responses.add(objectMapper.readValue(file, SurveyResponse.class));
                }
            }
        }
        return responses;
    }

    public void saveIpAddress(String ipAddress) throws IOException {
        Config config = new Config(ipAddress);
        objectMapper.writeValue(new File(IP_CONFIG_FILE), config);
    }

    public String getIpAddress() throws IOException {
        File file = new File(IP_CONFIG_FILE);
        if (!file.exists()) {
            return "localhost"; // Default to localhost if no IP is configured
        }
        Config config = objectMapper.readValue(file, Config.class);
        return config.getIpAddress();
    }

    public void deleteSurvey(String surveyName) throws IOException {
        // Delete survey JSON file
        File surveyFile = new File(SURVEY_DIR + surveyName + ".json");
        if (surveyFile.exists()) {
            Files.delete(surveyFile.toPath());
        }

        // Delete responses directory
        File responseDir = new File(RESPONSE_DIR + surveyName);
        if (responseDir.exists() && responseDir.isDirectory()) {
            Files.walk(responseDir.toPath())
                    .sorted((a, b) -> b.compareTo(a)) // Delete files before directories
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to delete: " + path, e);
                        }
                    });
        }
    }
}