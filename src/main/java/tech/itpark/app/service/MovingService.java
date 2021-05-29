package tech.itpark.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.itpark.app.dto.moving.MovingSaveRequestDto;
import tech.itpark.app.dto.moving.MovingSaveResponseDto;
import tech.itpark.app.exception.DataAccessException;
import tech.itpark.app.exception.InvalidSaleRequestException;
import tech.itpark.app.exception.ProductNotFoundException;
import tech.itpark.app.exception.WareHouseNotFoundException;
import tech.itpark.app.model.RoleEnum;
import tech.itpark.app.model.WareHouse;
import tech.itpark.app.model.docs.Moving;
import tech.itpark.app.repository.MovingRepository;
import tech.itpark.app.repository.ProductRepository;
import tech.itpark.app.repository.RoleRepository;
import tech.itpark.app.repository.WareHouseRepository;
import tech.itpark.framework.exception.PermissionDeniedException;
import tech.itpark.framework.security.Auth;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovingService {
    private final MovingRepository movingRepository;
    private final WareHouseRepository wareHouseRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;

    public MovingSaveResponseDto save(Auth auth, MovingSaveRequestDto dto) {
        if (!auth.hasAnyRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()),
                roleRepository.getByName(RoleEnum.SKLAD_OPERATOR.toString()))) {
            throw new PermissionDeniedException();
        }
        wareHouseRepository.getById(dto.getWareHouseToId()).orElseThrow(WareHouseNotFoundException::new);
        final var wareHouse = wareHouseRepository.getById(dto.getWareHouseFromId())
                .orElseThrow(WareHouseNotFoundException::new);

        dto.getMovingItems().forEach(item -> {
            final var product = productRepository.getById(item.getProductId(), wareHouse.getId())
                    .orElseThrow(ProductNotFoundException::new);
            if (product.getCount() < item.getCount()) {
                throw new InvalidSaleRequestException("Not enough products with id: " + product.getId() +
                        " on warehouse " + product.getWareHouseId());
            }
        });

        Moving moving = new Moving();
        moving.setWareHouseFromId(dto.getWareHouseFromId());
        moving.setWareHouseToId(dto.getWareHouseToId());
        moving.setMovingItems(dto.getMovingItems());

        final var saved = movingRepository.save(moving).orElseThrow(DataAccessException::new);
        return new MovingSaveResponseDto(
                saved.getId(),
                saved.getWareHouseFromId(),
                saved.getWareHouseToId(),
                saved.getCreateDate(),
                saved.getMovingItems()
        );
    }

    public List<Moving> getAll(Auth auth) {
        if (!auth.hasAnyRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()),
                roleRepository.getByName(RoleEnum.SKLAD_OPERATOR.toString()))) {
            throw new PermissionDeniedException();
        }
        return movingRepository.getAll();
    }

}
