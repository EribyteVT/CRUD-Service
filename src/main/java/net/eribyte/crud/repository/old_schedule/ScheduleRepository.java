package net.eribyte.crud.repository.old_schedule;

import net.eribyte.crud.entity.old_schedule.ScheduleEntity;
import net.eribyte.crud.entity.old_schedule.ScheduleEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, ScheduleEntityKey> {

    List<ScheduleEntity> findByScheduleEntityKeyGreaterThanAndScheduleEntityKeyLessThan(ScheduleEntityKey start, ScheduleEntityKey end);
}
