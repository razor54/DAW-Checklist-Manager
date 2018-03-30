package group1.spring_server.exceptions;

public class ForbiddenException extends MyException {
    @Override
    public String getMessage(){
        return "Not authorized to access requested data";
    }

    public int error(){
        return 403;
    }
}
