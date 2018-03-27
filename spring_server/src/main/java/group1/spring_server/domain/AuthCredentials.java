package group1.spring_server.domain;

public class AuthCredentials {




    private final String sessionCode;

    public AuthCredentials(String authHeader){

        this.sessionCode = authHeader.split(" ")[1];


    }

    public String getSessionCode() {
        return sessionCode;
    }
}
