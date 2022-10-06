package it.gov.pagopa.tkm.mock.dto.par;

import lombok.*;
import it.gov.pagopa.tkm.mock.constant.*;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParCreationRequest {

    @NotEmpty
    @Pattern(regexp = "^\\d{8,19}$")
    private String pan;

    @NotNull
    private CircuitEnum circuit;

    private int tokenNumber;

}
