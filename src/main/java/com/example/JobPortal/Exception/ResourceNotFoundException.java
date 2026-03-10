package com.example.JobPortal.Exception;

public class ResourceNotFoundException extends RuntimeException{
    private String field;
    private Long fieldValue;
    private String resourceName;
    private String resourceCode;

    public ResourceNotFoundException(String field, Long fieldValue, String resourceName, String resourceCode) {
        super(String.format("%s is not found with %s:%s",field,fieldValue,resourceName));
        this.field = field;
        this.fieldValue = fieldValue;
        this.resourceName = resourceName;
        this.resourceCode = resourceCode;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Long getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Long fieldValue) {
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
