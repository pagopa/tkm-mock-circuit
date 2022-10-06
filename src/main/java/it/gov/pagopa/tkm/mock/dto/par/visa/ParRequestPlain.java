package it.gov.pagopa.tkm.mock.dto.par.visa;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParRequestPlain {

    private String clientId;

    private String correlatnId;

    private String primaryAccount;

}
