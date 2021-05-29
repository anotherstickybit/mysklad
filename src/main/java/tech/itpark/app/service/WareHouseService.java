package tech.itpark.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.itpark.app.dto.WareHouseSaveRequestDto;
import tech.itpark.app.dto.WareHouseSaveResponseDto;
import tech.itpark.app.exception.WareHouseNotFoundException;
import tech.itpark.app.mapper.WarehouseMapper;
import tech.itpark.app.model.RoleEnum;
import tech.itpark.app.model.WareHouse;
import tech.itpark.app.repository.RoleRepository;
import tech.itpark.app.repository.WareHouseRepository;
import tech.itpark.framework.exception.PermissionDeniedException;
import tech.itpark.framework.security.Auth;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WareHouseService {
    private final WareHouseRepository repository;
    private final RoleRepository roleRepository;

    public WareHouseSaveResponseDto save(Auth auth, WareHouseSaveRequestDto requestDto) {
        if (!auth.hasAnyRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()),
                roleRepository.getByName(RoleEnum.SKLAD_OPERATOR.toString()))) {
            throw new PermissionDeniedException();
        }
        WareHouse wareHouse = WarehouseMapper.INSTANCE.fromDto(requestDto);
        if (wareHouse.getId() == 0) {
            return WarehouseMapper.INSTANCE.toDto(repository.save(wareHouse));
        }
        return WarehouseMapper.INSTANCE.toDto(repository.update(wareHouse));
    }

    public List<WareHouse> getAll(Auth auth) {
        if (!auth.hasAnyRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()),
                roleRepository.getByName(RoleEnum.SKLAD_OPERATOR.toString()))) {
            throw new PermissionDeniedException();
        }
        return repository.getAll();
    }
}
