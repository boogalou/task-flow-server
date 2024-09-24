package su.tomcat.taskflow.web.security.expression;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import su.tomcat.taskflow.domain.user.Role;
import su.tomcat.taskflow.service.UserService;
import su.tomcat.taskflow.web.security.JwtEntity;

@Service("CustomSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

  private final UserService userService;

  public boolean canAccessUser(Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    JwtEntity user = (JwtEntity) authentication.getPrincipal();
    Long userId = user.getId();

    return userId.equals(id);
  }

  private boolean hasAnyRole(Authentication authentication, Role... roles) {
    for (Role role: roles) {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
      if (authentication.getAuthorities().contains(authority)) {
        return true;
      }
    }

    return false;
  }

  public boolean canAccessTask(Long taskId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    JwtEntity user = (JwtEntity) authentication.getPrincipal();
    Long id = user.getId();

    return userService.isTaskOwner(id, taskId);
  }
}
