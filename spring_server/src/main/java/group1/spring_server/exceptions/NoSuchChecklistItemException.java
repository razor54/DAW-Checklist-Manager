package group1.spring_server.exceptions;

public class NoSuchChecklistItemException extends Exception{

    @Override
    public String getMessage(){
        return "Invalid ChecklistItem ID";
    }

    public int error(){
        return 404;
    }
}
