package tech.itpark.app.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductByIdResponseDto {
    private long id;
    private int vendorCode;
    private String name;
    private Integer lastPurchasePrice;
    private Integer lastSalePrice;
    private Integer count;
    private long wareHouseId;
}
