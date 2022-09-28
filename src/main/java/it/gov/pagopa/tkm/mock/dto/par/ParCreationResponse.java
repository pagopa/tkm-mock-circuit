package it.gov.pagopa.tkm.mock.dto.par;

import it.gov.pagopa.tkm.mock.constant.*;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParCreationResponse {

    private String pan;

    private String par;

    private CircuitEnum circuit;

    private List<String> tokens;

}
