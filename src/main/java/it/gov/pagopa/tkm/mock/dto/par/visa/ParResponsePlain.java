package it.gov.pagopa.tkm.mock.dto.par.visa;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParResponsePlain {

    private String paymentAccountReference;

    private String paymentAccountReferenceCreationDate;

    private String primaryAccount;

}
