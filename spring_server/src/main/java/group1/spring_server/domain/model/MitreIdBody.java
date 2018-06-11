package group1.spring_server.domain.model;

import java.util.Date;

public class MitreIdBody {

    private boolean active;
    private String scope;
    private Date expires_at;
    private String exp;
    private String sub;
    private String user_id;
    private String client_id;
    private String token_type;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Date getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(Date expires_at) {
        this.expires_at = expires_at;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }






    /*{"active":true,
    "scope":"openid profile email",
    "expires_at":"2018-06-11T16:43:04+0100",
    "exp":1528731784,"sub":"90342.ASDFJWFA"
    ,"user_id":"admin",
    "client_id":"react-web-app",
    "token_type":"Bearer"}
    */

}
