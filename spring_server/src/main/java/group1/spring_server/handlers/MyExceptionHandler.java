package group1.spring_server.handlers;

import group1.spring_server.exceptions.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(value = MyException.class)
    ResponseEntity<ErrorModel> handleExceptions(HttpServletRequest request, MyException e) throws IOException {

        ErrorModel error = new ErrorModel(e, request.getRequestURI());

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();

        header.add("Content-Type","application/problem+json");

        return new ResponseEntity<>(error, header,e.error());
    }

    private static class ErrorModel {

        public String type;
        public String title;
        public int status;
        public String detail;


        ErrorModel(MyException e,String uri){
            this.type=uri + "/" + e.type();
            this.title=e.title();
            this.status = e.error().value();
            this.detail=e.getMessage();
        }

    }


}
