package com.nhn.pebblewebback.application.config;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
 * <p>Generated with web3j version 1.5.0.
 */
@SuppressWarnings("rawtypes")
public class CommunityNFT extends Contract {
  public static final String BINARY = "Bin file was not provided";

  public static final String FUNC_APPROVE = "approve";

  public static final String FUNC_BALANCEOF = "balanceOf";

  public static final String FUNC_BASEURI = "baseURI";

  public static final String FUNC_CHANGEADMIN = "changeAdmin";

  public static final String FUNC_CHANGESIGNER = "changeSigner";

  public static final String FUNC_EXISTS = "exists";

  public static final String FUNC_GETAPPROVED = "getApproved";

  public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

  public static final String FUNC_ISSIGNATUREVALID = "isSignatureValid";

  public static final String FUNC_MINT = "mint";

  public static final String FUNC_NAME = "name";

  public static final String FUNC_OWNEROF = "ownerOf";

  public static final String FUNC_safeTransferFrom = "safeTransferFrom";

  public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

  public static final String FUNC_SETBASEURI = "setBaseURI";

  public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

  public static final String FUNC_SYMBOL = "symbol";

  public static final String FUNC_TOKENURI = "tokenURI";

  public static final String FUNC_TRANSFERFROM = "transferFrom";

