package com.example.survey.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Question {
    @JsonProperty("text")
    private String text;

    @JsonProperty("type")
    private String type; // single, multiple, open

    @JsonProperty("required")
    private boolean required;

    @JsonProperty("options")
    private List<String> options;

    // Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}