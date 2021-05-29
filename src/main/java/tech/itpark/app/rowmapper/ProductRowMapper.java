package tech.itpark.app.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.app.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Product(
                resultSet.getLong("id"),
                resultSet.getInt("vendor_code"),
                resultSet.getString("name"),
                resultSet.getInt("last_purchase_price"),
                resultSet.getInt("last_sale_price"),
                resultSet.getInt("count"),
                resultSet.getLong("warehouse_id")
        );
    }
}
