package it.gov.pagopa.tkm.mock.constant;

import lombok.*;

import java.util.*;

@AllArgsConstructor
public enum VisaBinRangeOutcomeEnum {

    // 200
    SUCCESS("CDI000", "Success"),
    // 500
    INTERNAL_SERVER_ERROR("CDI001", "Internal server error"),
    XML_PARSING_FAILURE("CDI002", "XML parsing failure"),
    INVALID_HEADER_LENGTH("CDI012", "Request header length is invalid"),
    CLIENT_AUTH_FAILED("CDI052", "Client authentication failed"),
    INVALID_DATA("CDI071", "Exception in Group level entitlement process"),
    INDEX_VALIDATION_FAILED("CDI246", "Bin Range Search Index validation failure"),
    COUNT_VALIDATION_FAILED("CDI247", "Bin Range Count validation failure"),
    PAYMENT_ACCOUNT_TYPE_VALIDATION_FAILED("CDI249", "Payment Account Type validation failure"),
    MISSING_FIELDS("CDI250", "At least one of the required/mandatory fields is missing in the request");

    @Getter
    private final String code;

    @Getter
    private final String description;

    private static final Random random = new Random();

    public static VisaBinRangeOutcomeEnum randomError() {
        return values()[random.nextInt(values().length)];
    }

}
