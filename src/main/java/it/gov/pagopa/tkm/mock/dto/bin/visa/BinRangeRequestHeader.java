package it.gov.pagopa.tkm.mock.dto.bin.visa;

import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinRangeRequestHeader {

    @NotEmpty
    private String requestTS;

    @NotEmpty
    private String requestMessageID;

}
