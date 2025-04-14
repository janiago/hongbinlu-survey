package com.example.survey.model;

import java.util.List;

public class Answer {
    private String questionText;
    private List<String> values;

    public Answer() {}

    public Answer(String questionText, List<String> values) {
        this.questionText = questionText;
        this.values = values;
    }

    // Getters and Setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}