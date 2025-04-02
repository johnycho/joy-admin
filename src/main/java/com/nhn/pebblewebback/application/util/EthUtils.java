package com.nhn.pebblewebback.application.util;

import static com.nhn.pebblewebback.domain.entity.constant.MintingStatus.FAILURE;
import static com.nhn.pebblewebback.domain.entity.constant.MintingStatus.INVALID;
import static com.nhn.pebblewebback.domain.entity.constant.MintingStatus.PENDING;
import static com.nhn.pebblewebback.domain.entity.constant.MintingStatus.SUCCESS;
import static com.nhn.pebblewebback.domain.entity.constant.MintingStatus.UNKNOWN;
import com.nhn.pebblewebback.domain.entity.constant.MintingStatus;
import com.nhn.pebblewebback.exception.constant.ExceptionInfo;
import java.io.IOException;
import java.util.Optional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public final class EthUtils {

  private EthUtils() {
    throw ExceptionInfo.NOT_ALLOWED_CONSTRUCTOR_CALL.exception();
  }

  public static MintingStatus getTransactionStatus(final Web3j web3j, final String transactionHash) throws IOException {
    if (web3j.ethGetTransactionByHash(transactionHash).send().getTransaction().isEmpty()) {
      return INVALID;
    }
    final Optional<TransactionReceipt> transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send().getTransactionReceipt();
    if (transactionReceipt.isEmpty()) {
      return PENDING;
    }
    final String status = transactionReceipt.get().getStatus();
    return switch (status) {
      case "0x1" -> SUCCESS;
      case "0x0" -> FAILURE;
      default -> UNKNOWN;
    };
  }
}
