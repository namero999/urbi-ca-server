package co.urbi.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class Registry extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b506100293361002e640100000000026401000000009004565b6101e8565b61004f81600061009564010000000002610715179091906401000000009004565b8073ffffffffffffffffffffffffffffffffffffffff167f22380c05984257a1cb900161c713dd71d39e74820f1aea43bd3f1bdd2096129960405160405180910390a250565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141515156100d157600080fd5b6100ea8282610154640100000000026401000000009004565b1515156100f657600080fd5b60018260000160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055505050565b60008073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415151561019157600080fd5b8260000160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16905092915050565b6108a0806101f76000396000f3fe608060405234801561001057600080fd5b50600436106100b0576000357c0100000000000000000000000000000000000000000000000000000000900480634c5a628c116100835780634c5a628c1461022b5780637362d9c814610235578063bb5f747b14610279578063d6cd9473146102d5578063eaf914a1146102df576100b0565b806310154bad146100b5578063236e0929146100f9578063291d95491461018b5780633af32abf146101cf575b600080fd5b6100f7600480360360208110156100cb57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610337565b005b61013b6004803603602081101561010f57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610357565b604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001838152602001828152602001935050505060405180910390f35b6101cd600480360360208110156101a157600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506103a1565b005b610211600480360360208110156101e557600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506103c1565b604051808215151515815260200191505060405180910390f35b6102336103de565b005b6102776004803603602081101561024b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506103e9565b005b6102bb6004803603602081101561028f57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610409565b604051808215151515815260200191505060405180910390f35b6102dd610426565b005b610335600480360360608110156102f557600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919080359060200190929190505050610431565b005b61034033610409565b151561034b57600080fd5b61035481610519565b50565b60026020528060005260406000206000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154908060020154905083565b6103aa33610409565b15156103b557600080fd5b6103be81610573565b50565b60006103d78260016105cd90919063ffffffff16565b9050919050565b6103e733610661565b565b6103f233610409565b15156103fd57600080fd5b610406816106bb565b50565b600061041f8260006105cd90919063ffffffff16565b9050919050565b61042f33610573565b565b61043a336103c1565b151561044557600080fd5b6060604051908101604052803373ffffffffffffffffffffffffffffffffffffffff16815260200183815260200182815250600260008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020155905050505050565b61052d81600161071590919063ffffffff16565b8073ffffffffffffffffffffffffffffffffffffffff167fee1504a83b6d4a361f4c1dc78ab59bfa30d6a3b6612c403e86bb01ef2984295f60405160405180910390a250565b6105878160016107c590919063ffffffff16565b8073ffffffffffffffffffffffffffffffffffffffff167f270d9b30cf5b0793bbfd54c9d5b94aeb49462b8148399000265144a8722da6b660405160405180910390a250565b60008073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415151561060a57600080fd5b8260000160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16905092915050565b6106758160006107c590919063ffffffff16565b8073ffffffffffffffffffffffffffffffffffffffff167f0a8eb35e5ca14b3d6f28e4abf2f128dbab231a58b56e89beb5d636115001e16560405160405180910390a250565b6106cf81600061071590919063ffffffff16565b8073ffffffffffffffffffffffffffffffffffffffff167f22380c05984257a1cb900161c713dd71d39e74820f1aea43bd3f1bdd2096129960405160405180910390a250565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161415151561075157600080fd5b61075b82826105cd565b15151561076757600080fd5b60018260000160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055505050565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161415151561080157600080fd5b61080b82826105cd565b151561081657600080fd5b60008260000160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff021916908315150217905550505056fea165627a7a72305820178e541c61bd26d6af474b570ebfa7cba1604ba3bc3d60128d2e2541a0aff0f70029";

    public static final String FUNC_ADDWHITELISTED = "addWhitelisted";

    public static final String FUNC_CERTIFICATIONS = "certifications";

    public static final String FUNC_REMOVEWHITELISTED = "removeWhitelisted";

    public static final String FUNC_ISWHITELISTED = "isWhitelisted";

    public static final String FUNC_RENOUNCEWHITELISTADMIN = "renounceWhitelistAdmin";

    public static final String FUNC_ADDWHITELISTADMIN = "addWhitelistAdmin";

    public static final String FUNC_ISWHITELISTADMIN = "isWhitelistAdmin";

    public static final String FUNC_RENOUNCEWHITELISTED = "renounceWhitelisted";

    public static final String FUNC_ADDCERTIFICATION = "addCertification";

    public static final Event WHITELISTEDADDED_EVENT = new Event("WhitelistedAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event WHITELISTEDREMOVED_EVENT = new Event("WhitelistedRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event WHITELISTADMINADDED_EVENT = new Event("WhitelistAdminAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event WHITELISTADMINREMOVED_EVENT = new Event("WhitelistAdminRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected Registry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Registry(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Registry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Registry(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addWhitelisted(String account) {
        final Function function = new Function(
                FUNC_ADDWHITELISTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<String, byte[], BigInteger>> certifications(String param0) {
        final Function function = new Function(FUNC_CERTIFICATIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<String, byte[], BigInteger>>(function,
                new Callable<Tuple3<String, byte[], BigInteger>>() {
                    @Override
                    public Tuple3<String, byte[], BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, byte[], BigInteger>(
                                (String) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> removeWhitelisted(String account) {
        final Function function = new Function(
                FUNC_REMOVEWHITELISTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isWhitelisted(String account) {
        final Function function = new Function(FUNC_ISWHITELISTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceWhitelistAdmin() {
        final Function function = new Function(
                FUNC_RENOUNCEWHITELISTADMIN, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addWhitelistAdmin(String account) {
        final Function function = new Function(
                FUNC_ADDWHITELISTADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isWhitelistAdmin(String account) {
        final Function function = new Function(FUNC_ISWHITELISTADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceWhitelisted() {
        final Function function = new Function(
                FUNC_RENOUNCEWHITELISTED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<WhitelistedAddedEventResponse> getWhitelistedAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WHITELISTEDADDED_EVENT, transactionReceipt);
        ArrayList<WhitelistedAddedEventResponse> responses = new ArrayList<WhitelistedAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WhitelistedAddedEventResponse typedResponse = new WhitelistedAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WhitelistedAddedEventResponse> whitelistedAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WhitelistedAddedEventResponse>() {
            @Override
            public WhitelistedAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WHITELISTEDADDED_EVENT, log);
                WhitelistedAddedEventResponse typedResponse = new WhitelistedAddedEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WhitelistedAddedEventResponse> whitelistedAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WHITELISTEDADDED_EVENT));
        return whitelistedAddedEventFlowable(filter);
    }

    public List<WhitelistedRemovedEventResponse> getWhitelistedRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WHITELISTEDREMOVED_EVENT, transactionReceipt);
        ArrayList<WhitelistedRemovedEventResponse> responses = new ArrayList<WhitelistedRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WhitelistedRemovedEventResponse typedResponse = new WhitelistedRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WhitelistedRemovedEventResponse> whitelistedRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WhitelistedRemovedEventResponse>() {
            @Override
            public WhitelistedRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WHITELISTEDREMOVED_EVENT, log);
                WhitelistedRemovedEventResponse typedResponse = new WhitelistedRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WhitelistedRemovedEventResponse> whitelistedRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WHITELISTEDREMOVED_EVENT));
        return whitelistedRemovedEventFlowable(filter);
    }

    public List<WhitelistAdminAddedEventResponse> getWhitelistAdminAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WHITELISTADMINADDED_EVENT, transactionReceipt);
        ArrayList<WhitelistAdminAddedEventResponse> responses = new ArrayList<WhitelistAdminAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WhitelistAdminAddedEventResponse typedResponse = new WhitelistAdminAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WhitelistAdminAddedEventResponse> whitelistAdminAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WhitelistAdminAddedEventResponse>() {
            @Override
            public WhitelistAdminAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WHITELISTADMINADDED_EVENT, log);
                WhitelistAdminAddedEventResponse typedResponse = new WhitelistAdminAddedEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WhitelistAdminAddedEventResponse> whitelistAdminAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WHITELISTADMINADDED_EVENT));
        return whitelistAdminAddedEventFlowable(filter);
    }

    public List<WhitelistAdminRemovedEventResponse> getWhitelistAdminRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WHITELISTADMINREMOVED_EVENT, transactionReceipt);
        ArrayList<WhitelistAdminRemovedEventResponse> responses = new ArrayList<WhitelistAdminRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WhitelistAdminRemovedEventResponse typedResponse = new WhitelistAdminRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WhitelistAdminRemovedEventResponse> whitelistAdminRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WhitelistAdminRemovedEventResponse>() {
            @Override
            public WhitelistAdminRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WHITELISTADMINREMOVED_EVENT, log);
                WhitelistAdminRemovedEventResponse typedResponse = new WhitelistAdminRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WhitelistAdminRemovedEventResponse> whitelistAdminRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WHITELISTADMINREMOVED_EVENT));
        return whitelistAdminRemovedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addCertification(String certifier, byte[] proof, BigInteger expirationDate) {
        final Function function = new Function(
                FUNC_ADDCERTIFICATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier), 
                new org.web3j.abi.datatypes.generated.Bytes32(proof), 
                new org.web3j.abi.datatypes.generated.Uint256(expirationDate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Registry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Registry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Registry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Registry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Registry load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Registry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Registry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Registry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Registry> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Registry.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Registry> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Registry.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Registry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Registry.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Registry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Registry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class WhitelistedAddedEventResponse extends BaseEventResponse {
        public String account;
    }

    public static class WhitelistedRemovedEventResponse extends BaseEventResponse {
        public String account;
    }

    public static class WhitelistAdminAddedEventResponse extends BaseEventResponse {
        public String account;
    }

    public static class WhitelistAdminRemovedEventResponse extends BaseEventResponse {
        public String account;
    }
}
