package it.gov.pagopa.tkm.mock.controller;

import it.gov.pagopa.tkm.mock.dto.par.*;
import it.gov.pagopa.tkm.mock.dto.par.visa.*;
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
@RequestMapping("/par")
@Log4j2
public class ParController {

    @Autowired
    private ParService parService;

    @Autowired
    private ConfigRepository configRepository;

    @PostMapping
    public ParCreationResponse createPar(@RequestBody @Valid ParCreationRequest request) throws Exception {
        log.info("START createPar - Body: " + request);
        return parService.createPar(request);
    }

    @PostMapping("/visa")
    public ParResponseEnc getParVisa(@RequestBody @Valid ParRequestEnc request, @RequestHeader(name = "keyId") String keyId, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth) throws Exception {
        HttpStatus configuredStatus = HttpStatus.valueOf(Integer.parseInt(configRepository.findByConfigName("VISA_PAR_API_HTTP_CODE").getConfigValue()));
        log.info("START getParVisa - Configured HTTP status: " + configuredStatus.value());
        ParResponseEnc response;
        switch (configuredStatus) {
            case OK:
                response = parService.getParVisa(request, keyId);
                break;
            // TODO ERRORI
            default:
                throw new Exception(configuredStatus.value() + " is not a valid HTTP status for this API");
        }
        return response;
    }

}
