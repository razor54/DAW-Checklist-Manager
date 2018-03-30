package group1.spring_server.exceptions;


public class NoSuchChecklistException extends MyException {


    @Override
    public String getMessage(){
        return "Invalid Checklist ID";
    }

    public int error(){
        return 404;
    }
}
