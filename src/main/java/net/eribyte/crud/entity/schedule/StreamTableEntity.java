package net.eribyte.crud.entity.schedule;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema="schedules", name="stream_table")
public class StreamTableEntity {
    @EmbeddedId
    private StreamTableEntityKey streamTableEntityKey;

    @Column(name="stream_name")
    private String streamName;
}
