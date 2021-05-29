package tech.itpark.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.itpark.app.dto.user.UserGetByIdResponseDto;
import tech.itpark.app.dto.user.UserRoleResponseDto;
import tech.itpark.app.model.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserRoleResponseDto toRoleDto(User user);
    UserGetByIdResponseDto toGetByIdDto(User user);
}
