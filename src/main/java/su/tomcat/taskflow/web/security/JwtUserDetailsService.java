package su.tomcat.taskflow.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import su.tomcat.taskflow.domain.user.User;
import su.tomcat.taskflow.service.UserService;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private final UserService userService;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.getByEmail(username);
    return JwtEntityFactory.create(user);
  }
}
