package net.eribyte.crud.entity.old_schedule;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema="Website", name="schedule")
public class ScheduleEntity {

    @EmbeddedId
    private ScheduleEntityKey scheduleEntityKey;

    @Column(name="Stream Name")
    private String streamName;

    @Column(name="description")
    private String description;
}
