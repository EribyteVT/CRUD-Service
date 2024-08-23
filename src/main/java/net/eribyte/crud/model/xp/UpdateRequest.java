package net.eribyte.crud.model.xp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {
    private String id;

    private int xp;

    private boolean updateTime;

    private long newTime;

    String password;
}
