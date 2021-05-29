package tech.itpark.app.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.app.model.docs.SaleItem;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleSaveRequestDto {
    private Long id;
    private Long wareHouseId;
    private List<SaleItem> saleItems;
}
