package tech.itpark.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.itpark.app.exception.DataAccessException;
import tech.itpark.app.exception.InvalidDataRangeException;
import tech.itpark.app.model.docs.Purchase;
import tech.itpark.app.model.docs.PurchaseItem;
import tech.itpark.app.rowmapper.PurchaseItemRowMapper;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class PurchaseRepository {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public PurchaseRepository(DataSource ds) {
        this.template = new NamedParameterJdbcTemplate(ds);
    }

    public Optional<Purchase> getById(Long id) {
        final var purchaseItems = template
                .query("select product_id, price_per_one, count from o2m_purchase_products where purchase_id = :id",
                        Map.of("id", id),
                        new PurchaseItemRowMapper());
        return Optional.ofNullable(template
                .queryForObject("select id, warehouse_id, date from purchases where id = :id",
                        Map.of("id", id),
                        (resultSet, i) -> new Purchase(
                                resultSet.getLong("id"),
                                resultSet.getLong("warehouse_id"),
                                resultSet.getDate("date"),
                                purchaseItems
                        )));
    }

    public List<Purchase> getAllByWareHouseId(Long id) {
        final var purchaseList = template
                .query("select id, warehouse_id, date from purchases where warehouse_id = :id",
                        Map.of("id", id),
                        (resultSet, i) -> new Purchase(
                                resultSet.getLong("id"),
                                resultSet.getLong("warehouse_id"),
                                resultSet.getDate("date"),
                                null
                        ));
        if (purchaseList.size() == 0) {
            return Collections.emptyList();
        }
        for (Purchase purchase : purchaseList) {
            final var purchaseItems = template.query("select product_id, count, price_per_one " +
                            "from o2m_purchase_products where purchase_id = :id",
                    Map.of("id", purchase.getId()), ((resultSet, i) -> new PurchaseItem(
                            resultSet.getLong("product_id"),
                            resultSet.getInt("price_per_one"),
                            resultSet.getInt("count")
                    )));
            purchase.setPurchaseItems(purchaseItems);
        }
        return purchaseList;
    }

    public Optional<Purchase> save(Purchase purchase) {
        final var id = Optional.ofNullable(template
                .queryForObject("insert into purchases (warehouse_id) values (:wareHouseId) returning id",
                        Map.of(
                                "wareHouseId", purchase.getWareHouseId()
                        ), ((resultSet, i) -> resultSet.getLong("id")))).orElseThrow(DataAccessException::new);
        for (PurchaseItem item : purchase.getPurchaseItems()) {
            template.update("insert into o2m_purchase_products (purchase_id, product_id, count, price_per_one) " +
                            "values (:purchase_id, :product_id, :count, :price_per_one)",
                    new MapSqlParameterSource(Map.of(
                            "purchase_id", id,
                            "product_id", item.getProductId(),
                            "count", item.getCount(),
                            "price_per_one", item.getPricePerOne()
                    )));
            template.update("update m2o_products_warehouses set count = count + :count " +
                            "where product_id = :product_id and warehouse_id = :warehouse_id",
                    new MapSqlParameterSource(Map.of(
                            "product_id", item.getProductId(),
                            "warehouse_id", purchase.getWareHouseId(),
                            "count", item.getCount())));
            template.update("update products set last_purchase_price = :price_per_one where id = :product_id",
                    new MapSqlParameterSource(Map.of(
                            "product_id", item.getProductId(),
                            "price_per_one", item.getPricePerOne()
                    )));
        }

        return getById(id);
    }

    public List<Purchase> getListByTimePeriod(Date begin, Date end) {
        if (end.before(begin)) {
            throw new InvalidDataRangeException();
        }
        //todo: finish this
        return Collections.emptyList();
    }

//    @SuppressWarnings("unchecked")
//    public List<Purchase> getAllByWareHouse(Long wareHouseId) {
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        Query query = session.createQuery("from Purchase as s join fetch s.wareHouse where s.wareHouse.id = :id");
//        query.setParameter("id", wareHouseId);
//        return query.getResultList();
//    }
}
