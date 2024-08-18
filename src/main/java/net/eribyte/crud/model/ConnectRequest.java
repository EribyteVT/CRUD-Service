package net.eribyte.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectRequest {
    private String discordId;
    private String serviceId;

    String password;
}
