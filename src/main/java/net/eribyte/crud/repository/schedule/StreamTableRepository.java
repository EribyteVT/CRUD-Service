package net.eribyte.crud.repository.schedule;

import net.eribyte.crud.entity.schedule.StreamTableEntity;
import net.eribyte.crud.entity.schedule.StreamTableEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface StreamTableRepository extends JpaRepository<StreamTableEntity, StreamTableEntityKey> {
    List<StreamTableEntity> findByStreamTableEntityKeyStreamDateGreaterThanAndStreamTableEntityKeyStreamDateLessThanAndStreamTableEntityKeyStreamerIdEquals(Timestamp start, Timestamp end,String streamerId);

}
