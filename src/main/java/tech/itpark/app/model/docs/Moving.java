package tech.itpark.app.model.docs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Moving {
    private Long id;
    private Long wareHouseFromId;
    private Long wareHouseToId;
    private Date createDate;
    private List<MovingItem> movingItems;
}
