package net.eribyte.crud.repository.xp;

import net.eribyte.crud.entity.xp.ConnectionTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<ConnectionTableEntity, Integer> {
    List<ConnectionTableEntity> findAllByDiscordIdEquals(String discordId);

    ConnectionTableEntity findByDiscordIdEqualsAndServiceNameEquals(String discordId, String serviceName);

    ConnectionTableEntity findByServiceIdEqualsAndServiceNameEquals(String serviceId, String serviceName);
}
