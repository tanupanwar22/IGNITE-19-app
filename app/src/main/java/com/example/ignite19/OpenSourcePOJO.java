package com.example.ignite19;

public class OpenSourcePOJO {
    private String projectName;
    private String projectCode;
    private String projectLicense;

    OpenSourcePOJO(){

    }

    public OpenSourcePOJO(String projectName, String projectCode, String projectLicense) {
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.projectLicense = projectLicense;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public String getProjectLicense() {
        return projectLicense;
    }
}
