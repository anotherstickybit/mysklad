package tech.itpark.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tech.itpark.app.dto.product.ProductByIdResponseDto;
import tech.itpark.app.dto.product.ProductSaveRequestDto;
import tech.itpark.app.dto.product.ProductsByWareHouseIdResponseDto;
import tech.itpark.app.model.Product;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductByIdResponseDto toProductByIdDto(Product product);
    ProductsByWareHouseIdResponseDto toProductsByWareHouseIdDto(Product product);
}
