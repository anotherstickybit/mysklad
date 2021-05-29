package tech.itpark.app.model.docs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItem {
    private Long productId;
    private Integer pricePerOne;
    private Integer count;
}
