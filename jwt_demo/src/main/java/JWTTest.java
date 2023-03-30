import io.jsonwebtoken.*;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTTest {
  private static final RsaKeyHelper RSA_KEY_HELPER = new RsaKeyHelper();


  @Test
  public void test() {
    Long id = 3L;
    String account = "pinda";
    String name = "平台管理员";
    Long orgId = 100L;
    Long stationId = 100L;
    JwtUserInfo jwtInfo = new JwtUserInfo(id, account, name, orgId, stationId);
    String priKeyPath = "pri.key";
    int expire = 300;
    Map<String, Object> header = new HashMap<>();
    //使用RS256签名算法
    header.put("alg", SignatureAlgorithm.RS256.getValue());
    header.put("typ", "JWT");
    JwtBuilder jwtBuilder = Jwts.builder()
       .setHeader(header)
       //设置主题
       .setSubject(String.valueOf(jwtInfo.getUserId()))
       .claim(BaseContextConstants.JWT_KEY_ACCOUNT, jwtInfo.getAccount())
       .claim(BaseContextConstants.JWT_KEY_NAME, jwtInfo.getName())
       .claim(BaseContextConstants.JWT_KEY_ORG_ID, jwtInfo.getOrgId())
       .claim(BaseContextConstants.JWT_KEY_STATION_ID, jwtInfo.getStationId());
    String token = null;
    PrivateKey privateKey = null;
    try {
      InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(priKeyPath);
      DataInputStream dis = new DataInputStream(resourceAsStream);
      byte[] keyBytes = new byte[resourceAsStream.available()];
      dis.readFully(keyBytes);

      PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      privateKey = kf.generatePrivate(spec);

      //返回的字符串便是我们的jwt串了
      String compactJws = jwtBuilder.setExpiration(localDateTime2Date(LocalDateTime.now().plusSeconds(expire)))
         //设置算法（必须）
         .signWith(SignatureAlgorithm.RS256, privateKey)
         //这个是全部设置完成后拼成jwt串的方法
         .compact();
      Token t = new Token(compactJws, expire);
      token = t.getToken();
      // eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzIiwiYWNjb3VudCI6InBpbmRhIiwibmFtZSI6IuW5s-WPsOeuoeeQhuWRmCIsIm9yZ2lkIjoxMDAsInN0YXRpb25pZCI6MTAwLCJleHAiOjE2NTc4NzI3MDZ9.bjNz6tEXNvMw4QbQdymUVQ2W_ta7MVCl0SsTXXi9T8aPZgw8JN-ZziuihjYlnhvBLfDCTUB-dNP4zi-oJWALDBIjCVO4ioasfh1CfBxbTLgV8mP1hcPoQUESmoKQ68DeTQ3gwHeAfrBUvCXYFnSr8EX2zrrhkACZum7o97QaE5U
      System.out.println(token);
    } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      System.out.println(e.getMessage());
    }

    // 解析
    String pubKeyPath = "pub.key";
    InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(pubKeyPath);
    try (DataInputStream dis = new DataInputStream(resourceAsStream)) {
      byte[] keyBytes = new byte[resourceAsStream.available()];
      dis.readFully(keyBytes);
      X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      PublicKey publicKey = kf.generatePublic(spec);
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token);
      JwsHeader headerResp = claimsJws.getHeader();
      System.out.println("header:" + headerResp);
      Claims body = claimsJws.getBody();
      String strUserId = body.getSubject();
      String accountResp = StrHelper.getObjectValue(body.get(BaseContextConstants.JWT_KEY_ACCOUNT));
      String nameResp = StrHelper.getObjectValue(body.get(BaseContextConstants.JWT_KEY_NAME));
      String strOrgId = StrHelper.getObjectValue(body.get(BaseContextConstants.JWT_KEY_ORG_ID));
      String strDepartmentId = StrHelper.getObjectValue(body.get(BaseContextConstants.JWT_KEY_STATION_ID));
      Long userId = NumberHelper.longValueOf0(strUserId);
      Long orgIdResp = NumberHelper.longValueOf0(strOrgId);
      Long departmentId = NumberHelper.longValueOf0(strDepartmentId);
      JwtUserInfo userInfo = new JwtUserInfo(userId, accountResp, nameResp, orgIdResp, departmentId);
      System.out.println(userInfo);
    } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      System.out.println(e.getMessage());
    }

  }


  /**
   * 生成token
   *
   * @param jwtInfo
   * @param expire
   * @return
   */
  public static Token generateUserToken(JwtUserInfo jwtInfo, Integer expire) {
    String priKeyPath = "pri.key";
    if (expire == null || expire <= 0) {
      expire = 300;
    }
    return generateUserToken(jwtInfo, priKeyPath, expire);
  }

  /**
   * 生成用户token
   *
   * @param jwtInfo
   * @param priKeyPath
   * @param expire
   * @return
   */
  public static Token generateUserToken(JwtUserInfo jwtInfo, String priKeyPath, int expire) {
    Map<String, Object> header = new HashMap<>();
    //使用RS256签名算法
    header.put("alg", SignatureAlgorithm.RS256.getValue());
    header.put("typ", "JWT");
    JwtBuilder jwtBuilder = Jwts.builder()
       .setHeader(header)
       //设置主题
       .setSubject(String.valueOf(jwtInfo.getUserId()))
       .claim(BaseContextConstants.JWT_KEY_ACCOUNT, jwtInfo.getAccount())
       .claim(BaseContextConstants.JWT_KEY_NAME, jwtInfo.getName())
       .claim(BaseContextConstants.JWT_KEY_ORG_ID, jwtInfo.getOrgId())
       .claim(BaseContextConstants.JWT_KEY_STATION_ID, jwtInfo.getStationId());
    return generateToken(jwtBuilder, priKeyPath, expire);
  }


  /**
   * 生成token
   *
   * @param builder
   * @param priKeyPath
   * @param expire
   * @return
   */
  protected static Token generateToken(JwtBuilder builder, String priKeyPath, int expire) {
    try {
      //返回的字符串便是我们的jwt串了
      String compactJws = builder.setExpiration(localDateTime2Date(LocalDateTime.now().plusSeconds(expire)))
         //设置算法（必须）
         .signWith(SignatureAlgorithm.RS256, RSA_KEY_HELPER.getPrivateKey(priKeyPath))
         //这个是全部设置完成后拼成jwt串的方法
         .compact();
      return new Token(compactJws, expire);
    } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  /**
   * LocalDateTime转换为Date
   *
   * @param localDateTime
   */
  public static Date localDateTime2Date(LocalDateTime localDateTime) {
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime zdt = localDateTime.atZone(zoneId);
    return Date.from(zdt.toInstant());
  }


  /**
   * 解析token
   *
   * @param token
   * @return
   */
  public static JwtUserInfo getUserInfo(String token) {
    String pubKeyPath = "pub.key";
    return getJwtFromToken(token, pubKeyPath);
  }


  /**
   * 获取token中的用户信息
   *
   * @param token      token
   * @param pubKeyPath 公钥路径
   * @return
   * @throws Exception
   */
  public static JwtUserInfo getJwtFromToken(String token, String pubKeyPath) {
    Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
    JwsHeader header = claimsJws.getHeader();
    System.out.println("header:" + header);
    Claims body = claimsJws.getBody();
    String strUserId = body.getSubject();
    String account = StrHelper.getObjectValue(body.get(BaseContextConstants.JWT_KEY_ACCOUNT));
    String name = StrHelper.getObjectValue(body.get(BaseContextConstants.JWT_KEY_NAME));

    String strOrgId = StrHelper.getObjectValue(body.get(BaseContextConstants.JWT_KEY_ORG_ID));
    String strDepartmentId = StrHelper.getObjectValue(body.get(BaseContextConstants.JWT_KEY_STATION_ID));
    Long userId = NumberHelper.longValueOf0(strUserId);
    Long orgId = NumberHelper.longValueOf0(strOrgId);
    Long departmentId = NumberHelper.longValueOf0(strDepartmentId);
    return new JwtUserInfo(userId, account, name, orgId, departmentId);
  }

  /**
   * 公钥解析token
   *
   * @param token
   * @param pubKeyPath 公钥路径
   * @return
   */
  private static Jws<Claims> parserToken(String token, String pubKeyPath) {
    try {
      return Jwts.parser().setSigningKey(RSA_KEY_HELPER.getPublicKey(pubKeyPath)).parseClaimsJws(token);
    }  //签名错误
    //token 为空
    catch (Exception ex) {
      //过期
      return null;
    }
  }
}
