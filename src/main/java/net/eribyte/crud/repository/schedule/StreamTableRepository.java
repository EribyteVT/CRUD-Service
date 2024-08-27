package net.eribyte.crud.repository.schedule;

import net.eribyte.crud.entity.schedule.StreamTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface StreamTableRepository extends JpaRepository<StreamTableEntity, Integer> {
    List<StreamTableEntity> findByStreamDateGreaterThanAndStreamDateLessThanAndStreamerIdEquals(Timestamp start, Timestamp end, String streamerId);

    StreamTableEntity findByStreamIdEquals(int streamId);
}
