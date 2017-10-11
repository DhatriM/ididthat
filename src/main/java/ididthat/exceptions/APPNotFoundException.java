package ididthat.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Create 500
 */
public class APPNotFoundException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public APPNotFoundException() {
        this("Resource Not Found", "");
    }

    public APPNotFoundException(String msg, String desc) {
        super(Response.status(Status.NOT_FOUND).entity(
                new APPExceptionInfo(Status.NOT_FOUND.getStatusCode(), msg, desc)
        ).type("application/json").build());
    }

}

