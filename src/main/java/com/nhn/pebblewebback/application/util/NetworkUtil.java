package com.nhn.pebblewebback.application.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * network util.
 */
@Slf4j
@UtilityClass
public class NetworkUtil {

  private static final String UNKNOWN = "unknown";
  private static final String COMMA = ",";

  /**
   * client ip. comma(,) 로 여러개 일 수 있다.
   *
   * @return client ip
   */
  public static String getClientIps() {

    return getClientIp(
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
    );
  }

  /**
   * client ip, 여러개 인 경우 처음 ip 리턴.
   *
   * @return client ip
   */
  public static String getClientIp() {

    final String ips = getClientIps();
    if (StringUtils.contains(ips, COMMA)) {
      return StringUtils.split(ips, COMMA)[0];
    }
    return ips;
  }

  /**
   * client ip.
   *
   * @param req request
   * @return client ip
   */
  public static String getClientIp(HttpServletRequest req) {
    final String ips = getClientIpInternal(req);
    if (StringUtils.contains(ips, COMMA)) {
      return StringUtils.split(ips, COMMA)[0];
    }
    return ips;
  }

  private static String getClientIpInternal(HttpServletRequest req) {

    // HTTP_X_FORWARDED_FOR 는 comma(,) 로 여러개 올 수 있음.
    String ip = req.getHeader("HTTP_X_FORWARDED_FOR");
    if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
      ip = req.getHeader("Proxy-Client-IP");
    }
    if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
      ip = req.getHeader("WL-Proxy-Client-IP");
    }
    if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
      ip = req.getHeader("HTTP_CLIENT_IP");
    }
    if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
      ip = req.getHeader("X-Forwarded-For");
    }
    if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(ip, UNKNOWN)) {
      ip = req.getRemoteAddr();
    }

    if (StringUtils.equals(ip, "0:0:0:0:0:0:0:1")) {
      ip = "127.0.0.1";
    }
    return ip;
  }
}
