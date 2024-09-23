package su.tomcat.taskflow.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su.tomcat.taskflow.domain.exception.ResourceNotFoundException;
import su.tomcat.taskflow.domain.user.Role;
import su.tomcat.taskflow.domain.user.User;
import su.tomcat.taskflow.repository.UserRepository;
import su.tomcat.taskflow.service.UserService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional(readOnly = true)
  public User getById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public User getByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  @Transactional
  public User update(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return user;
  }

  @Override
  @Transactional
  public User create(User user) {
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new IllegalStateException("User already exists");
    };

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Set<Role> roles = Set.of(Role.ROLE_USER);
    user.setRoles(roles);
    userRepository.save(user);
    return user;
  }

  @Override
  @Transactional
  public void delete(Long userId) {
    userRepository.deleteById(userId);
  }
}
