package tech.itpark.app.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.app.model.docs.PurchaseItem;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseItemRowMapper implements RowMapper<PurchaseItem> {
    @Override
    public PurchaseItem mapRow(ResultSet resultSet, int i) throws SQLException {
        return new PurchaseItem(
                resultSet.getLong("product_id"),
                resultSet.getInt("price_per_one"),
                resultSet.getInt("count")
        );
    }
}
