package tech.itpark.app.model.docs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.app.model.WareHouse;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale implements Serializable {
    private Long id;
    private Long wareHouseId;
    private Date createDate;
    private List<SaleItem> saleItems;
}
