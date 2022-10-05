package it.gov.pagopa.tkm.mock.repository;

import it.gov.pagopa.tkm.mock.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity, Long> {

    ConfigEntity findByConfigName(String name);

}
