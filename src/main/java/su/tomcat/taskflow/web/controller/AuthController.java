package su.tomcat.taskflow.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import su.tomcat.taskflow.service.AuthService;
import su.tomcat.taskflow.service.UserService;
import su.tomcat.taskflow.web.dto.auth.*;
import su.tomcat.taskflow.web.dto.user.UserDto;
import su.tomcat.taskflow.web.mappers.UserMapper;
import su.tomcat.taskflow.web.security.JwtTokenProvider;
import su.tomcat.taskflow.web.validation.OnCreate;

@RestController()
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

  private final AuthService authService;
  private final UserService userService;
  private final UserMapper userMapper;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("/registration")
  public ResponseEntity<Void> registration(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
    userService.create(userMapper.toEntity(userDto));
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@Validated @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
    LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
    String refreshToken = jwtTokenProvider.createRefreshToken(loginResponseDto.getId(), loginResponseDto.getEmail());
    Cookie cookie = getCookie(refreshToken);
    response.addCookie(cookie);

    return ResponseEntity.ok(loginResponseDto);
  }

  @PostMapping("/refresh")
  public JwtResponseDto refresh(@RequestBody String refreshToken) {
    return authService.refresh(refreshToken);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse response) {

    Cookie cookie = new Cookie("refreshToken", null);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);

    return ResponseEntity.ok().build();

  }

  private Cookie getCookie(String refreshToken) {
    Cookie cookie = new Cookie("refreshToken", refreshToken);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(7 * 24 * 60 * 60);
    cookie.setPath("/");

    return cookie;
  }
}

