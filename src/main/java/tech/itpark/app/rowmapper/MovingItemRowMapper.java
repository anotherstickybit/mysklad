package tech.itpark.app.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.app.model.docs.MovingItem;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovingItemRowMapper implements RowMapper<MovingItem> {
    @Override
    public MovingItem mapRow(ResultSet resultSet, int i) throws SQLException {
        return new MovingItem(
                resultSet.getLong("product_id"),
                resultSet.getInt("count")
        );
    }
}
