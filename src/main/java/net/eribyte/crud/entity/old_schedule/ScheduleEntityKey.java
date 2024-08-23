package net.eribyte.crud.entity.old_schedule;

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
public class ScheduleEntityKey {
    @Column(name="Date")
    private Timestamp date;
}
