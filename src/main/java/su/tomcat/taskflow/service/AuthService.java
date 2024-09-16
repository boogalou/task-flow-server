package su.tomcat.taskflow.service;

import su.tomcat.taskflow.web.dto.auth.LoginRequest;
import su.tomcat.taskflow.web.dto.auth.LoginResponse;

public interface AuthService {

  LoginResponse login(LoginRequest loginRequest);

  LoginResponse refresh(String refreshToken);

}
