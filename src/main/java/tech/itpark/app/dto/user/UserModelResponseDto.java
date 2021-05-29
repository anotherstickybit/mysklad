package tech.itpark.app.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.app.model.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModelResponseDto {
    private Long id;
    private String username;
    private Status status;
}
