package ididthat.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Create 500
 */
public class APPBadRequestException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public APPBadRequestException() {
        this("Bad Request. Fix and Retry", "");
    }

    public APPBadRequestException(String msg, String desc) {
        super(Response.status(Status.BAD_REQUEST).entity(
                new APPExceptionInfo(Status.BAD_REQUEST.getStatusCode(), msg, desc)
        ).type("application/json").build());
    }

}