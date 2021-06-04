package com.labs.errors;

public class CastToIntFault {
    private static final String DEFAULT_MESSAGE = "Some parameters should be converted to int!";
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static CastToIntFault defaultInstance() {
        CastToIntFault fault = new CastToIntFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}