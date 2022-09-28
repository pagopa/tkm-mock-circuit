package it.gov.pagopa.tkm.mock.entity;

import lombok.*;

import javax.persistence.*;
import it.gov.pagopa.tkm.mock.constant.*;

@Table(name = "CARD")
@Entity
@Data
@NoArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CIRCUIT")
    @Enumerated(EnumType.STRING)
    private CircuitEnum circuit;

    @Column(name = "PAN")
    private String pan;

    @Column(name = "PAR")
    private String par;

    @Column(name = "TOKENS")
    private String tokens;

}
