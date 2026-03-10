package com.example.JobPortal.Exception;

public class MatchingJobsNotFoundException extends RuntimeException{
    private String field;
    private String fieldValue;
    private String resourceName;
    private String resourceCode;
    private String field1value;

    public MatchingJobsNotFoundException(String field, String fieldValue,String field1value, String resourceName, String resourceCode) {
        super(String.format("%s is not found with %s:%s",field,fieldValue,resourceName));
        this.field = field;
        this.fieldValue = fieldValue;
        this.field1value=field1value;
        this.resourceName = resourceName;
        this.resourceCode = resourceCode;
    }

    public String getField1value() {
        return field1value;
    }

    public void setField1value(String field1value) {
        this.field1value = field1value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }
}
