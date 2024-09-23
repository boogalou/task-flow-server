package su.tomcat.taskflow.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import su.tomcat.taskflow.domain.exception.AccessDeniedException;
import su.tomcat.taskflow.domain.user.Role;
import su.tomcat.taskflow.domain.user.User;
import su.tomcat.taskflow.service.UserService;
import su.tomcat.taskflow.service.props.JwtProperties;
import su.tomcat.taskflow.web.dto.auth.JwtResponseDto;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final JwtProperties jwtProperties;
  private final UserDetailsService userDetailsService;
  private final UserService userService;
  private SecretKey ACCESS_SECRET_KEY;
  private SecretKey REFRESH_SECRET_KEY;

  @PostConstruct
  public void init() {
    this.ACCESS_SECRET_KEY = Keys.hmacShaKeyFor(jwtProperties.getAccessSecret().getBytes());
    this.REFRESH_SECRET_KEY = Keys.hmacShaKeyFor(jwtProperties.getRefreshSecret().getBytes());
  }

  public String createAccessToken(Long userId, String username, Set<Role> roles) {
    return createToken(username, userId, roles, ACCESS_SECRET_KEY, jwtProperties.getAccess());
  }

  public String createRefreshToken(Long userId, String username) {
    return createToken(username, userId, null, REFRESH_SECRET_KEY, jwtProperties.getRefresh());
  }

  public boolean validateAccessToken(String token) {
    return validateToken(token, ACCESS_SECRET_KEY);
  }

  public boolean validateRefreshToken(String token) {
    return validateToken(token, REFRESH_SECRET_KEY);
  }

  private String createToken(String username, Long userId, Set<Role> roles, SecretKey secretKey, long expiration) {
    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + expiration);

    JwtBuilder builder = Jwts.builder()
        .subject(username)
        .claim("id", userId)
        .issuedAt(now)
        .expiration(expiredDate)
        .signWith(secretKey);

    if (roles != null) {
      builder.claim("roles", resolveRoles(roles));
    }

    return builder.compact();

  }

  public JwtResponseDto refreshToken(String token) {
    JwtResponseDto jwtResponseDto = new JwtResponseDto();

    if (!validateRefreshToken(token)) {
      throw new AccessDeniedException();
    }

    Long userId = Long.valueOf(getId(token));
    User user = userService.getById(userId);

    jwtResponseDto.setAccessToken(createAccessToken(user.getId(), user.getEmail(), user.getRoles()));

    return jwtResponseDto;
  }

  private boolean validateToken(String token, SecretKey secretKey) {
    try {
      Claims claims = Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();

      return !claims.getExpiration()
          .before(new Date());
    } catch (JwtException | IllegalArgumentException err) {
      System.out.println(err.getMessage());
      return false;
    }
  }

  public Authentication getAuthentication(String token) {
    String userEmail = getEmail(token);
    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private String getEmail(String token) {
    return getClaimFromToken(token, ACCESS_SECRET_KEY, Claims::getSubject);
  }

  public String getId(String token) {
    return getClaimFromToken(token, ACCESS_SECRET_KEY, claims -> claims.get("id").toString());
  }

  private List<String> resolveRoles(Set<Role> roles) {
    return roles.stream()
        .map(Enum::name)
        .collect(Collectors.toList());
  }

  private <T> T getClaimFromToken(String token, SecretKey secretKey, Function<Claims, T> claimsResolver) {
    Claims claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();

    return claimsResolver.apply(claims);
  }
}
