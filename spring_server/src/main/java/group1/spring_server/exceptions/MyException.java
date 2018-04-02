package group1.spring_server.exceptions;

import org.springframework.http.HttpStatus;


//error object
public abstract class MyException extends Exception {

    public abstract HttpStatus error();

    public abstract String type();

    public abstract String title();


}
