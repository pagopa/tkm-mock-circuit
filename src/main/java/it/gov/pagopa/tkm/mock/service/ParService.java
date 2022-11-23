package it.gov.pagopa.tkm.mock.service;

import com.fasterxml.jackson.databind.*;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import it.gov.pagopa.tkm.mock.dto.par.*;
import it.gov.pagopa.tkm.mock.dto.par.visa.*;
import it.gov.pagopa.tkm.mock.entity.*;
import it.gov.pagopa.tkm.mock.repository.*;
import it.gov.pagopa.tkm.mock.constant.*;
import lombok.extern.log4j.*;
import org.apache.commons.lang3.*;
import org.bouncycastle.asn1.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.math.*;
import java.security.*;
import java.security.cert.*;
import java.security.interfaces.*;
import java.security.spec.*;
import java.time.*;
import java.util.*;

@Service
@Log4j2
public class ParService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BinRangeRepository binRangeRepository;

    @Autowired
    private ObjectMapper mapper;

    @Value("${VISA_CERTIFICATE}")
    private String visaCertificate;

    @Value("${VISA_PRIVATE_KEY}")
    private String visaPrivateKey;

    private final Random random = new Random();

    // GENERATE PAR

    public ParCreationResponse createPar(ParCreationRequest request) throws Exception {
        String pan = request.getPan();
        CircuitEnum circuit = request.getCircuit();
        CardEntity card = cardRepository.findByPan(pan);
        log.info(card == null ? "No existing card found, creating one..." : "Existing card found, updating...");
        if (card == null) {
            checkBin(circuit, pan);
            card = new CardEntity();
            card.setPan(pan);
            card.setCircuit(circuit);
        }
        String par = generatePar();
        List<String> tokens = generateTokens(circuit, request.getTokenNumber());
        card.setPar(par);
        card.setTokens(String.join(",", tokens));
        cardRepository.save(card);
        log.info("END createPar - Saved card: " + card);
        return new ParCreationResponse(pan, par, circuit, tokens);
    }

    private void checkBin(CircuitEnum circuit, String pan) throws Exception {
        if (!((pan.startsWith("4") && circuit.equals(CircuitEnum.VISA)) || (pan.startsWith("37") && circuit.equals(CircuitEnum.AMEX)) || (StringUtils.startsWithAny(pan, "51", "52", "53", "54", "55") && circuit.equals(CircuitEnum.MASTERCARD)))) {
            throw new Exception("Circuit " + circuit.name() + " does not match pan " + pan);
        }
    }

    private String generatePar() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private List<String> generateTokens(CircuitEnum circuit, int number) {
        List<String> tokens = new ArrayList<>();
        List<BinRangeEntity> binRanges = binRangeRepository.findByCircuit(circuit);
        for (int i = 0; i < number; i++) {
            BinRangeEntity randomBin = binRanges.get(random.nextInt(binRanges.size()));
            tokens.add(randomBin.getMinRange().replaceAll("0{9}$", String.valueOf(100000000 + random.nextInt(900000000))));
        }
        return tokens;
    }

    // GET PAR

    public ParResponseEnc getParVisa(ParRequestEnc request, String keyId) throws Exception {
        log.info("Encrypted request: " + request.getEncData());
        ParRequestPlain plainRequest = getDecryptedPayload(request.getEncData());
        log.info("Plain par request: " + plainRequest);
        CardEntity card = cardRepository.findByPan(plainRequest.getPrimaryAccount());
        log.info("Card found by pan: " + card);
        ParResponsePlain plainResponse = new ParResponsePlain(card.getPar(), null, card.getPan());
        log.info("Plain par response: " + plainResponse);
        return new ParResponseEnc(getEncryptedPayload(mapper.writeValueAsString(plainResponse), keyId));
    }

    private String getEncryptedPayload(String payload, String keyId) throws Exception {
        JWEHeader.Builder headerBuilder = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);
        headerBuilder.keyID(keyId);
        headerBuilder.customParam("iat", Instant.now().toEpochMilli());
        JWEObject jweObject = new JWEObject(headerBuilder.build(), new Payload(payload));
        jweObject.encrypt(new RSAEncrypter(getRSAPublicKey()));
        return jweObject.serialize();
    }

    private ParRequestPlain getDecryptedPayload(String payload) throws Exception {
        JWEObject jweObject = JWEObject.parse(payload);
        jweObject.decrypt(new RSADecrypter(getRSAPrivateKey()));
        return mapper.readValue(jweObject.getPayload().toString(), ParRequestPlain.class);
    }

    private PrivateKey getRSAPrivateKey() throws Exception {
        final Enumeration<?> e = ((ASN1Sequence) ASN1Primitive.fromByteArray(new com.nimbusds.jose.util.Base64(visaPrivateKey.replace("-----BEGIN RSA PRIVATE KEY-----", "").replace("-----END RSA PRIVATE KEY-----", "").trim()).decode())).getObjects();
        final BigInteger v = ((ASN1Integer) e.nextElement()).getPositiveValue();
        int version = v.intValue();
        if (version != 0 && version != 1) {
            throw new IllegalArgumentException("wrong version for RSA private key");
        }
        final BigInteger modulus = ((ASN1Integer) e.nextElement()).getPositiveValue();
        ((ASN1Integer) e.nextElement()).getPositiveValue();
        BigInteger privateExponent = ((ASN1Integer) e.nextElement()).getPositiveValue();
        ((ASN1Integer) e.nextElement()).getPositiveValue();
        ((ASN1Integer) e.nextElement()).getPositiveValue();
        ((ASN1Integer) e.nextElement()).getPositiveValue();
        ((ASN1Integer) e.nextElement()).getPositiveValue();
        ((ASN1Integer) e.nextElement()).getPositiveValue();
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(modulus, privateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(privateKeySpec);
    }

    private RSAPublicKey getRSAPublicKey() throws Exception {
        return (RSAPublicKey) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(new com.nimbusds.jose.util.Base64(visaCertificate.replace("-----BEGIN CERTIFICATE-----", "").replace("-----END CERTIFICATE-----", "").trim()).decode())).getPublicKey();
    }

}
