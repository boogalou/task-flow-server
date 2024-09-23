package su.tomcat.taskflow.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import su.tomcat.taskflow.domain.user.User;
import su.tomcat.taskflow.service.AuthService;
import su.tomcat.taskflow.service.UserService;
import su.tomcat.taskflow.web.dto.auth.AuthResponseDto;
import su.tomcat.taskflow.web.dto.auth.JwtResponseDto;
import su.tomcat.taskflow.web.dto.auth.LoginRequestDto;
import su.tomcat.taskflow.web.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public AuthResponseDto login(LoginRequestDto loginRequestDto) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
    User user = userService.getByEmail(loginRequestDto.getEmail());
    String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getEmail(), user.getRoles());

    return createAuthResponse(user, accessToken);
  }

  @Override
  public AuthResponseDto refresh(String refreshToken) {
    JwtResponseDto jwtResponseDto = jwtTokenProvider.refreshToken(refreshToken);
    Long userId = Long.valueOf(jwtTokenProvider.getId(jwtResponseDto.getAccessToken()));
    User user = userService.getById(userId);

    return createAuthResponse(user, jwtResponseDto.getAccessToken());
  }

  private AuthResponseDto createAuthResponse(User user, String accessToken) {
    AuthResponseDto authResponseDto = new AuthResponseDto();
    authResponseDto.setId(user.getId());
    authResponseDto.setUsername(user.getUsername());
    authResponseDto.setEmail(user.getEmail());
    authResponseDto.setUserPic(user.getUserPic());
    authResponseDto.setAccessToken(accessToken);
    return authResponseDto;
  }
}
