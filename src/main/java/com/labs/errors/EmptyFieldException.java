package com.labs.errors;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.labs.errors.EmptyFieldFault")
public class EmptyFieldException extends Exception {

    private static final long serialVersionUID = -6647544772732631047L;
    private final EmptyFieldFault fault;

    public EmptyFieldException(String message, EmptyFieldFault fault) {
        super(message);
        this.fault = fault;
    }

    public EmptyFieldException(String message, EmptyFieldFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public EmptyFieldFault getFaultInfo() {
        return fault;
    }

}