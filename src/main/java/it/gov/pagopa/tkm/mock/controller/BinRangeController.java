package it.gov.pagopa.tkm.mock.controller;

import it.gov.pagopa.tkm.mock.dto.bin.visa.*;
import it.gov.pagopa.tkm.mock.repository.*;
import it.gov.pagopa.tkm.mock.service.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@Validated
@RestController
@RequestMapping("/bin")
@Log4j2
public class BinRangeController {

    @Autowired
    private BinRangeService binRangeService;

    @Autowired
    private ConfigRepository configRepository;

    @PostMapping("/visa")
    public ResponseEntity<BinRangeResponse> getBinRangesVisa(@RequestBody @Valid BinRangeRequest request, @RequestHeader(name = "keyId") String keyId, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth) throws Exception {
        HttpStatus configuredStatus = HttpStatus.valueOf(Integer.parseInt(configRepository.findByConfigName("VISA_BIN_RANGE_API_HTTP_CODE").getConfigValue()));
        log.info("START getBinRangesVisa - Configured HTTP status: " + configuredStatus.value());
        BinRangeResponse response;
        switch (configuredStatus) {
            case BAD_REQUEST:
            case MULTIPLE_CHOICES:
            case UNAUTHORIZED:
                response = null;
                break;
            case INTERNAL_SERVER_ERROR:
            case OK:
                response = binRangeService.getBinRangesVisa(request, configuredStatus);
                break;
            default:
                throw new Exception(configuredStatus.value() + " is not a valid HTTP status for this API");
        }
        return ResponseEntity.status(configuredStatus).body(response);
    }

}
