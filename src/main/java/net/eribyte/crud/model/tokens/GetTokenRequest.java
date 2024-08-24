package net.eribyte.crud.model.tokens;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTokenRequest {
    private Integer twitchId;
    private String password;

}
