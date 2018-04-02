package group1.spring_server.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchTemplateItemException extends MyException {
    @Override
    public String getMessage(){
        return "The requested template item doesn't exist in the database ";
    }

    @Override
    public HttpStatus error(){
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String type() {
        return "invalid-id";
    }

    @Override
    public String title() {
        return "Invalid template item ID";
    }

}
