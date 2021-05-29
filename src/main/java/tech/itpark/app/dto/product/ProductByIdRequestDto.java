package tech.itpark.app.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductByIdRequestDto {
    private Long id;
    private Long wareHouseId;
}
