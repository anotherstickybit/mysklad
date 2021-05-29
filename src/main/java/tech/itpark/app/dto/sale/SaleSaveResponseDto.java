package tech.itpark.app.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.app.model.docs.SaleItem;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleSaveResponseDto {
    private Long id;
    private Long wareHouseId;
    private Date createDate;
    private List<SaleItem> saleItems;
}
