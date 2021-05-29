package tech.itpark.app.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.app.model.docs.PurchaseItem;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseSaveResponseDto {
    private Long id;
    private Long wareHouseId;
    private Date createDate;
    private List<PurchaseItem> purchaseItems;
}
