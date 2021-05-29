package tech.itpark.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private long id;
    private int vendorCode;
    private String name;
    private Integer lastPurchasePrice;
    private Integer lastSalePrice;
    private Integer count;
    private long wareHouseId;
}
