package tech.itpark.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WareHouseSaveRequestDto {
    private Long id;
    private String name;
}
