package tech.itpark.app.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.app.model.docs.SaleItem;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleItemRowMapper implements RowMapper<SaleItem> {
    @Override
    public SaleItem mapRow(ResultSet resultSet, int i) throws SQLException {
        return new SaleItem(
                resultSet.getLong("product_id"),
                resultSet.getInt("price_per_one"),
                resultSet.getInt("count")
        );
    }
}
