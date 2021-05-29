package tech.itpark.app.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.app.model.docs.Purchase;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseByWareHouseIdResponseDto {
    private List<Purchase> purchaseList;
}