  public static final Event APPROVAL_EVENT = new Event("Approval",
                                                       Arrays.asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));

  public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll",
                                                             Arrays.asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));

  public static final Event TRANSFER_EVENT = new Event("Transfer",
                                                       Arrays.asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));

  @Deprecated
  protected CommunityNFT(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected CommunityNFT(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected CommunityNFT(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected CommunityNFT(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
    ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      ApprovalEventResponse typedResponse = new ApprovalEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
      typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
    ApprovalEventResponse typedResponse = new ApprovalEventResponse();
    typedResponse.log = log;
    typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
    typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
    return typedResponse;
  }

  public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getApprovalEventFromLog(log));
  }

  public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
    return approvalEventFlowable(filter);
  }

  public static List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
    ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
      typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static ApprovalForAllEventResponse getApprovalForAllEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, log);
    ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
    typedResponse.log = log;
    typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
    typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getApprovalForAllEventFromLog(log));
  }

  public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
    return approvalForAllEventFlowable(filter);
  }

  public static List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
    ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      TransferEventResponse typedResponse = new TransferEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
      typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static TransferEventResponse getTransferEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
    TransferEventResponse typedResponse = new TransferEventResponse();
    typedResponse.log = log;
    typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
    typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
    return typedResponse;
  }

  public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
  }

  public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
    return transferEventFlowable(filter);
  }

  public RemoteFunctionCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
    final Function function = new Function(
        FUNC_APPROVE,
        Arrays.asList(new org.web3j.abi.datatypes.Address(160, to),
                      new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
        Collections.emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<BigInteger> balanceOf(String owner) {
    final Function function = new Function(FUNC_BALANCEOF,
        List.of(new Address(160, owner)),
        List.of(new TypeReference<Uint256>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<String> baseURI() {
    final Function function = new Function(FUNC_BASEURI,
        List.of(),
        List.of(new TypeReference<Utf8String>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<TransactionReceipt> changeAdmin(String newAdmin) {
    final Function function = new Function(
        FUNC_CHANGEADMIN,
        List.of(new Address(160, newAdmin)),
        Collections.emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> changeSigner(String newSigner) {
    final Function function = new Function(
        FUNC_CHANGESIGNER,
        List.of(new Address(160, newSigner)),
        Collections.emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<Boolean> exists(BigInteger tokenId) {
    final Function function = new Function(FUNC_EXISTS,
        List.of(new Uint256(tokenId)),
        List.of(new TypeReference<Bool>() {
        }));
    return executeRemoteCallSingleValueReturn(function, Boolean.class);
  }

  public RemoteFunctionCall<String> getApproved(BigInteger tokenId) {
    final Function function = new Function(FUNC_GETAPPROVED,
        List.of(new Uint256(tokenId)),
        List.of(new TypeReference<Address>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<Boolean> isApprovedForAll(String owner, String operator) {
    final Function function = new Function(FUNC_ISAPPROVEDFORALL,
                                           Arrays.asList(new org.web3j.abi.datatypes.Address(160, owner),
                                                         new org.web3j.abi.datatypes.Address(160, operator)),
        List.of(new TypeReference<Bool>() {
        }));
    return executeRemoteCallSingleValueReturn(function, Boolean.class);
  }

  public RemoteFunctionCall<Boolean> isSignatureValid(byte[] signature, byte[] messageHash) {
    final Function function = new Function(FUNC_ISSIGNATUREVALID,
                                           Arrays.asList(new org.web3j.abi.datatypes.DynamicBytes(signature),
                                                         new org.web3j.abi.datatypes.generated.Bytes32(messageHash)),
        List.of(new TypeReference<Bool>() {
        }));
    return executeRemoteCallSingleValueReturn(function, Boolean.class);
  }

  public RemoteFunctionCall<TransactionReceipt> mint(SBTInfo info, byte[] signature) {
    final Function function = new Function(
        FUNC_MINT,
        Arrays.<Type>asList(info,
                            new org.web3j.abi.datatypes.DynamicBytes(signature)),
        Collections.emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<String> name() {
    final Function function = new Function(FUNC_NAME,
        List.of(),
        List.of(new TypeReference<Utf8String>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<String> ownerOf(BigInteger tokenId) {
    final Function function = new Function(FUNC_OWNEROF,
        List.of(new Uint256(tokenId)),
        List.of(new TypeReference<Address>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId) {
    final Function function = new Function(
        FUNC_safeTransferFrom,
        Arrays.asList(new org.web3j.abi.datatypes.Address(160, from),
                      new org.web3j.abi.datatypes.Address(160, to),
                      new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
        Collections.emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, byte[] data) {
    final Function function = new Function(
        FUNC_safeTransferFrom,
        Arrays.asList(new org.web3j.abi.datatypes.Address(160, from),
                      new org.web3j.abi.datatypes.Address(160, to),
                      new org.web3j.abi.datatypes.generated.Uint256(tokenId),
                      new org.web3j.abi.datatypes.DynamicBytes(data)),
        Collections.emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator, Boolean approved) {
    final Function function = new Function(
        FUNC_SETAPPROVALFORALL,
        Arrays.asList(new org.web3j.abi.datatypes.Address(160, operator),
                      new org.web3j.abi.datatypes.Bool(approved)),
        Collections.emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> setBaseURI(String newBaseURI) {
    final Function function = new Function(
        FUNC_SETBASEURI,
        List.of(new Utf8String(newBaseURI)),
        Collections.emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
    final Function function = new Function(FUNC_SUPPORTSINTERFACE,
        List.of(new Bytes4(interfaceId)),
        List.of(new TypeReference<Bool>() {
        }));
    return executeRemoteCallSingleValueReturn(function, Boolean.class);
  }

  public RemoteFunctionCall<String> symbol() {
    final Function function = new Function(FUNC_SYMBOL,
        List.of(),
        List.of(new TypeReference<Utf8String>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<String> tokenURI(BigInteger tokenId) {
    final Function function = new Function(FUNC_TOKENURI,
        List.of(new Uint256(tokenId)),
        List.of(new TypeReference<Utf8String>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokenId) {
    final Function function = new Function(
        FUNC_TRANSFERFROM,
        Arrays.asList(new org.web3j.abi.datatypes.Address(160, from),
                      new org.web3j.abi.datatypes.Address(160, to),
                      new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
        Collections.emptyList());
    return executeRemoteCallTransaction(function);
  }

  @Deprecated
  public static CommunityNFT load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return new CommunityNFT(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static CommunityNFT load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
    return new CommunityNFT(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static CommunityNFT load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return new CommunityNFT(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static CommunityNFT load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return new CommunityNFT(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static class SBTInfo extends StaticStruct {
    public String receiver;

    public BigInteger tokenId;

    public BigInteger deadline;

    public byte[] salt;

    public SBTInfo(String receiver, BigInteger tokenId, BigInteger deadline, byte[] salt) {
      super(new org.web3j.abi.datatypes.Address(160, receiver),
            new org.web3j.abi.datatypes.generated.Uint256(tokenId),
            new org.web3j.abi.datatypes.generated.Uint256(deadline),
            new org.web3j.abi.datatypes.generated.Bytes32(salt));
      this.receiver = receiver;
      this.tokenId = tokenId;
      this.deadline = deadline;
      this.salt = salt;
    }

    public SBTInfo(Address receiver, Uint256 tokenId, Uint256 deadline, Bytes32 salt) {
      super(receiver, tokenId, deadline, salt);
      this.receiver = receiver.getValue();
      this.tokenId = tokenId.getValue();
      this.deadline = deadline.getValue();
      this.salt = salt.getValue();
    }
  }

  public static class ApprovalEventResponse extends BaseEventResponse {
    public String owner;

    public String approved;

    public BigInteger tokenId;
  }

  public static class ApprovalForAllEventResponse extends BaseEventResponse {
    public String owner;

    public String operator;

    public Boolean approved;
  }

  public static class TransferEventResponse extends BaseEventResponse {
    public String from;

    public String to;

    public BigInteger tokenId;
  }
}
