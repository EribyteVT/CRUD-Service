package net.eribyte.crud.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStreamTableEntryRequest {
    String timestamp;
    String streamName;
    String streamerId;

    String password;
}
