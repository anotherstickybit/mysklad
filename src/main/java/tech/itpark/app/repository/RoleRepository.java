package tech.itpark.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.itpark.app.model.Role;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RoleRepository {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public RoleRepository(DataSource ds) {
        this.template = new NamedParameterJdbcTemplate(ds);
    }

    public List<Role> getAll() {
        return template.query("select id, name from roles",
                (resultSet, i) -> new Role(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                ));
    }

    public Optional<Role> getById(Long id) {
        return Optional.ofNullable(template.queryForObject("select id, name from roles where id = :id",
                Map.of("id", id), ((resultSet, i) -> new Role(
                        resultSet.getLong("id"),
                        resultSet.getString("name")))));
    }

    public Role getByName(String name) {
        return template.queryForObject("select id, name from roles where name = :name",
                Map.of("name", name), ((resultSet, i) -> new Role(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                )));
    }

    public List<Role> getByUserId(Long id) {
        return template.query("select id, name from roles " +
                        "join user_roles ur on roles.id = ur.role_id where ur.user_id = :id",
                Map.of(
                        "id", id
                ), (resultSet, i) -> new Role(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                ));
    }

}
