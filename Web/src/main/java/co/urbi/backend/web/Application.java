package co.urbi.backend.web;

import co.urbi.contracts.Registry;
import co.urbi.json.JSON;
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
import org.web3j.protocol.websocket.WebSocketClient;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.net.URI;

import static org.web3j.crypto.WalletUtils.loadCredentials;
import static org.web3j.protocol.core.DefaultBlockParameterName.LATEST;

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

        final String network = "web3.network.goerli.ws";
        WebSocketService webSocketService = new WebSocketService(new WebSocketClient(new URI($(network))), false);
        webSocketService.connect();

        Web3j web3j = Web3j.build(webSocketService);
        log.info("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());

        String walletPwd = $("web3.wallet.password");
        Web3Credentials.caCreds = loadCredentials(walletPwd, loadWallet($("web3.wallet.ca")));
        Web3Credentials.userCreds = loadCredentials(walletPwd, loadWallet($("web3.wallet.user")));
        Web3Credentials.providerCreds = loadCredentials(walletPwd, loadWallet($("web3.wallet.provider")));
        log.info("Wallet credentials loaded");

        ContractGasProvider contractGasProvider = new DefaultGasProvider();
//        Web3Credentials.registry = Registry.deploy(web3j, Web3Credentials.caCreds, contractGasProvider).send();
//        System.out.println(Web3Credentials.registry.getContractAddress());
        Web3Credentials.registry = Registry.load($("web3.registry.address.goerli"), web3j, Web3Credentials.caCreds, contractGasProvider);

        Web3Credentials.registry
                .whitelistAdminAddedEventFlowable(LATEST, LATEST)
                .subscribe(e -> {
                    System.out.println(e);
                });

        Web3Credentials.registry.addWhitelistAdmin(Web3Credentials.providerCreds.getAddress()).sendAsync().handle((tr, t) -> {
            System.out.println(tr);
            return null;
        });

        return web3j;

    }

    private String loadWallet(String wallet) {
        return this.getClass().getResource("/" + wallet).getFile();
    }

    private String $(String key) {
        return env.getProperty(key);
    }

}