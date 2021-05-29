package tech.itpark.app.dto.moving;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.app.model.docs.MovingItem;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovingSaveRequestDto {
    private Long id;
    private Long wareHouseFromId;
    private Long wareHouseToId;
    private List<MovingItem> movingItems;
}
