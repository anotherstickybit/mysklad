package tech.itpark.app.configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.itpark.app.controller.*;
import com.google.gson.Gson;
import tech.itpark.framework.bodyconverter.BodyConverter;
import tech.itpark.framework.bodyconverter.GsonBodyConverter;
import tech.itpark.framework.bodyconverter.MultipartBodyConverter;
import tech.itpark.framework.http.Handler;
import tech.itpark.framework.http.Methods;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.itpark.framework.security.TokenProvider;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
public class AppConfiguration {

    @Bean
    public DataSource dataSource() throws NamingException {
        final var cxt = new InitialContext();
        return (DataSource) cxt.lookup("java:/comp/env/jdbc/db");
    }

    @Bean
    public List<BodyConverter> bodyConverters() {
        return List.of(
                new GsonBodyConverter(new Gson()),
                new MultipartBodyConverter()
        );
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider();
    }

    @Bean
    public Map<String, Map<String, Handler>> routes(
            WareHouseController wareHouseController,
            UserController userController,
            ProductController productController,
            PurchaseController purchaseController,
            SaleController saleController,
            MovingController movingController,
            MediaController mediaController) {
        return Map.ofEntries(
                Map.entry("/api/warehouse/all", Map.of(
                        Methods.GET, wareHouseController::getAll
                )),
                Map.entry("/api/warehouse/new", Map.of(
                        Methods.POST, wareHouseController::save
                )),
                Map.entry("/api/register", Map.of(
                        Methods.POST, userController::register
                )),
                Map.entry("/api/auth/login", Map.of(
                        Methods.POST, userController::login
                )),
                Map.entry("/api/user/all", Map.of(
                        Methods.GET, userController::getAll
                )),
                Map.entry("/api/user/setrole", Map.of(
                        Methods.POST, userController::setRole
                )),
                Map.entry("/api/user/removerole", Map.of(
                        Methods.POST, userController::removeRole
                )),
                Map.entry("/api/user/get", Map.of(
                        Methods.GET, userController::getById
                )),
                Map.entry("/api/user/remove", Map.of(
                        Methods.POST, userController::removeById
                )),
                Map.entry("/api/user/reset", Map.of(
                        Methods.POST, userController::passwordReset
                )),
                Map.entry("/api/product", Map.of(
                        Methods.GET, productController::getById
                )),
                Map.entry("/api/product/save", Map.of(
                        Methods.POST, productController::save
                )),
                Map.entry("/api/product/warehouse", Map.of(
                        Methods.GET, productController::getAllByWareHouseId
                )),
                Map.entry("/api/purchase", Map.of(
                        Methods.POST, purchaseController::savePurchase
                )),
                Map.entry("/api/purchase/warehouse", Map.of(
                        Methods.GET, purchaseController::getAllByWareHouseId
                )),
                Map.entry("/api/sale", Map.of(
                        Methods.POST, saleController::saveSale
                )),
                Map.entry("/api/sale/warehouse", Map.of(
                        Methods.GET, saleController::getAllByWareHouseId
                )),
                Map.entry("/api/moving", Map.of(
                        Methods.POST, movingController::save
                )),
                Map.entry("/api/moving/all", Map.of(
                        Methods.GET, movingController::getAll
                )),
                Map.entry("/api/media", Map.of(
                        Methods.POST, mediaController::save
                ))
        );
    }
}
