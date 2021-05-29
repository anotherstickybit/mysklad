package repository;

import tech.itpark.app.repository.WareHouseRepository;
import tech.itpark.app.model.WareHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import(WareHouseRepository.class)
class WareHouseRepositoryTest {
    @Autowired
    private WareHouseRepository repository;

//    @org.junit.jupiter.api.Test
//    void getById() {
//        final var wareHouse = repository.getById(1L);
//        final var test = new WareHouse();
//        test.setId(1);
//        test.setName("SKLAD1");
//        assertEquals(test, wareHouse);
//    }

    @org.junit.jupiter.api.Test
    void getByName() {

    }
}