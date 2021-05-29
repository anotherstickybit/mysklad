package tech.itpark.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.itpark.app.model.WareHouse;
import tech.itpark.app.rowmapper.WareHouseMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class WareHouseRepository {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public WareHouseRepository(DataSource ds) {
        this.template = new NamedParameterJdbcTemplate(ds);
    }

    public List<WareHouse> getAll() {
        return template.query(
                "select id, name from warehouses",
                new WareHouseMapper());
    }

    public Optional<WareHouse> getById(Long id) {
        return Optional.ofNullable(template.queryForObject("select id, name from warehouses where id = :id",
                Map.of("id", id),
                new WareHouseMapper()));
    }

    public Optional<WareHouse> getByName(String name) {
        return Optional.ofNullable(template.queryForObject("select id, name from warehouses where name = :name",
                Map.of("name", name),
                new WareHouseMapper()));
    }

    public WareHouse save(WareHouse wareHouse) {
        return template.queryForObject("insert into warehouses (name) values (:name) returning id, name",
                new MapSqlParameterSource(Map.of(
                    "name", wareHouse.getName()
                )), new WareHouseMapper());
    }

    public WareHouse update(WareHouse wareHouse) {
        return template.queryForObject("update warehouses set name = :name where id = :id returning id, name",
                new MapSqlParameterSource(Map.of(
                        "name", wareHouse.getName(),
                        "id", wareHouse.getId()
                )), new WareHouseMapper());
    }
}
