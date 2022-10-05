package it.gov.pagopa.tkm.mock.service;

import it.gov.pagopa.tkm.mock.constant.*;
import it.gov.pagopa.tkm.mock.dto.bin.visa.*;
import it.gov.pagopa.tkm.mock.entity.*;
import it.gov.pagopa.tkm.mock.repository.*;
import lombok.extern.log4j.*;
import org.apache.commons.lang3.math.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

@Service
@Log4j2
public class BinRangeService {

    @Autowired
    private BinRangeRepository binRangeRepository;

    private List<BinRangeEntity> allBinRanges;

    @PostConstruct
    protected void init() {
        allBinRanges = binRangeRepository.findAll();
    }

    public BinRangeResponse getBinRangesVisa(BinRangeRequest request, HttpStatus status) {
        if (!HttpStatus.OK.equals(status)) {
            BinRangeResponse response = new BinRangeResponse();
            VisaBinRangeOutcomeEnum error = VisaBinRangeOutcomeEnum.randomError();
            response.setResponseStatus(new BinRangeResponseStatus(error.getCode(), error.getDescription()));
            log.info("Configured HTTP status not OK, returning random error: " + error.getCode());
            return response;
        }
        int fromIndex = Integer.parseInt(request.getRequestData().getBinRangeSearchIndex());
        int toIndex = NumberUtils.min(fromIndex + Integer.parseInt(request.getRequestData().getBinRangeCount()), allBinRanges.size());
        log.info("Retrieving bin range chunk from index " + fromIndex + " to index " + toIndex);
        List<BinRangeEntity> currentChunk = allBinRanges.subList(fromIndex, toIndex);
        return new BinRangeResponse(
            String.valueOf(currentChunk.size()),
            toIndex < allBinRanges.size() ? "Y" : "N",
            new BinRangeResponseHeader(null),
            currentChunk.stream().map(b ->
                new BinRangeResponseData(
                    b.getMinRange(),
                    b.getMaxRange(),
                    "T",
                    null, null, null, null, null, null, null, null
                )
            ).collect(Collectors.toList()),
            new BinRangeResponseStatus(
                    VisaBinRangeOutcomeEnum.SUCCESS.getCode(), VisaBinRangeOutcomeEnum.SUCCESS.getDescription()
            ),
            String.valueOf(allBinRanges.size())
        );
    }

}
