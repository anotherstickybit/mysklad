package tech.itpark.app.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.app.model.WareHouse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WareHouseMapper implements RowMapper<WareHouse> {

    @Override
    public WareHouse mapRow(ResultSet resultSet, int i) throws SQLException {
        return new WareHouse(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }
}
