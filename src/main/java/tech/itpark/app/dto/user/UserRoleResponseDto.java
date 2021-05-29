package tech.itpark.app.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.app.model.Role;
import tech.itpark.app.model.Status;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResponseDto {
    private long id;
    private String username;
    private Status status;
    private List<Role> roles;
}
