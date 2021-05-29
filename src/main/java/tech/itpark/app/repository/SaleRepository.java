package tech.itpark.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.itpark.app.exception.DataAccessException;
import tech.itpark.app.exception.InvalidDataRangeException;
import tech.itpark.app.model.docs.Sale;
import tech.itpark.app.model.docs.SaleItem;
import tech.itpark.app.rowmapper.SaleItemRowMapper;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class SaleRepository {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public SaleRepository(DataSource ds) {
        this.template = new NamedParameterJdbcTemplate(ds);
    }

    public Optional<Sale> getById(Long id) {
        final var saleItems = template
                .query("select product_id, count, price_per_one from o2m_sale_products where sale_id = :id",
                        Map.of("id", id),
                        new SaleItemRowMapper());
        return Optional.ofNullable(template.queryForObject("select id, warehouse_id, date from sales where id = :id",
                Map.of("id", id), ((resultSet, i) -> new Sale(
                        resultSet.getLong("id"),
                        resultSet.getLong("warehouse_id"),
                        resultSet.getDate("date"),
                        saleItems
                ))));
    }

    public Sale save(Sale sale) {
        final var id = Optional.ofNullable(template
                .queryForObject("insert into sales (warehouse_id) values (:wareHouseId) returning id",
                        Map.of("wareHouseId", sale.getWareHouseId()),
                        ((resultSet, i) -> resultSet.getLong("id")))).orElseThrow(DataAccessException::new);
        for (SaleItem item : sale.getSaleItems()) {
            template.update("insert into o2m_sale_products (sale_id, product_id, count, price_per_one) " +
                            "values (:sale_id, :product_id, :count, :price_per_one)",
                    new MapSqlParameterSource(Map.of(
                            "sale_id", id,
                            "product_id", item.getProductId(),
                            "count", item.getCount(),
                            "price_per_one", item.getPricePerOne()
                    )));
            template.update("update m2o_products_warehouses set count = count - :count " +
                            "where product_id = :product_id and warehouse_id = :warehouse_id",
                    new MapSqlParameterSource(Map.of(
                            "product_id", item.getProductId(),
                            "warehouse_id", sale.getWareHouseId(),
                            "count", item.getCount())));
            template.update("update products set last_sale_price = :price_per_one where id = :product_id",
                    new MapSqlParameterSource(Map.of(
                            "product_id", item.getProductId(),
                            "price_per_one", item.getPricePerOne()
                    )));
        }
        sale.setId(id);
        return sale;
    }

    public List<Sale> getAllByWareHouseId(Long id) {
        final var saleList = template.query("select id, warehouse_id, date from sales where id = :id",
                Map.of("id", id), ((resultSet, i) -> new Sale(
                        resultSet.getLong("id"),
                        resultSet.getLong("warehouse_id"),
                        resultSet.getDate("date"),
                        null
                )));
        if (saleList.size() == 0) {
            return Collections.emptyList();
        }
        for (Sale sale : saleList) {
            final var saleItems = template.query("select product_id, price_per_one, count from o2m_sale_products",
                    Map.of("id", sale.getId()), ((resultSet, i) -> new SaleItem(
                            resultSet.getLong("product_id"),
                            resultSet.getInt("price_per_one"),
                            resultSet.getInt("count")
                    )));
            sale.setSaleItems(saleItems);
        }
        return saleList;
    }

    public List<Sale> getListByTimePeriod(Date begin, Date end) {
        if (end.before(begin)) {
            throw new InvalidDataRangeException();
        }
        //todo: finish this
        return Collections.emptyList();
    }

//    @SuppressWarnings("unchecked")
//    public List<Sale> getAllByWareHouse(Long wareHouseId) {
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        Query query = session.createQuery("from Sale as s join fetch s.wareHouse where s.wareHouse.id = :id");
//        query.setParameter("id", wareHouseId);
//        return query.getResultList();
//    }
}
