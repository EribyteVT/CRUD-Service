package net.eribyte.crud.repository;

import net.eribyte.crud.entity.XpTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XpRepository extends JpaRepository<XpTableEntity, Integer> {

    XpTableEntity findByServiceIdEqualsAndServiceNameEquals(String serviceId,String serviceName);

    XpTableEntity findByEriIdEquals(Integer eriId);

}
