package group1.spring_server.exceptions;

import group1.spring_server.OutputModel;


//error object
public abstract class MyException extends Exception implements OutputModel {

    public abstract int error();
    
}
