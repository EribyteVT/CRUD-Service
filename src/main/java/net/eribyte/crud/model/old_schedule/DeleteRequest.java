package net.eribyte.crud.model.old_schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRequest {
    private String id;
    private String passcode;
}
