package tech.itpark.app.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaveRequestDto {
    private Long id;
    private int vendorCode;
    private String name;
}
