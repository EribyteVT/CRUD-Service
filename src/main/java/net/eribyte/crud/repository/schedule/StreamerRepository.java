package net.eribyte.crud.repository.schedule;


import net.eribyte.crud.entity.schedule.StreamerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamerRepository extends JpaRepository<StreamerEntity, Integer>{


    StreamerEntity findByGuildEquals(String guildId);

    StreamerEntity findByStreamerIdEquals(int streamer_id);
}
