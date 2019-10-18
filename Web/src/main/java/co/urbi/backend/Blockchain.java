package co.urbi.backend;

import co.urbi.contracts.Registry;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

public class Blockchain {

    public static Web3j web3j;

    public static Registry registry;

    public static Credentials caCreds;
    public static Credentials userCreds;
    public static Credentials providerCreds;

}