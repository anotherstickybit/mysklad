package tech.itpark.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.itpark.app.exception.DataAccessException;
import tech.itpark.app.model.docs.Moving;
import tech.itpark.app.model.docs.MovingItem;
import tech.itpark.app.rowmapper.MovingItemRowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MovingRepository {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public MovingRepository(DataSource ds) {
        this.template = new NamedParameterJdbcTemplate(ds);
    }

    public Optional<Moving> getById(Long id) {
        final var movingItems = template.query("select product_id, count " +
                        "from o2m_moving_products where product_id = :id",
                Map.of("id", id),
                new MovingItemRowMapper());
        return Optional.ofNullable(template.queryForObject("select id, warehouse_from_id, warehouse_to_id, date " +
                        "from movings where id = :id",
                Map.of("id", id), ((resultSet, i) -> new Moving(
                        resultSet.getLong("id"),
                        resultSet.getLong("warehouse_from_id"),
                        resultSet.getLong("warehouse_to_id"),
                        resultSet.getDate("date"),
                        movingItems
                ))));
    }

    public Optional<Moving> save(Moving moving) {
        final var id = Optional.ofNullable(template
                .queryForObject("insert into movings (warehouse_from_id, warehouse_to_id) " +
                                "values (:warehouse_from_id, :warehouse_to_id) returning id",
                        new MapSqlParameterSource(Map.of(
                                "warehouse_from_id", moving.getWareHouseFromId(),
                                "warehouse_to_id", moving.getWareHouseToId()
                        )), ((resultSet, i) ->
                                resultSet.getLong("id")))).orElseThrow(DataAccessException::new);
        for (MovingItem item : moving.getMovingItems()) {
            template.update("insert into o2m_moving_products (moving_id, product_id, count) " +
                            "values (:moving_id, :product_id, :count)",
                    new MapSqlParameterSource(Map.of(
                            "moving_id", id,
                            "product_id", item.getProductId(),
                            "count", item.getCount()
                    )));
            template.update("update m2o_products_warehouses set count = count - :count " +
                            "where warehouse_id = :warehouse_from_id and product_id = :product_id; " +
                            "update m2o_products_warehouses set count = count + :count " +
                            "where warehouse_id = :warehouse_to_id and product_id = :product_id",
                    new MapSqlParameterSource(Map.of(
                            "count", item.getCount(),
                            "product_id", item.getProductId(),
                            "warehouse_from_id", moving.getWareHouseFromId(),
                            "warehouse_to_id", moving.getWareHouseToId()
                    )));
        }

        return getById(id);
    }

    public List<Moving> getAll() {
        final var movingList = template.query("select id, warehouse_from_id, warehouse_to_id, date from movings",
                ((resultSet, i) -> new Moving(
                        resultSet.getLong("id"),
                        resultSet.getLong("warehouse_from_id"),
                        resultSet.getLong("warehouse_to_id"),
                        resultSet.getDate("date"),
                        null
                )));
        for (Moving moving : movingList) {
            final var movingItems = template.query("select product_id, count from o2m_moving_products where moving_id = :id",
                    Map.of("id", moving.getId()),
                    ((resultSet, i) -> new MovingItem(
                            resultSet.getLong("product_id"),
                            resultSet.getInt("count")
                    )));
            moving.setMovingItems(movingItems);
        }
        return movingList;
    }
}
