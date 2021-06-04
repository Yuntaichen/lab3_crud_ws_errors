package com.labs.errors;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.labs.errors.CRUDServiceCreateStudentEmptyFieldFault")
public class EmptyFieldException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;
    private final CRUDServiceCreateStudentEmpryFieldFault fault;

    public EmptyFieldException(String message, CRUDServiceCreateStudentEmpryFieldFault fault) {
        super(message);
        this.fault = fault;
    }

    public EmptyFieldException(String message, CRUDServiceCreateStudentEmpryFieldFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public CRUDServiceCreateStudentEmpryFieldFault getFaultInfo() {
        return fault;
    }

}