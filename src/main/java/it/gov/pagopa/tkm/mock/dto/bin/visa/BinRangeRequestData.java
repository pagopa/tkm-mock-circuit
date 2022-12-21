package it.gov.pagopa.tkm.mock.dto.bin.visa;

import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinRangeRequestData {

    @NotEmpty
    private String binRangeSearchIndex;

    @NotEmpty
    private String binRangeCount;

}
