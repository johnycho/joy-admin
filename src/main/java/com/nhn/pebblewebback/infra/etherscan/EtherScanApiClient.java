package com.nhn.pebblewebback.infra.etherscan;

import static com.nhn.pebblewebback.infra.constant.EtherScanApiActionType.TOKEN_NFT_TRANSACTION;
import static com.nhn.pebblewebback.infra.constant.EtherScanApiModuleType.ACCOUNT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EtherScanApiClient {

  private final EtherScanApiFeign etherScanApiFeign;

  public int getNftCount(final String address) {
    // SBT(Soulbound Token, 양도 불가능한 NFT)이므로,
    // 해당 지갑 주소에서 발생한 NFT Transfer Event 갯수 = 해당 지갑이 소유한 NFT 갯수
    return etherScanApiFeign.getNftTransferEvents(ACCOUNT.code(),
                                                  TOKEN_NFT_TRANSACTION.code(),
                                                  "0x0507fe482152b85cca4355d0d7980ccc3d1ec8f7",
                                                  address,
                                                  1,
                                                  10_000,
                                                  0,
                                                  99_999_999,
                                                  "desc",
                                                  "BRZ5RMGUB7CHZKTCKCK91ENWG48BMC8MMC")
                            .result()
                            .size();
  }
}
