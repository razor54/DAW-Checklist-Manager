package group1.spring_server.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import group1.spring_server.domain.model.MitreIdBody;
import group1.spring_server.exceptions.UnauthorizedException;
import org.springframework.util.Base64Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class BearerToken {

    private  String sessionCode;
    private boolean noAcess;

    private final String clientId ="ab866e69-755c-4ab1-a859-ef42d71cb0df";
    private final String clientSecret= "AMsb-sf0Wg5zgPB1DKs9vDRzo0AfcNGBkP-Oi5jBTe2Jrpkjr0GrATZ7hhu8lTZRP1AmjVoAjm8ZZy3yqE24pNk";



    public BearerToken(String authHeader) throws Exception {

        if (authHeader == null) {
            noAcess = true;
            return;
        }

        String bearerToken = authHeader.split("Bearer ")[1];

        String basicAuth = Base64Utils.encodeToString((clientId + ":" + clientSecret).getBytes());

        HttpURLConnection connection = (HttpURLConnection) new URL("http://35.228.51.77/openid-connect-server-webapp/introspect").openConnection();

        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects( false );
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        connection.setRequestProperty("Authorization","Basic "+ basicAuth);
        connection.setUseCaches( false );


        try( DataOutputStream writer = new DataOutputStream( connection.getOutputStream())) {
            writer.writeBytes("token="+ bearerToken + "&token_type_hint=access_token");
            writer.flush();


            String response = connection.getResponseMessage();

            int code = connection.getResponseCode();

            if(code!=200){
                throw new Exception(code + "-" + response);
            }


        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        StringBuilder body = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){

            String inputLine;

            while((inputLine= reader.readLine())!=null){ body.append(inputLine); }
        }

        String bodyString = body.toString();

        MitreIdBody properties = new ObjectMapper().readValue(bodyString, MitreIdBody.class);

        if(!properties.isActive()){
            this.noAcess = true;
            return;
        }

        this.sessionCode = properties.getUser_id();
    }

    public String getSessionCode() throws UnauthorizedException {

        if (noAcess) throw new UnauthorizedException();
        return sessionCode;
    }

}
