package su.tomcat.taskflow.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.tomcat.taskflow.domain.user.UserEntity;
import su.tomcat.taskflow.service.AuthService;
import su.tomcat.taskflow.service.UserService;
import su.tomcat.taskflow.web.dto.auth.*;
import su.tomcat.taskflow.web.dto.user.UserDto;
import su.tomcat.taskflow.web.mappers.UserMapper;
import su.tomcat.taskflow.web.validation.OnCreate;

@RestController()
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

  private final AuthService authService;
  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping("/registration")
  public UserDto registration(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
    UserEntity user = userMapper.toEntity(userDto);
    UserEntity createdUser = userService.create(user);
    return userMapper.toDto(createdUser);
  }

  @PostMapping("/login")
  public LoginResponseDto login(@Validated @RequestBody LoginRequestDto loginRequestDto) {
    return authService.login(loginRequestDto);
  }

  @PostMapping("/refresh")
  public JwtResponseDto refresh(@RequestBody String refreshToken) {
    return authService.refresh(refreshToken);
  }
}

