package co.urbi.backend;

import co.urbi.backend.events.AdminAdded;
import co.urbi.backend.events.AdminRemoved;
import co.urbi.backend.events.PartnerAdded;
import co.urbi.backend.events.PartnerRemoved;
import co.urbi.contracts.Registry;
import co.urbi.json.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.websocket.WebSocketClient;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import javax.annotation.PreDestroy;
import java.net.URI;

import static org.web3j.protocol.core.DefaultBlockParameterName.LATEST;

@Log4j2
@SpringBootApplication
public class Application {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @SneakyThrows
    public Web3j web3j() {

        final String network = "web3.network.rinkeby.ws";

        Web3j web3j = startWeb3j(network);
        loadCredentials();
        loadContracts(web3j);

        subscribeToEvents();

        // Web3Credentials.registry.addWhitelistAdmin(Web3Credentials.providerCreds.getAddress()).send();

        return web3j;

    }

    @SneakyThrows
    private void loadContracts(Web3j web3j) {

        ContractGasProvider contractGasProvider = new DefaultGasProvider();

//        Blockchain.registry = Registry.deploy(web3j, Blockchain.caCreds, contractGasProvider).send();
//        log.info("Deployed contract at " + Blockchain.registry.getContractAddress());
        Blockchain.registry = Registry.load($("web3.registry.address.rinkeby"), web3j, Blockchain.caCreds, contractGasProvider);

    }

    @SneakyThrows
    private Web3j startWeb3j(String network) {

        WebSocketService webSocketService = new WebSocketService(new WebSocketClient(new URI($(network))), false);
        webSocketService.connect();

        Web3j web3j = Web3j.build(webSocketService);
        log.info("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());

        Blockchain.web3j = web3j;

        return web3j;

    }

    @SneakyThrows
    private void loadCredentials() {
        String walletPwd = $("web3.wallet.password");
        Blockchain.caCreds = WalletUtils.loadCredentials(walletPwd, loadWallet($("web3.wallet.ca")));
        Blockchain.userCreds = WalletUtils.loadCredentials(walletPwd, loadWallet($("web3.wallet.user")));
        Blockchain.providerCreds = WalletUtils.loadCredentials(walletPwd, loadWallet($("web3.wallet.provider")));
        log.info("Wallet credentials loaded");
    }

    private void subscribeToEvents() {
        Blockchain.registry.whitelistAdminAddedEventFlowable(LATEST, LATEST).subscribe(new AdminAdded());
        Blockchain.registry.whitelistAdminRemovedEventFlowable(LATEST, LATEST).subscribe(new AdminRemoved());
        Blockchain.registry.whitelistedAddedEventFlowable(LATEST, LATEST).subscribe(new PartnerAdded());
        Blockchain.registry.whitelistedRemovedEventFlowable(LATEST, LATEST).subscribe(new PartnerRemoved());
    }

    private String loadWallet(String wallet) {
        return this.getClass().getResource("/" + wallet).getFile();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper mapper = builder.build();
        JSON.init(mapper);
        return mapper;
    }

    @PreDestroy
    public void onExit() {
        log.info("Stopping...");
        if (Blockchain.web3j != null)
            Blockchain.web3j.shutdown();
    }

    private String $(String key) {
        return env.getProperty(key);
    }

}