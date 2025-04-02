package com.nhn.pebblewebback.infra.etherscan.dto;

import java.util.List;

public record NftTransferEventsApiResponse(String status, String message, List<Transaction> result) {

  public record Transaction(String blockNumber,
                            String timeStamp,
                            String hash,
                            String nonce,
                            String blockHash,
                            String from,
                            String contractAddress,
                            String to,
                            String tokenID,
                            String tokenName,
                            String tokenSymbol,
                            String tokenDecimal,
                            String transactionIndex,
                            String gas,
                            String gasPrice,
                            String gasUsed,
                            String cumulativeGasUsed,
                            String input,
                            String confirmations) {

  }
}
