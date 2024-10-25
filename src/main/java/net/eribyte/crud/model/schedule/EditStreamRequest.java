package net.eribyte.crud.model.schedule;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditStreamRequest {
    Integer streamId;

    List<String> which;

    String newTimestamp;
    String newName;
    Integer newDuration;

    String password;

}
