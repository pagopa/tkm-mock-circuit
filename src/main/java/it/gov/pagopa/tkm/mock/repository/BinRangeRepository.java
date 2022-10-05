package it.gov.pagopa.tkm.mock.repository;

import it.gov.pagopa.tkm.mock.constant.*;
import it.gov.pagopa.tkm.mock.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface BinRangeRepository extends JpaRepository<BinRangeEntity, Long> {

    List<BinRangeEntity> findByCircuit(CircuitEnum circuit);

}
