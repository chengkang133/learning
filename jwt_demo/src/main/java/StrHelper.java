import cn.hutool.core.util.StrUtil;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 字符串帮助类
 */
public class StrHelper {
  public static String getObjectValue(Object obj) {
    return obj == null ? "" : obj.toString();
  }

  public static String encode(String value) {
    try {
      return URLEncoder.encode(value, "utf-8");
    } catch (Exception e) {
      return "";
    }
  }

  public static String decode(String value) {
    try {
      return URLDecoder.decode(value, "utf-8");
    } catch (Exception e) {
      return "";
    }
  }

  public static String getOrDef(String val, String def) {
    return StrUtil.isEmpty(val) ? def : val;
  }
}
