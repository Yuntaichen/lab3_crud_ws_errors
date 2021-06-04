package com.labs.errors;

public class FieldValueFault {
    private static final String DEFAULT_MESSAGE = "Field has not one of the required values.";
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static FieldValueFault defaultInstance() {
        FieldValueFault fault = new FieldValueFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}