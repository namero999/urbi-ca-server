package co.urbi.blockchain.contracts;

import co.urbi.blockchain.contracts.generated.Registry;
import lombok.extern.log4j.Log4j2;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.web3j.crypto.WalletUtils.loadCredentials;

@Log4j2
public class DeployRegistry {

    public static void main(String[] args) throws Exception {
        new DeployRegistry().run();
    }

    private void run() throws Exception {

        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/c257869547584032a007552a87fb810d"));
        log.info("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());

        Credentials credentials = loadCredentials("BatSharing#22", "wallet-ca-e67905e3154392cf46cc91ac1ac29880d9f4fd4c.json");
        log.info("Credentials loaded");
        String recipient = "0x4c6903f3FeCEA691F18488e455845AbED673cBd3";

        log.info("Deploying smart contract");
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
//        Registry contract = Registry.deploy(web3j, credentials, contractGasProvider).send();
        Registry contract = Registry.load("0x0d1d6bc297349dd29ffd8bf6ab0f7ffc4868eb70", web3j, credentials, contractGasProvider);

        String contractAddress = contract.getContractAddress();
        log.info("Smart contract deployed to address " + contractAddress);
        log.info("View contract at https://rinkeby.etherscan.io/address/" + contractAddress);

        byte[] hash = Hash.sha3("test".getBytes(UTF_8));

        Tuple3<String, byte[], BigInteger> test = contract.certifications(recipient).send();
        log.info("Value stored in remote smart contract: " + test);

        TransactionReceipt transactionReceipt = contract.addCertification(recipient, hash, BigInteger.TEN).send();

        test = contract.certifications(recipient).send();
        log.info("Value stored in remote smart contract: " + test);

    }

}