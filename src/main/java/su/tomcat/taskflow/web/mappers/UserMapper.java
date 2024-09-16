package su.tomcat.taskflow.web.mappers;

import org.mapstruct.Mapper;
import su.tomcat.taskflow.domain.user.UserEntity;
import su.tomcat.taskflow.web.dto.user.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(UserEntity user);

  UserEntity toEntity(UserDto dto);
}
