package net.eribyte.crud.model.schedule;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditStreamRequest {
    String timestamp;
    String streamName;
    String streamerId;

    String newTimestamp;
    String newName;

    String password;

}
