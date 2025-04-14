package com.example.survey.model;

public class Config {
    private String ipAddress;

    public Config() {
    }

    public Config(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
