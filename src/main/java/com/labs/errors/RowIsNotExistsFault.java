package com.labs.errors;

public class RowIsNotExistsFault {
    private static final String DEFAULT_MESSAGE = "Row with this rowId is not exists.";
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static RowIsNotExistsFault defaultInstance() {
        RowIsNotExistsFault fault = new RowIsNotExistsFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}