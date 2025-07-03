package com.joy.config;

import java.util.List;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("joy-admin")
public class JoyProperties {
  private Cors cors = new Cors();
  private String logRoot = StringUtils.EMPTY;
  private CommunityNFT communityNft = new CommunityNFT();
  private Giveaway giveaway = new Giveaway();

  @Data
  public static class Cors {
    private List<String> origins = List.of();
  }

  @Data
  public static class CommunityNFT {
    private String messageSignerPrivateKey = StringUtils.EMPTY;
  }

  @Data
  public static class Giveaway {
    private String messageSignerPrivateKey = StringUtils.EMPTY;
  }
}
