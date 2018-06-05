package group1.spring_server.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import group1.spring_server.domain.model.MItreidAuthResponse;
import group1.spring_server.exceptions.UnauthorizedException;
import org.springframework.util.Base64Utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

public class BearerToken {

    private  String sessionCode;
    private boolean noAcess;

    private final String clientId ="2c202c7a-5507-469c-89f5-b78f35bbb836";
    private final String clientSecret= "AOT2nGzvICGDGpMMgjnmDHhSMZSkAeCgTnxWMbTXoVLJvpAiPYRPvzkoMHEpw5GmzcHDUOFUwdhJ";



    public BearerToken(String authHeader) throws Exception {

        if (authHeader == null) {
            noAcess = true;
            return;
        }

        String bearerToken = authHeader.split("Bearer ")[1];

        String API_BasicAuth = Base64Utils.encodeToString((clientId + ":" + clientSecret).getBytes());

        HttpsURLConnection con = (HttpsURLConnection) new URL("https://localhost:8080/instrospect/").openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("Authorization","Basic "+ API_BasicAuth);

        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());

        writer.write("token = "+ bearerToken);
        writer.flush();

        String response = con.getResponseMessage();

        MItreidAuthResponse mItreidAuth = new ObjectMapper().readValue(response, MItreidAuthResponse.class);

        if(!mItreidAuth.isActive()){
            this.noAcess = true;
            return;
        }

        this.sessionCode = mItreidAuth.getUser_id();


    }

    public String getSessionCode() throws UnauthorizedException {

        if (noAcess) throw new UnauthorizedException();
        return sessionCode;
    }


}
