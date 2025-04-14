package com.example.survey.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Survey {
    @JsonProperty("name")
    private String name;

    @JsonProperty("questions")
    private List<Question> questions;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
