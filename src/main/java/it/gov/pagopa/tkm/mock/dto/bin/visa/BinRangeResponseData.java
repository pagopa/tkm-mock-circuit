package it.gov.pagopa.tkm.mock.dto.bin.visa;

import lombok.*;

@Data
@AllArgsConstructor
public class BinRangeResponseData {

    private String binRangeMinNum;

    private String binRangeMaxNum;

    private String binRangePaymentAccountType;

    private String productID;

    private String productIDName;

    private String accountFundingSourceCd;

    private String platformCd;

    private String accountRegionCode;

    private String issuerBin;

    private String issuerBillingCurrCd;

    private String accountCtryAlpha2Code;

}
