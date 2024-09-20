package su.tomcat.taskflow.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import su.tomcat.taskflow.domain.user.User;
import su.tomcat.taskflow.service.AuthService;
import su.tomcat.taskflow.service.UserService;
import su.tomcat.taskflow.web.dto.auth.JwtResponseDto;
import su.tomcat.taskflow.web.dto.auth.LoginRequestDto;
import su.tomcat.taskflow.web.dto.auth.LoginResponseDto;
import su.tomcat.taskflow.web.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) {

    LoginResponseDto loginResponseDto = new LoginResponseDto();
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
    User user = userService.getByEmail(loginRequestDto.getEmail());

    loginResponseDto.setId(user.getId());
    loginResponseDto.setUsername(user.getUsername());
    loginResponseDto.setEmail(user.getEmail());
    loginResponseDto.setUserPic(user.getUserPic());
    loginResponseDto.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getEmail(), user.getRoles()));

    return loginResponseDto;
  }

  @Override
  public JwtResponseDto refresh(String refreshToken) {
    return jwtTokenProvider.refreshTokens(refreshToken);
  }
}
