package com.labs.errors;

public class CRUDServiceCreateStudentEmpryFieldFault {
    private static final String DEFAULT_MESSAGE = "method createStudent() cannot have empty arguments";
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static CRUDServiceCreateStudentEmpryFieldFault defaultInstance() {
        CRUDServiceCreateStudentEmpryFieldFault fault = new CRUDServiceCreateStudentEmpryFieldFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }

}