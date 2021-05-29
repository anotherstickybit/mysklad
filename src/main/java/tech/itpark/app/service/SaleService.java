package tech.itpark.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.itpark.app.dto.sale.SaleByWareHouseIdRequestDto;
import tech.itpark.app.dto.sale.SaleByWareHouseIdResponseDto;
import tech.itpark.app.dto.sale.SaleSaveRequestDto;
import tech.itpark.app.dto.sale.SaleSaveResponseDto;
import tech.itpark.app.exception.InvalidSaleRequestException;
import tech.itpark.app.exception.ProductNotFoundException;
import tech.itpark.app.exception.WareHouseNotFoundException;
import tech.itpark.app.model.RoleEnum;
import tech.itpark.app.model.WareHouse;
import tech.itpark.app.model.docs.Sale;
import tech.itpark.app.repository.ProductRepository;
import tech.itpark.app.repository.RoleRepository;
import tech.itpark.app.repository.SaleRepository;
import tech.itpark.app.repository.WareHouseRepository;
import tech.itpark.framework.exception.PermissionDeniedException;
import tech.itpark.framework.security.Auth;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final WareHouseRepository wareHouseRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;

    public SaleSaveResponseDto save(Auth auth, SaleSaveRequestDto dto) {
        if (!auth.hasAnyRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()),
                roleRepository.getByName(RoleEnum.SKLAD_OPERATOR.toString()))) {
            throw new PermissionDeniedException();
        }
        final var wareHouse = wareHouseRepository.getById(dto.getWareHouseId())
                .orElseThrow(WareHouseNotFoundException::new);
        dto.getSaleItems().forEach(item -> {
            final var product = productRepository.getById(item.getProductId(), wareHouse.getId())
                    .orElseThrow(ProductNotFoundException::new);
            if (product.getCount() < item.getCount()) {
                throw new InvalidSaleRequestException("Not enough products with id: " + product.getId() +
                        " on warehouse " + product.getWareHouseId());
            }
        });
        Sale sale = new Sale();
        sale.setId(0L);
        sale.setWareHouseId(dto.getWareHouseId());
        sale.setSaleItems(dto.getSaleItems());

        final var saved = saleRepository.save(sale);
        return new SaleSaveResponseDto(
                saved.getId(),
                saved.getWareHouseId(),
                saved.getCreateDate(),
                saved.getSaleItems()
        );
    }

    public SaleByWareHouseIdResponseDto getAllForWarehouse(Auth auth, SaleByWareHouseIdRequestDto dto) {
        if (!auth.hasAnyRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()),
                roleRepository.getByName(RoleEnum.SKLAD_OPERATOR.toString()))) {
            throw new PermissionDeniedException();
        }
        wareHouseRepository.getById(dto.getId())
                .orElseThrow(WareHouseNotFoundException::new);
        return new SaleByWareHouseIdResponseDto(saleRepository.getAllByWareHouseId(dto.getId()));
    }
}
