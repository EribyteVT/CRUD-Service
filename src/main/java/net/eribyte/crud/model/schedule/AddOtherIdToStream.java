package net.eribyte.crud.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOtherIdToStream {
    private int streamId;

    private String serviceName;

    private String twitchStreamId;
    private String discordEventId;


    String password;
}
