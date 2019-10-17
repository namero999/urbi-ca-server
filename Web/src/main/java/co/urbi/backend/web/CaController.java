package co.urbi.backend.web;

import co.urbi.backend.model.Params;
import co.urbi.backend.model.ValidationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

import static co.urbi.backend.web.Blockchain.registry;
import static co.urbi.json.JSON.toJson;
import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;
import static java.time.temporal.ChronoUnit.YEARS;

@RestController
public class CaController {

    @Autowired
    private DLValidationService dlValidationService;

    @PostMapping(value = "/validate", produces = "application/json; charset=utf-8")
    public String validate(@RequestBody ValidationRequest validationRequest) throws Exception {

        boolean cryptographicallyValid = validationRequest.verifySignature();
        boolean administrativelyValid = dlValidationService.checkValidity(validationRequest.getPayload());

        boolean valid = cryptographicallyValid && administrativelyValid;

        Params result = new Params().put("valid", valid)
                                    .put("hash", validationRequest.hashHex());

        if (valid) {

            TransactionReceipt receipt = registry.addCertification(
                    validationRequest.getAddress(),
                    validationRequest.hash(),
                    BigInteger.valueOf(now().plus(5, YEARS).toEpochSecond(UTC))).send();

            result.put("txId", receipt.getTransactionHash())
                  .put("txUrl", "https://rinkeby.etherscan.io/tx/" + receipt.getTransactionHash());

        }

        return toJson(result);

    }

}