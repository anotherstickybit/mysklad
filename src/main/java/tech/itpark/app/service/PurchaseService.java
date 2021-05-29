package tech.itpark.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.itpark.app.dto.purchase.PurchaseByWareHouseIdRequestDto;
import tech.itpark.app.dto.purchase.PurchaseByWareHouseIdResponseDto;
import tech.itpark.app.dto.purchase.PurchaseSaveRequestDto;
import tech.itpark.app.dto.purchase.PurchaseSaveResponseDto;
import tech.itpark.app.exception.DataAccessException;
import tech.itpark.app.exception.WareHouseNotFoundException;
import tech.itpark.app.model.RoleEnum;
import tech.itpark.app.model.docs.Purchase;
import tech.itpark.app.repository.ProductRepository;
import tech.itpark.app.repository.PurchaseRepository;
import tech.itpark.app.repository.RoleRepository;
import tech.itpark.app.repository.WareHouseRepository;
import tech.itpark.framework.exception.PermissionDeniedException;
import tech.itpark.framework.security.Auth;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final WareHouseRepository wareHouseRepository;
    private final RoleRepository roleRepository;

    public PurchaseSaveResponseDto save(Auth auth, PurchaseSaveRequestDto dto) {
        if (!auth.hasAnyRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()),
                roleRepository.getByName(RoleEnum.SKLAD_OPERATOR.toString()))) {
            throw new PermissionDeniedException();
        }
        wareHouseRepository.getById(dto.getWareHouseId())
                .orElseThrow(WareHouseNotFoundException::new);
        Purchase purchase = new Purchase();
        purchase.setId(0L);
        purchase.setWareHouseId(dto.getWareHouseId());
        purchase.setPurchaseItems(dto.getPurchaseItems());

        final var saved = purchaseRepository.save(purchase).orElseThrow(DataAccessException::new);
        return new PurchaseSaveResponseDto(
                saved.getId(),
                saved.getWareHouseId(),
                saved.getCreateDate(),
                saved.getPurchaseItems()
        );
    }

    public PurchaseByWareHouseIdResponseDto getAllForWareHouse(Auth auth, PurchaseByWareHouseIdRequestDto dto) {
        if (!auth.hasAnyRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()),
                roleRepository.getByName(RoleEnum.SKLAD_OPERATOR.toString()))) {
            throw new PermissionDeniedException();
        }
        wareHouseRepository.getById(dto.getId()).orElseThrow(WareHouseNotFoundException::new);
        return new PurchaseByWareHouseIdResponseDto(purchaseRepository.getAllByWareHouseId(dto.getId()));
    }
}
