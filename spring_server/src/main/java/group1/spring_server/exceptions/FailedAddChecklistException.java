package group1.spring_server.exceptions;

public class FailedAddChecklistException extends Exception{

    @Override
    public String getMessage(){
        return "Failed to add checklist, please try again";
    }

    public int error(){
        return 500;
    }
}
