package net.eribyte.crud.model.schedule;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditStreamRequest {
    Integer streamId;

    String which;

    String newTimestamp;
    String newName;

    String password;

}
