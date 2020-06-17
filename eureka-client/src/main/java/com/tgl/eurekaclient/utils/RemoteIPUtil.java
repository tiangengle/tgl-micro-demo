package com.tgl.eurekaclient.utils;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tgl
 * @date 2020-05-08
*/
public class RemoteIPUtil {

  public static String getIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Real-IP");
    if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip.trim())) {
      ip = request.getHeader("remote-host");
    }
    if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip.trim())) {
      ip = request.getRemoteAddr();
    }
    if (StringUtils.isNotBlank(ip)) {
      if (ip.startsWith("10.")) {
        String tip = request.getParameter("ip");
        if (StringUtils.isNotBlank(tip)) {
          ip = tip;
        }
      }
    }
    return ip;
  }

  public static boolean validIp(String ipAddress) {
    if(StringUtils.isBlank(ipAddress)) {
        return false;
    }
    String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
    Pattern pattern = Pattern.compile(ip);
    Matcher matcher = pattern.matcher(ipAddress);
    return matcher.matches();
  }
}
