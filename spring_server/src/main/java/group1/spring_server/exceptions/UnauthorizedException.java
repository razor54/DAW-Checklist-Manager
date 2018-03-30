package group1.spring_server.exceptions;

public class UnauthorizedException extends MyException{

    @Override
    public String getMessage(){
        return "Necessary Authentication";
    }

    public int error(){
        return 401;
    }
}
