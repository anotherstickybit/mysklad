package tech.itpark.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.framework.security.Auth;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Auth {
    private long id;
    private String username;
    private String password;
    private String secret;
    private Status status;
    private List<Role> roles;
}
