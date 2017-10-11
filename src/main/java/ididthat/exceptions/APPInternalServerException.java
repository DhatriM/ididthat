package ididthat.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.WebApplicationException;

/**
 * Create 500
 */
public class APPInternalServerException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public APPInternalServerException() {
        this("Internal Server Error", "");
    }

    public APPInternalServerException(String msg, String desc) {
        super(Response.status(Status.INTERNAL_SERVER_ERROR).entity(
                new APPExceptionInfo(Status.INTERNAL_SERVER_ERROR.getStatusCode(), msg, desc)
        ).type("application/json").build());
    }

}