package group1.spring_server.exceptions;

import org.springframework.http.HttpStatus;

public class FailedAddCheklistItemException extends MyException{

    @Override
    public String getMessage(){
        return "We have found an error in our server database, please try again";
    }

    @Override
    public HttpStatus error(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String type() {
        return "checklist-item-not-added";
    }

    @Override
    public String title() {

        return "Failed to add checklist item";
    }


}
