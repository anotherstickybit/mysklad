package tech.itpark.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tech.itpark.app.dto.WareHouseSaveRequestDto;
import tech.itpark.app.dto.WareHouseSaveResponseDto;
import tech.itpark.app.model.WareHouse;

@Mapper
public interface WarehouseMapper {
    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    WareHouse fromDto(WareHouseSaveRequestDto dto);
    WareHouseSaveResponseDto toDto(WareHouse wareHouse);
}
