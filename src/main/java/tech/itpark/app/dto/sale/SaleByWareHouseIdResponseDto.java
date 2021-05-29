package tech.itpark.app.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.app.model.docs.Purchase;
import tech.itpark.app.model.docs.Sale;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleByWareHouseIdResponseDto {
    private List<Sale> saleList;
}
