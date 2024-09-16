package su.tomcat.taskflow.service.impl;

import org.springframework.stereotype.Service;
import su.tomcat.taskflow.service.AuthService;
import su.tomcat.taskflow.web.dto.auth.LoginRequest;
import su.tomcat.taskflow.web.dto.auth.LoginResponse;

@Service
public class AuthServiceImpl implements AuthService {
  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    return null;
  }

  @Override
  public LoginResponse refresh(String refreshToken) {
    return null;
  }
}
