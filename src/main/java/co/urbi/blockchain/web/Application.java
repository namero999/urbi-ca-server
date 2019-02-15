package co.urbi.blockchain.web;

import co.urbi.blockchain.JSON;
import co.urbi.blockchain.contracts.Web3Credentials;
import co.urbi.blockchain.contracts.generated.Registry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import static org.web3j.crypto.WalletUtils.loadCredentials;

@Log4j2
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private Environment env;

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper mapper = builder.build();
        JSON.init(mapper);
        return mapper;
    }

    @Bean
    public Web3j web3j() throws Exception {

        Web3j web3j = Web3j.build(new HttpService($("web3.network")));
        log.info("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());

        Web3Credentials.caCreds = loadCredentials($("web3.wallet.password"), $("web3.wallet.ca"));
        Web3Credentials.userCreds = loadCredentials($("web3.wallet.password"), $("web3.wallet.user"));
        Web3Credentials.providerCreds = loadCredentials($("web3.wallet.password"), $("web3.wallet.provider"));
        log.info("Wallet credentials loaded");

        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        Web3Credentials.registry = Registry.load($("web3.registry.address"), web3j, Web3Credentials.caCreds, contractGasProvider);

        return web3j;

    }

    private String $(String key) {
        return env.getProperty(key);
    }

}