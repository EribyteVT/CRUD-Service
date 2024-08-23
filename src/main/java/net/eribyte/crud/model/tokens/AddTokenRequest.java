package net.eribyte.crud.model.tokens;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTokenRequest {
    private Integer streamer_id;
    private String refresh_token;
    private String salt;
    private String password;

}