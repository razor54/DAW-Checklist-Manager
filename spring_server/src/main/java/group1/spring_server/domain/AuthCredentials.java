package group1.spring_server.domain;

import group1.spring_server.exceptions.UnauthorizedException;

public class AuthCredentials {




    private final String sessionCode;
    private final boolean noAcess;

    public AuthCredentials(String authHeader){

        if(authHeader==null) {
            noAcess = false;
            this.sessionCode = null;
        }
        else{
            noAcess = true;
            this.sessionCode = authHeader.split(" ")[1];
        }

    }

    public String getSessionCode() throws UnauthorizedException {

        if(noAcess) throw new UnauthorizedException();
        return sessionCode;
    }
}
