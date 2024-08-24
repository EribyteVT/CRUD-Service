package net.eribyte.crud.model.tokens;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTokenRequest {
    private Integer twitch_id;
    private String refresh_token;
    private String refresh_salt;
    private String access_token;
    private String access_salt;
    private String password;
}