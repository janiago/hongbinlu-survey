package com.example.survey.model;

import java.util.List;

public class SurveyResponse {
    private String surveyName;
    private List<Answer> answers;

    public SurveyResponse() {}

    public SurveyResponse(String surveyName, List<Answer> answers) {
        this.surveyName = surveyName;
        this.answers = answers;
    }

    // Getters and Setters
    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}