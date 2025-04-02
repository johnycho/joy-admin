package com.nhn.pebblewebback;

import static com.nhn.pebblewebback.application.util.EthUtils.getTransactionStatus;
import static com.nhn.pebblewebback.domain.entity.constant.MintingStatus.FAILURE;
import static com.nhn.pebblewebback.domain.entity.constant.MintingStatus.INVALID;
import static com.nhn.pebblewebback.domain.entity.constant.MintingStatus.PENDING;
import static com.nhn.pebblewebback.domain.entity.constant.MintingStatus.SUCCESS;
import com.nhn.pebblewebback.annotations.NscSpringBootTest;
import com.nhn.pebblewebback.application.config.CommunityNFT;
import com.nhn.pebblewebback.infra.etherscan.EtherScanApiClient;
import java.io.IOException;
import java.math.BigInteger;
import org.apache.commons.lang3.StringUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.abi.TypeEncoder;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

@Disabled
@NscSpringBootTest
class Web3jTests {

  @Autowired
  private Web3j web3j;

  @Autowired
  private EtherScanApiClient etherScanApiClient;

  @Test
  public void 클라이언트_버전_확인() throws IOException {
    assertTrue(StringUtils.isNotBlank(web3j.web3ClientVersion().send().getWeb3ClientVersion()));
  }

  @Test
  public void 존재하지_않는_트랜잭션_확인() throws IOException {
    assertEquals(getTransactionStatus(web3j, "0xtesttransaction"), INVALID);
  }

  @Test
  public void 트랜잭션_성공_확인() throws IOException {
    assertEquals(getTransactionStatus(web3j, "0x210fd94ea40c31854661c2da04f1f386d59a0025a32259fa864adc35bbb51963"), SUCCESS);
  }

  @Test
  public void 트랜잭션_실패_확인() throws IOException {
    assertEquals(getTransactionStatus(web3j, "0x66b5d3e2a830b0fd10439112d60e0240cea042b6f4bd11eac6721116cf0b4020"), FAILURE);
  }

  @Test
  @Disabled("pending 상태로 유지되는 트랜잭션이 없으므로 테스트 불가")
  public void 트랜잭션_진행중_확인() throws IOException {
    assertEquals(getTransactionStatus(web3j, "0xcb2620529f3071ea4830b301de0f09ab21e505d0c39acbbb17d496fca74ec476"), PENDING);
  }

  @Test
  public void 보유중인_NFT_갯수_조회() {
    assertEquals(1, etherScanApiClient.getNftCount("0x4db61a3374a06c5ae6c60fd6829afc48d50f5101"));
  }

  @Test
  public void 서명_테스트() throws Exception {
    final String sbtContractAddress = "0x0507FE482152b85CCa4355D0D7980ccC3D1eC8F7";
    Credentials invokerCred = Credentials.create("34811f6e854a07193a84aeb2cfd8664f6fc2e6178286d0bdcebecd0d344c4f3e");
    CommunityNFT contract = CommunityNFT.load(sbtContractAddress, web3j, invokerCred, new DefaultGasProvider());

    BigInteger tokenId = new BigInteger("1");
    BigInteger deadline = new BigInteger("1710638178");
    byte[] salt = new byte[32];
    CommunityNFT.SBTInfo info = new CommunityNFT.SBTInfo(invokerCred.getAddress(), tokenId, deadline, salt);

    String encodedMsg = TypeEncoder.encode(info);
    String msgString = Hash.sha3(encodedMsg);
    byte[] message = Numeric.hexStringToByteArray(msgString);

    Credentials signerCred = Credentials.create("655842276f3ff6dcdebb9dc45732fcb4879ecfee420b81fc454214e5a0907f80");
    Sign.SignatureData signature = Sign.signPrefixedMessage(message, signerCred.getEcKeyPair());
    byte[] sign = new byte[65];
    System.arraycopy(signature.getR(), 0, sign, 0, 32);
    System.arraycopy(signature.getS(), 0, sign, 32, 32);
    System.arraycopy(signature.getV(), 0, sign, 64, 1);
    System.out.printf("Message : %s\nSign : %s\n", msgString, Numeric.toHexString(sign));

    Boolean verified = contract.isSignatureValid(sign, message).send();
    System.out.println(verified);
  }
}
