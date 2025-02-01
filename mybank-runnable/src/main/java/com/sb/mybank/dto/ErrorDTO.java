package com.sb.mybank.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorDTO {

    private List<String> fieldNamesList = new ArrayList<>();

    private String errorMessage;

    public ErrorDTO() {
    }

    public ErrorDTO(String errorMessage, List<String> fieldNames) {
        this.fieldNamesList = fieldNames;
        this.errorMessage = errorMessage;
    }

    public List<String> getFieldNames() {
        return fieldNamesList;
    }

    public void setFieldNames(List<String> fieldNames) {
        this.fieldNamesList = fieldNames;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
