package net.eribyte.crud.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class StreamTableEntityKey {
    @Column(name="stream_date")
    private Timestamp streamDate;

    @Column(name="streamer_id")
    private String streamerId;
}
