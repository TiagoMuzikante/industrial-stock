package space.industock.industrial_stock.service.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

  @Value(value = "${jwt.secretLogin}")
  private String secret;

  private SecretKeySpec getSecret(){
    return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
  }

  public String generateToken(UUID userId){
    return Jwts.builder()
        .setSubject(userId.toString())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
        .signWith(SignatureAlgorithm.HS256, getSecret())
        .compact();
  }

  public String extractUserId(String token){
    return Jwts.parser()
        .setSigningKey(getSecret())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean isValid(String token){
    try {
      extractUserId(token);
      return true;
    } catch (Exception e){
      return false;
    }
  }


}
