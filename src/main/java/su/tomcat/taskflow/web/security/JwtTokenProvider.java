package su.tomcat.taskflow.web.security;

import io.jsonwebtoken.Claims;
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
import su.tomcat.taskflow.domain.user.UserEntity;
import su.tomcat.taskflow.service.UserService;
import su.tomcat.taskflow.service.props.JwtProperties;
import su.tomcat.taskflow.web.dto.auth.JwtResponseDto;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final JwtProperties jwtProperties;
  private final UserDetailsService userDetailsService;
  private final UserService userService;
  private SecretKey SECRET_KEY;

  @PostConstruct
  public void init() {
    this.SECRET_KEY = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
  }

  public String createAccessToken(Long userId, String username, Set<Role> roles) {
    Claims claims = Jwts.claims()
        .subject(username)
        .build();
    claims.put("id", userId);
    claims.put("roles", resolveRoles(roles));


    Date now = getCurrentDate();
    Date expiredDate = getExpirationDate(jwtProperties.getAccess());

    return Jwts.builder()
        .claims(claims)
        .issuedAt(now)
        .expiration(expiredDate)
        .signWith(SECRET_KEY)
        .compact();
  }

  public String createRefreshToken(Long userId, String username) {
    Claims claims = Jwts.claims()
        .subject(username)
        .build();
    claims.put("id", userId);


    Date now = getCurrentDate();
    Date expiredDate = getExpirationDate(jwtProperties.getRefresh());

    return Jwts.builder()
        .claims(claims)
        .issuedAt(now)
        .expiration(expiredDate)
        .signWith(SECRET_KEY)
        .compact();
  }

  public JwtResponseDto refreshTokens(String refreshToken) {
    JwtResponseDto jwtResponseDto = new JwtResponseDto();

    if (!validateToken(refreshToken)) {
      throw new AccessDeniedException();
    }

    Long userId = Long.valueOf(getId(refreshToken));
    UserEntity user = userService.getById(userId);

    jwtResponseDto.setAccessToken(createAccessToken(user.getId(), user.getEmail(), user.getRoles()));
    jwtResponseDto.setRefreshToken(createRefreshToken(user.getId(), user.getEmail()));

    return jwtResponseDto;
  }

  public boolean validateToken(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(SECRET_KEY)
        .build()
        .parseSignedClaims(token)
        .getPayload();

    return !claims.getExpiration()
        .before(new Date());
  }

  public Authentication getAuthentication(String token) {
    String userEmail = getEmail(token);
    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private String getEmail(String token) {
    return Jwts.parser()
        .verifyWith(SECRET_KEY)
        .build()
        .parseSignedClaims(token)
        .getPayload().getSubject();
  }

  private String getId(String token) {
    return Jwts.parser()
        .verifyWith(SECRET_KEY)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get("id")
        .toString();

  }

  private List<String> resolveRoles(Set<Role> roles) {
    return roles.stream()
        .map(Enum::name)
        .collect(Collectors.toList());
  }

  private Date getCurrentDate() {
    return new Date();
  }

  private Date getExpirationDate(Long validityPeriod) {
    return new Date(getCurrentDate().getTime() + validityPeriod);
  }

}
