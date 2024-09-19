package su.tomcat.taskflow.service.impl;

import org.springframework.stereotype.Service;
import su.tomcat.taskflow.service.AuthService;
import su.tomcat.taskflow.web.dto.auth.JwtResponseDto;
import su.tomcat.taskflow.web.dto.auth.LoginRequestDto;
import su.tomcat.taskflow.web.dto.auth.LoginResponseDto;

@Service
public class AuthServiceImpl implements AuthService {
  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) {
    return null;
  }

  @Override
  public JwtResponseDto refresh(String refreshToken) {
    return null;
  }
}
