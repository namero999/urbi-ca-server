package co.urbi.blockchain.web.ca;

import co.urbi.blockchain.model.Params;
import co.urbi.blockchain.model.ValidationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Map;

import static co.urbi.blockchain.contracts.Web3Credentials.registry;
import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;
import static java.time.temporal.ChronoUnit.YEARS;

@RestController
public class ValidationController {

    @PostMapping("/validate")
    public Map validate(@RequestBody ValidationRequest validationRequest) throws Exception {

        boolean valid = validationRequest.verifySignature();

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

        return result;

    }

}