package group1.spring_server.exceptions;

public class FailedAddCheklistItemException extends Exception{

    @Override
    public String getMessage(){
        return "Failed to add checklist item, please try again";
    }

    public int error(){
        return 500;
    }
}