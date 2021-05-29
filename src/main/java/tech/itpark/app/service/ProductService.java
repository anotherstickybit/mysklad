package tech.itpark.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.itpark.app.dto.product.*;
import tech.itpark.app.exception.ProductNotFoundException;
import tech.itpark.app.mapper.ProductMapper;
import tech.itpark.app.model.Product;
import tech.itpark.app.model.RoleEnum;
import tech.itpark.app.repository.ProductRepository;
import tech.itpark.app.repository.RoleRepository;
import tech.itpark.framework.exception.PermissionDeniedException;
import tech.itpark.framework.security.Auth;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;

    public ProductByIdResponseDto getById(Auth auth, ProductByIdRequestDto requestDto) {
        if (!auth.hasAnyRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()),
                roleRepository.getByName(RoleEnum.SKLAD_OPERATOR.toString()))) {
            throw new PermissionDeniedException();
        }
        final var product = productRepository.getById(requestDto.getId(), requestDto.getWareHouseId())
                .orElseThrow(ProductNotFoundException::new);
        return ProductMapper.INSTANCE.toProductByIdDto(product);
    }

    public ProductSaveResponseDto save(Auth auth, ProductSaveRequestDto requestDto) {
        if (!auth.hasAnyRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()),
                roleRepository.getByName(RoleEnum.SKLAD_OPERATOR.toString()))) {
            throw new PermissionDeniedException();
        }
        Product product = new Product();
        product.setId(requestDto.getId());
        product.setName(requestDto.getName());
        product.setVendorCode(requestDto.getVendorCode());

        final var saved = productRepository.save(product);
        return new ProductSaveResponseDto(saved.getId());
    }

    public List<ProductsByWareHouseIdResponseDto> getAllByWareHouseId(Auth auth, ProductsByWareHouseIdRequestDto requestDto) {
        if (!auth.hasAnyRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()),
                roleRepository.getByName(RoleEnum.SKLAD_OPERATOR.toString()))) {
            throw new PermissionDeniedException();
        }
        final var productList = productRepository.getByWareHouseId(requestDto.getId());
        return productList.stream()
                .map(ProductMapper.INSTANCE::toProductsByWareHouseIdDto)
                .collect(Collectors.toList());
    }
}
