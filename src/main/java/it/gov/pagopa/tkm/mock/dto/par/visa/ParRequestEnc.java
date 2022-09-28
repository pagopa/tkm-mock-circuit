package it.gov.pagopa.tkm.mock.dto.par.visa;

import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParRequestEnc {

    @NotEmpty
    private String encData;

}
