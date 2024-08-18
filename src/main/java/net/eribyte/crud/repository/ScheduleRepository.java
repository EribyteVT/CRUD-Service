package net.eribyte.crud.repository;

import net.eribyte.crud.entity.ScheduleEntity;
import net.eribyte.crud.entity.ScheduleEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, ScheduleEntityKey> {

    List<ScheduleEntity> findByScheduleEntityKeyGreaterThanAndScheduleEntityKeyLessThan(ScheduleEntityKey start, ScheduleEntityKey end);
}
