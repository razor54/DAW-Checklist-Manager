package group1.spring_server.domain.model;

public class MItreidAuthResponse {

    private String user_id;
    private boolean active;


    public MItreidAuthResponse(String user_id, boolean active){

        this.active = active;
        this.user_id = user_id;

    }

    public String getUser_id() {
        return user_id;
    }

    public boolean isActive() {
        return active;
    }
}
