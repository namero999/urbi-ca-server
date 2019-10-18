package co.urbi.backend.web;

import co.urbi.backend.model.Params;
import co.urbi.backend.model.ValidationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;

import static co.urbi.backend.Blockchain.registry;
import static java.time.Instant.now;

@RestController
public class ProviderController {

    @PostMapping("/signup")
    public Map validate(@RequestBody ValidationRequest validationRequest) throws Exception {

        Tuple3<String, byte[], BigInteger> certification = registry.certifications(validationRequest.getAddress()).send();

        Instant expiration = Instant.ofEpochSecond(certification.component3().longValue());

        return new Params().put("valid", expiration.isAfter(now()) && Arrays.equals(validationRequest.hash(), certification.component2()));

    }

}