package com.joy.config.util;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataKeyGenerator {
  private static final Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();

  public static String generateUniqueId() {
    return uuidToBase64();
  }

  public static byte[] generateRandomByte32Array() {
    final UUID uuid = UUID.randomUUID();
    final ByteBuffer bb = ByteBuffer.wrap(new byte[32]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }

  private static String uuidToBase64() {
    return BASE64_URL_ENCODER.encodeToString(uuidToBytes(UUID.randomUUID()));
  }

  private static byte[] uuidToBytes(final UUID uuid) {
    // URL에 포함될 수 있는 Base64 문자열로 변환
    final ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }
}
