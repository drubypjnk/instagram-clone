package com.example.prm392_project.DTO;

public class TestDTO {
    String date;
    Integer temperatureC;
    Integer temperatureF;
    String summary;

    public TestDTO() {
    }

    public TestDTO(String date, Integer temperatureC, Integer temperatureF, String summary) {
        this.date = date;
        this.temperatureC = temperatureC;
        this.temperatureF = temperatureF;
        this.summary = summary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(Integer temperatureC) {
        this.temperatureC = temperatureC;
    }

    public Integer getTemperatureF() {
        return temperatureF;
    }

    public void setTemperatureF(Integer temperatureF) {
        this.temperatureF = temperatureF;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
