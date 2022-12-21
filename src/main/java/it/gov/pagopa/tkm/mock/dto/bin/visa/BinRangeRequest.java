package it.gov.pagopa.tkm.mock.dto.bin.visa;

import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinRangeRequest {

    @NotNull
    private BinRangeRequestHeader requestHeader;

    @NotNull
    private BinRangeRequestData requestData;

}
