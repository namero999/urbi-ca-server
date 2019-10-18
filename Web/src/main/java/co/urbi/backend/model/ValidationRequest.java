package co.urbi.backend.model;

import co.urbi.json.JSON;
import lombok.Data;
import lombok.SneakyThrows;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign.SignatureData;

import java.math.BigInteger;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.copyOfRange;
import static org.web3j.crypto.Hash.sha3;
import static org.web3j.crypto.Keys.toChecksumAddress;
import static org.web3j.crypto.Sign.signedPrefixedMessageToKey;
import static org.web3j.utils.Numeric.hexStringToByteArray;
import static org.web3j.utils.Numeric.toHexString;

@Data
public class ValidationRequest {

    private String address;
    private String signature;
    private ValidationPayload payload;

    public byte[] hash() {
        return sha3(getPayloadBytes());
    }

    public String hashHex() {
        return toHexString(sha3(getPayloadBytes()));
    }

    public byte[] getPayloadBytes() {
        return JSON.toJson(payload).getBytes(UTF_8);
    }

    @SneakyThrows
    public boolean verifySignature() {

        byte[] message = getPayloadBytes();

        BigInteger bigInteger = signedPrefixedMessageToKey(message, getSignatureData(signature));
        String recoveredAddress = toChecksumAddress(Keys.getAddress(bigInteger));

        return address.equals(recoveredAddress.toLowerCase());

    }

    private SignatureData getSignatureData(String signature) {

        byte[] signatureBytes = hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        return new SignatureData(
                v < 27 ? (byte) (v + 27) : v,
                copyOfRange(signatureBytes, 0, 32),
                copyOfRange(signatureBytes, 32, 64));

    }

}