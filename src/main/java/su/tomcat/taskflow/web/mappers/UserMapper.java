package su.tomcat.taskflow.web.mappers;

import org.mapstruct.Mapper;
import su.tomcat.taskflow.domain.user.User;
import su.tomcat.taskflow.web.dto.user.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {}
