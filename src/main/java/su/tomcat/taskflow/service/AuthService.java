package su.tomcat.taskflow.service;

import su.tomcat.taskflow.web.dto.auth.JwtResponseDto;
import su.tomcat.taskflow.web.dto.auth.LoginRequestDto;
import su.tomcat.taskflow.web.dto.auth.LoginResponseDto;

public interface AuthService {

  LoginResponseDto login(LoginRequestDto loginRequestDto);

  JwtResponseDto refresh(String refreshToken);

}
