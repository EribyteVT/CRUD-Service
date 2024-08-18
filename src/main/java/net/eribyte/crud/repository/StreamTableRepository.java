package net.eribyte.crud.repository;

import net.eribyte.crud.entity.StreamTableEntity;
import net.eribyte.crud.entity.StreamTableEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface StreamTableRepository extends JpaRepository<StreamTableEntity, StreamTableEntityKey> {
    List<StreamTableEntity> findByStreamTableEntityKeyStreamDateGreaterThanAndStreamTableEntityKeyStreamDateLessThanAndStreamTableEntityKeyStreamerIdEquals(Timestamp start, Timestamp end,String streamerId);

}
