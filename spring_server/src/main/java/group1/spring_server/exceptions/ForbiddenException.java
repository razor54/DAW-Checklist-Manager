package group1.spring_server.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends MyException {
    @Override
    public String getMessage() {
        return "The request data are not available for the current user";
    }

    @Override
    public HttpStatus error() {

        return HttpStatus.FORBIDDEN;

    }

    @Override
    public String type() {
        return "access-denied";
    }

    @Override
    public String title() {

        return "Not authorized to access requested data";
    }

}
