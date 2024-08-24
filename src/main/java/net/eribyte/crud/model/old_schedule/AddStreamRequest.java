package net.eribyte.crud.model.old_schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStreamRequest {
    String timestamp;
    String streamName;
    String description;

    String password;
}
