package su.tomcat.taskflow.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import su.tomcat.taskflow.domain.user.Role;
import su.tomcat.taskflow.domain.user.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtEntityFactory {

  public static JwtEntity create(UserEntity user) {
    return new JwtEntity(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
    );
  }

  private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
    return roles.stream()
        .map(Enum:: name)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
