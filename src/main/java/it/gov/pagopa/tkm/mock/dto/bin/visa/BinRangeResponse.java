package it.gov.pagopa.tkm.mock.dto.bin.visa;

import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinRangeResponse {

    private String numRecordsReturned;

    private String areNextOffsetRecordsAvailable;

    private BinRangeResponseHeader responseHeader;

    private List<BinRangeResponseData> responseData;

    private BinRangeResponseStatus responseStatus;

    private String totalRecordsCount;

}
