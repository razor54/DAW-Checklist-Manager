package group1.spring_server.exceptions;

public class NoSuchUserException extends Exception{

    @Override
    public String getMessage(){
        return "Invalid User ID";
    }

    public int error(){
        return 404;
    }
}
