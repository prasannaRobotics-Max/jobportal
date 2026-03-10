package com.example.JobPortal.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EducationDto {
    private long id;
    @NotBlank(message = "levelOfStudy is must")
    private String levelOfStudy;
    @NotNull(message = "totalMarks cannot be Null")
    private double totalMarks;
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "yearOfCompletion cannot be blank and must")
    private String yearOfCompletion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLevelOfStudy() {
        return levelOfStudy;
    }

    public void setLevelOfStudy(String levelOfStudy) {
        this.levelOfStudy = levelOfStudy;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearOfCompletion() {
        return yearOfCompletion;
    }

    public void setYearOfCompletion(String yearOfCompletion) {
        this.yearOfCompletion = yearOfCompletion;
    }
}
