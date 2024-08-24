package net.eribyte.crud.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTwitchToStreamerRequest {
    private int streamerId;
    private String twitchId;
    private String password;
}
