package com.labs.errors;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.labs.errors.FieldValueFault")
public class FieldValueException extends Exception {
    private static final long serialVersionUID = -6647544772732631047L;
    private final FieldValueFault fault;

    public FieldValueException(String message, FieldValueFault fault) {
        super(message);
        this.fault = fault;
    }

    public FieldValueException(String message, FieldValueFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public FieldValueFault getFaultInfo() {
        return fault;
    }
}