package com.joy.web.calendar.application.service;

import static java.security.spec.NamedParameterSpec.ED25519;
import com.joy.config.JoyProperties;
import com.joy.web.calendar.application.constant.EventType;
import com.joy.web.calendar.application.dto.SignatureDto.SignatureResponse;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EdECPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.utils.Numeric;

/**
 * 메시지 서명 서비스.
 */
@Service
@RequiredArgsConstructor
public class SignatureService {
  private final JoyProperties joyProperties;

  public SignatureResponse sign(final String type, final String message) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
    // 서명 인스턴스 생성
    final Signature signature = Signature.getInstance(ED25519.getName());

    // 서명 생성
    signature.initSign(getPrivateKey(type));
    signature.update(Numeric.hexStringToByteArray(message));

    return SignatureResponse.of(message, Numeric.toHexString(signature.sign()));
  }

  private PrivateKey getPrivateKey(final String type) throws NoSuchAlgorithmException, InvalidKeySpecException {
    return switch (EventType.getTypeByCode(type)) {
      case COMMUNITY_NFT -> generatePrivateKey(joyProperties.getCommunityNft().getMessageSignerPrivateKey());
      case GIVEAWAY -> generatePrivateKey(joyProperties.getGiveaway().getMessageSignerPrivateKey());
    };
  }

  private PrivateKey generatePrivateKey(final String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
    return KeyFactory.getInstance(ED25519.getName())
                     .generatePrivate(new EdECPrivateKeySpec(ED25519, Numeric.hexStringToByteArray(privateKey)));
  }
}
