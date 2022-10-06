package it.gov.pagopa.tkm.mock.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "CONFIG")
@Entity
@Data
@NoArgsConstructor
public class ConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CONFIG_NAME")
    private String configName;

    @Column(name = "CONFIG_VALUE")
    private String configValue;

}
