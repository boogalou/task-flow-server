package su.tomcat.taskflow.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import su.tomcat.taskflow.web.validation.OnCreate;
import su.tomcat.taskflow.web.validation.OnUpdate;

@Data
@Schema(name = "User DTO")
public class UserDto {

  @Schema(description = "User id", example = "42")
  @NotNull(message = "ID must be not null", groups = OnUpdate.class)
  private Long id;

  @Schema(description = "User username", example = "John Doe")
  @NotNull(message = "Name is Required", groups = {OnCreate.class, OnUpdate.class})
  @Length(max = 255, message = "Name must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
  private String username;

  @Schema(description = "User email", example = "johndoe@gmail.com")
  @NotNull(message = "Email is Required", groups = {OnCreate.class, OnUpdate.class})
  @Length(max = 255, message = "Name must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
  @Email(message = "Email is Invalid")
  private String email;

  @Schema(description = "User picture", example = "http://example.com/pic/picture.jpg")
  private String userPic;

  private String accessToken;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotNull(message = "Password is Required", groups = {OnCreate.class, OnUpdate.class})
  private String password;
}
