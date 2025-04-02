package com.nhn.pebblewebback.infra.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public enum EtherScanApiActionType {
  // module = "account"
  BALANCE("balance"),
  BALANCE_FOR_MULTIPLE_ADDRESSES("balancemulti"),
  TRANSACTION_LIST("txlist"),
  TRANSACTION_LIST_INTERNAL("txlistinternal"),
  TOKEN_TRANSACTION("tokentx"),
  TOKEN_NFT_TRANSACTION("tokennfttx"),
  TOKEN_ERC1155_TRANSACTION("token1155tx"),
  GET_MINED_BLOCKS("getminedblocks"),
  TRANSACTIONS_BEACON_WITHDRAWAL("txsBeaconWithdrawal"),
  BALANCE_HISTORY("balancehistory"),
  TOKEN_BALANCE("tokenbalance"),
  TOKEN_BALANCE_HISTORY("tokenbalancehistory"),
  ADDRESS_TOKEN_BALANCE("addresstokenbalance"),
  ADDRESS_TOKEN_NFT_BALANCE("addresstokennftbalance"),
  ADDRESS_TOKEN_NFT_INVENTORY("addresstokennftinventory"),

  // module = "contract"
  GET_ABI("getabi"),
  GET_SOURCE_CODE("getsourcecode"),
  GET_CONTRACT_CREATION("getcontractcreation"),

  // module = "transaction"
  GET_STATUS("getstatus"),
  GET_TRANSACTION_RECEIPT_STATUS("gettxreceiptstatus"),

  // module = "block"
  GET_BLOCK_REWARD("getblockreward"),
  GET_BLOCK_COUNTDOWN("getblockcountdown"),
  GET_BLOCK_NO_BY_TIME("getblocknobytime"),

  // module = "stats"
  DAILY_AVERAGE_BLOCK_SIZE("dailyavgblocksize"),
  DAILY_BLOCK_COUNT("dailyblkcount"),
  DAILY_BLOCK_REWARDS("dailyblockrewards"),
  DAILY_AVERAGE_BLOCK_TIME("dailyavgblocktime"),
  DAILY_UNCLE_BLOCK_COUNT("dailyuncleblkcount"),
  TOKEN_SUPPLY("tokensupply"),
  TOKEN_SUPPLY_HISTORY("tokensupplyhistory"),
  DAILY_AVERAGE_GAS_LIMIT("dailyavggaslimit"),
  DAILY_GAS_USED("dailygasused"),
  DAILY_AVERAGE_GAS_PRICE("dailyavggasprice"),
  ETHER_SUPPLY("ethsupply"),
  ETHER_SUPPLY2("ethsupply2"),
  ETHER_PRICE("ethprice"),
  CHAIN_SIZE("chainsize"),
  NODE_COUNT("nodecount"),
  DAILY_NETWORK_TRANSACTION_FEE("dailytxnfee"),
  DAILY_NEW_ADDRESS("dailynewaddress"),
  DAILY_NETWORK_UTILIZATION("dailynetutilization"),
  DAILY_AVERAGE_HASH_RATE("dailyavghashrate"),
  DAILY_TRANSACTION_COUNT("dailytx"),
  DAILY_AVERAGE_NETWORK_DIFFICULTY("dailyavgnetdifficulty"),
  ETHER_HISTORICAL_DAILY_MARKET_CAP("ethdailymarketcap"),
  ETHER_DAILY_PRICE("ethdailyprice"),

  // module = "logs"
  GET_LOGS("getLogs"),

  // module = "proxy"
  ETHER_BLOCK_NUMBER("eth_blockNumber"),
  ETHER_GET_BLOCK_BY_NUMBER("eth_getBlockByNumber"),
  ETHER_GET_UNCLE_BY_BLOCK_NUMBER_AND_INDEX("eth_getUncleByBlockNumberAndIndex"),
  ETHER_GET_BLOCK_TRANSACTION_COUNT_BY_NUMBER("eth_getBlockTransactionCountByNumber"),
  ETHER_GET_TRANSACTION_BY_HASH("eth_getTransactionByHash"),
  ETHER_GET_TRANSACTION_BY_BLOCK_NUMBER_AND_INDEX("eth_getTransactionByBlockNumberAndIndex"),
  ETHER_GET_TRANSACTION_COUNT("eth_getTransactionCount"),
  ETHER_SEND_RAW_TRANSACTION("eth_sendRawTransaction"),
  ETHER_GET_TRANSACTION_RECEIPT("eth_getTransactionReceipt"),
  ETHER_CALL("eth_call"),
  ETHER_GET_CODE("eth_getCode"),
  ETHER_GET_STORAGE_AT("eth_getStorageAt"),
  ETHER_GAS_PRICE("eth_gasPrice"),
  ETHER_ESTIMATE_GAS("eth_estimateGas"),

  // module = "token"
  TOKEN_HOLDER_LIST("tokenholderlist"),
  TOKEN_INFO("tokeninfo"),

  // module = "gastracker"
  GAS_ESTIMATE("gasestimate"),
  GAS_ORACLE("gasoracle"),
  ;

  private final String code;
}
