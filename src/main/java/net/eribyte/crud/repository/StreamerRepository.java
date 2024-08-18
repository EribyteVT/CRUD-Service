package net.eribyte.crud.repository;


import net.eribyte.crud.entity.ConnectionTableEntity;
import net.eribyte.crud.entity.StreamerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamerRepository extends JpaRepository<StreamerEntity, Integer>{


    StreamerEntity findByGuildEquals(String guildId);
}
