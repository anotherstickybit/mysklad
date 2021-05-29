package tech.itpark.app.model.docs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    private Long id;
    private Long wareHouseId;
    private Date createDate;
    private List<PurchaseItem> purchaseItems;
}
