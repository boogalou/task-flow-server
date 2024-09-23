package su.tomcat.taskflow.service;

import su.tomcat.taskflow.web.dto.auth.AuthResponseDto;
import su.tomcat.taskflow.web.dto.auth.JwtResponseDto;
import su.tomcat.taskflow.web.dto.auth.LoginRequestDto;

public interface AuthService {

  AuthResponseDto login(LoginRequestDto loginRequestDto);

  AuthResponseDto refresh(String refreshToken);

}
