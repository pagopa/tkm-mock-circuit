package it.gov.pagopa.tkm.mock.entity;

import it.gov.pagopa.tkm.mock.constant.*;
import lombok.*;

import javax.persistence.*;

@Table(name = "TOKEN_BIN_RANGE")
@Entity
@Data
@NoArgsConstructor
public class BinRangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CIRCUIT")
    @Enumerated(EnumType.STRING)
    private CircuitEnum circuit;

    @Column(name = "MIN_RANGE")
    private String minRange;

    @Column(name = "MAX_RANGE")
    private String maxRange;

}
