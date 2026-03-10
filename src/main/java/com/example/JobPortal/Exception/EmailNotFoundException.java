package com.example.JobPortal.Exception;

public class EmailNotFoundException extends RuntimeException{

    private String field;
    private String fieldValue;
    private String resource;
    private String resourceCode;

    public EmailNotFoundException(String field, String fieldValue, String resource, String resourceCode) {
        super(String.format("%s is not found with %s:%s",resource,field,fieldValue));
        this.field = field;
        this.fieldValue = fieldValue;
        this.resource = resource;
        this.resourceCode = resourceCode;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }
}
