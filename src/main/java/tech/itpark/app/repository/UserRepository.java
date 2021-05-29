package tech.itpark.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.itpark.app.dto.user.UserModelResponseDto;
import tech.itpark.app.exception.DataAccessException;
import tech.itpark.app.exception.UserNotFoundException;
import tech.itpark.app.model.Role;
import tech.itpark.app.model.Status;
import tech.itpark.app.model.TokenAuth;
import tech.itpark.app.model.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public UserRepository(DataSource ds) {
        this.template = new NamedParameterJdbcTemplate(ds);
    }

    public List<UserModelResponseDto> getAll() {
        return template.query("select id, username, password, secret, status from users",
                ((resultSet, i) -> new UserModelResponseDto(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        Status.valueOf(resultSet.getString("status"))
                )));
    }

    public User save(User user) {
        final var id = Optional.ofNullable(template.queryForObject("insert into users (username, password, secret, status) " +
                        "values (:username, :password, :secret, :status) returning id;",
                new MapSqlParameterSource(Map.of(
                        "username", user.getUsername(),
                        "password", user.getPassword(),
                        "secret", user.getSecret(),
                        "status", user.getStatus().toString()
                )), ((resultSet, i) -> resultSet.getLong("id")))).orElseThrow(DataAccessException::new);
        for (Role role : user.getRoles()) {
            template.update("insert into user_roles (user_id, role_id) values (:userId, :roleId)",
                    new MapSqlParameterSource(Map.of(
                            "userId", id,
                            "roleId", role.getId()
                    )));
        }
        user.setId(id);
        return user;
    }

    public void save(TokenAuth auth) {
        template.update("insert into tokens (user_id, token) values (:userId, :token)",
                new MapSqlParameterSource(Map.of(
                        "userId", auth.getUserId(),
                        "token", auth.getToken()
                )));
    }

    public void updatePassword(User user) {
        template.update("update users set password = :password where id = :id",
                new MapSqlParameterSource(Map.of(
                        "id", user.getId(),
                        "password", user.getPassword()
                )));
    }

    public Optional<User> getById(Long id) {
        final var roles = template.query("select ur.role_id, r.name from user_roles ur " +
                        "inner join roles r on r.id = ur.role_id where ur.user_id = :id",
                Map.of("id", id), ((resultSet, i) -> new Role(
                        resultSet.getLong("role_id"),
                        resultSet.getString("name")
                )));
        return Optional.ofNullable(template.queryForObject("select id, username, password, secret, status from users where id = :id",
                Map.of("id", id),
                ((resultSet, i) -> new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("secret"),
                        Status.valueOf(resultSet.getString("status")),
                        roles
                ))));
    }

    public Long deleteById(Long id) {
        template.update("update users set status = :status where id = :id",
                new MapSqlParameterSource(Map.of(
                        "id", id,
                        "status", Status.DELETED.toString()
                )));
        return id;
    }

    public Optional<User> getByLogin(String login) {
        final var id = Optional.ofNullable(template
                .queryForObject("select id from users where username = :login",
                        Map.of("login", login), ((resultSet, i) -> resultSet.getLong("id"))))
                .orElseThrow(UserNotFoundException::new);
       return getById(id);
    }

    public Optional<User> getByToken(String token) {
        if (token == null) {
            return Optional.empty();
        }
        final var id = Optional.ofNullable(template.queryForObject("select id from users " +
                        "join tokens t on users.id = t.user_id where t.token = :token",
                Map.of(
                        "token", token
                ), ((resultSet, i) -> resultSet.getLong("id")))).orElseThrow(UserNotFoundException::new);
        return getById(id);
    }

    public Optional<User> setRole(Long userId, Long roleId) {
        template.update("insert into user_roles (user_id, role_id) values (:user_id, :role_id)",
                new MapSqlParameterSource(Map.of(
                        "user_id", userId,
                        "role_id", roleId
                )));
        return getById(userId);
    }

    public Optional<User> removeRole(Long userId, Long roleId) {
        template.update("delete from user_roles where user_id = :user_id and role_id = :role_id",
                new MapSqlParameterSource(Map.of(
                        "user_id", userId,
                        "role_id", roleId
                )));
        return getById(userId);
    }
}
