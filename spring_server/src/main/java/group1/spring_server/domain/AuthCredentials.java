package group1.spring_server.domain;

import group1.spring_server.OutputModel;
import group1.spring_server.exceptions.UnauthorizedException;

public class AuthCredentials extends MyData {


    private final String sessionCode;
    private final boolean noAcess;

    public AuthCredentials(String authHeader) {

        if (authHeader == null) {
            noAcess = true;
            this.sessionCode = null;
        } else {
            noAcess = false;
            this.sessionCode = authHeader.split(" ")[1];
        }

    }

    public String getSessionCode() throws UnauthorizedException {

        if (noAcess) throw new UnauthorizedException();
        return sessionCode;
    }
}
