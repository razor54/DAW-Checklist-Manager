package group1.spring_server.exceptions;

public class FailedAddUserException extends MyException {

    @Override
    public String getMessage(){
        return "Failed to add user, please try again";
    }

    public int error(){
        return 500;
    }
}
