package tech.itpark.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.itpark.app.exception.DataAccessException;
import tech.itpark.app.model.Product;
import tech.itpark.app.model.WareHouse;
import tech.itpark.app.rowmapper.ProductRowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final NamedParameterJdbcTemplate template;
    private final WareHouseRepository wareHouseRepository;

    @Autowired
    public ProductRepository(DataSource ds, WareHouseRepository wareHouseRepository) {
        this.template = new NamedParameterJdbcTemplate(ds);
        this.wareHouseRepository = wareHouseRepository;
    }

    public Optional<Product> getById(Long id, Long wareHouseId) {
        return Optional.ofNullable(template.queryForObject(
                "select p.id, p.vendor_code, p.name, p.last_purchase_price, p.last_sale_price, " +
                        "m2opw.warehouse_id, m2opw.count from products p join m2o_products_warehouses m2opw " +
                        "on p.id = m2opw.product_id where p.id = :id and m2opw.warehouse_id = :warehouse_id",
                new MapSqlParameterSource(Map.of(
                        "id", id,
                        "warehouse_id", wareHouseId)),
                new ProductRowMapper()));
    }

    public Product save(Product product) {
        if (product.getId() == 0) {
            final var id = Optional.ofNullable(template.queryForObject(
                    "insert into products (vendor_code, name) values (:vendor_code, :name) returning id",
                    new MapSqlParameterSource(Map.of(
                            "vendor_code", product.getVendorCode(),
                            "name", product.getName()
                    )), ((resultSet, i) ->
                            resultSet.getLong("id")))).orElseThrow(DataAccessException::new);
            final var wareHouseList = wareHouseRepository.getAll();
            for (WareHouse wareHouse : wareHouseList) {
                template.update("insert into m2o_products_warehouses (warehouse_id, product_id) " +
                                "values (:warehouse_id, :product_id)",
                        new MapSqlParameterSource(Map.of(
                                "warehouse_id", wareHouse.getId(),
                                "product_id", id
                        )));

            }
            product.setId(id);
            return product;
        }
        template.update("update products set name = :name" +
                        " where id = :id",
                new MapSqlParameterSource(Map.of(
                        "id", product.getId(),
                        "name", product.getName()
                )));
        return product;
    }

    public List<Product> getByWareHouseId(Long id) {
        return template.query("select id, vendor_code, name, last_purchase_price, last_sale_price, m2opw.count as count, " +
                        "m2opw.warehouse_id as warehouse_id from products " +
                        "inner join m2o_products_warehouses m2opw on products.id = m2opw.product_id " +
                        "where m2opw.warehouse_id = :id",
                Map.of("id", id),
                (resultSet, i)-> new Product(
                        resultSet.getLong("id"),
                        resultSet.getInt("vendor_code"),
                        resultSet.getString("name"),
                        resultSet.getInt("last_purchase_price"),
                        resultSet.getInt("last_sale_price"),
                        resultSet.getInt("count"),
                        resultSet.getLong("warehouse_id")
                ));
    }
}
