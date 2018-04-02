package group1.spring_server.exceptions;

import group1.spring_server.OutputModel;
import org.springframework.http.HttpStatus;


//error object
public abstract class MyException extends Exception implements OutputModel {

    public abstract HttpStatus error();

    public abstract String type();

    public abstract String title();


}
