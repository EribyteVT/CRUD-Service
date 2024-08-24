package net.eribyte.crud.repository.xp;

import net.eribyte.crud.entity.xp.XpTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XpRepository extends JpaRepository<XpTableEntity, Integer> {

    XpTableEntity findByServiceIdEqualsAndServiceNameEquals(String serviceId,String serviceName);

    XpTableEntity findByEriIdEquals(Integer eriId);

}
