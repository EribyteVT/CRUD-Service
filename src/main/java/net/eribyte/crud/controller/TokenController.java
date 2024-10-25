package net.eribyte.crud.controller;


import lombok.extern.log4j.Log4j2;
import net.eribyte.crud.Constants.ResponseConstants;
import net.eribyte.crud.entity.tokens.StreamerRefreshToken;
import net.eribyte.crud.model.CrudResponseEntity;
import net.eribyte.crud.model.tokens.AddTokenRequest;
import net.eribyte.crud.model.tokens.GetTokenRequest;
import net.eribyte.crud.repository.tokens.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Log4j2
@RestController
@PreAuthorize("hasIpAddress('10.0.0.6') or hasIpAddress('127.0.0.1') or hasIpAddress('10.0.0.158')")
public class TokenController {

    @Autowired
    TokenRepository repository;

    @Value("${security.delete.code}")
    private String updatePassword;

    @Value("${security.oauth.password}")
    private String getPassword;

    @PostMapping(value = "/token/getToken",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CrudResponseEntity getToken(@RequestBody GetTokenRequest getTokenRequest){
        //always send it via UTC timestamp
        try {
            if(!getPassword.equals(getTokenRequest.getPassword())){
                CrudResponseEntity response = new CrudResponseEntity();
                response.setResponse(ResponseConstants.FORBIDDEN_RESPONSE);
                return response;
            }

            StreamerRefreshToken streamerRefreshToken = repository.findByTwitchIdEquals(getTokenRequest.getTwitchId());
            CrudResponseEntity response = new CrudResponseEntity();

            response.setResponse(ResponseConstants.OKAY_RESPONSE);
            response.setData(streamerRefreshToken);

            return response;


        }
        catch (Exception e){
            e.printStackTrace();
            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.ERROR_RESPONSE);
            return response;
        }
    }


    @PostMapping(value = "/token/updateToken",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CrudResponseEntity updateToken(@RequestBody AddTokenRequest addTokenRequest){
        //always send it via UTC timestamp
        try {
            if(!updatePassword.equals(addTokenRequest.getPassword())){
                CrudResponseEntity response = new CrudResponseEntity();
                response.setResponse(ResponseConstants.FORBIDDEN_RESPONSE);
                return response;
            }

            StreamerRefreshToken streamerRefreshToken = new StreamerRefreshToken();

            streamerRefreshToken.setTwitchId(addTokenRequest.getTwitch_id());
            streamerRefreshToken.setRefreshToken(addTokenRequest.getRefresh_token());
            streamerRefreshToken.setRefreshSalt(addTokenRequest.getRefresh_salt());
            streamerRefreshToken.setAccessToken(addTokenRequest.getAccess_token());
            streamerRefreshToken.setAccessSalt(addTokenRequest.getAccess_salt());

            StreamerRefreshToken updatedToken = repository.save(streamerRefreshToken);

            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.OKAY_RESPONSE);
            response.setData(updatedToken);

            return response;

        }
        catch (Exception e){
            e.printStackTrace();
            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.ERROR_RESPONSE);
            return response;
        }
    }

}
