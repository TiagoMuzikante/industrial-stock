package space.industock.industrial_stock.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

  private Claims extractAllClaims(String token){
    return Jwts.parser()
        .setSigningKey(getSecret())
        .build()
        .parseClaimsJws(token)
        .getBody();
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
    return extractAllClaims(token).getSubject();
  }

  public Date extractExpiration(String token){
    return extractAllClaims(token).getExpiration();
  }

  public boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }

  public boolean isValid(String token){
    try {
      extractUserId(token);
      if(isTokenExpired(token)){
        Claims claims = extractAllClaims(token);
        throw new ExpiredJwtException(null, claims, "Token expirado");
      }
      return true;
    } catch (Exception ex){
      return false;
    }
  }


}
