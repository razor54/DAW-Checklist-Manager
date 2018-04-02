package group1.spring_server.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends MyException{

    @Override
    public String getMessage(){
        return "To have access the requested data it's necessary to be logged";
    }

    @Override
    public HttpStatus error(){

        return HttpStatus.FORBIDDEN;

    }

    @Override
    public String type() {
        return "not-authenticated";
    }

    @Override
    public String title() {

        return "Necessary authentication";
    }

}
