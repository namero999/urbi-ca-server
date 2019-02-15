package co.urbi.blockchain.web.provider;

import co.urbi.blockchain.model.Params;
import co.urbi.blockchain.model.ValidationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;

import static co.urbi.blockchain.contracts.Web3Credentials.registry;
import static java.time.Instant.now;

@RestController
public class VerificationController {

    @PostMapping("/signup")
    public Map validate(@RequestBody ValidationRequest validationRequest) throws Exception {

        Tuple3<String, byte[], BigInteger> certification = registry.certifications(validationRequest.getAddress()).send();

        Params result = new Params();

        Instant expiration = Instant.ofEpochSecond(certification.getValue3().longValue());
        boolean valid = expiration.isAfter(now()) && Arrays.equals(validationRequest.hash(), certification.getValue2());
        result.put("valid", valid);

        return result;

    }

}