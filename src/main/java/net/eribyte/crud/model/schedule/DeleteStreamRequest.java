package net.eribyte.crud.model.schedule;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteStreamRequest {
    Integer streamId;

    String password;
}
