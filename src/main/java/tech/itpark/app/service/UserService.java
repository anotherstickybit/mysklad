package tech.itpark.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.itpark.app.dto.PasswordResetRequestDto;
import tech.itpark.app.dto.PasswordResetResponseDto;
import tech.itpark.app.dto.user.*;
import tech.itpark.app.exception.*;
import tech.itpark.app.mapper.UserMapper;
import tech.itpark.app.model.*;
import tech.itpark.app.repository.RoleRepository;
import tech.itpark.app.repository.UserRepository;
import tech.itpark.framework.exception.PermissionDeniedException;
import tech.itpark.framework.security.Auth;
import tech.itpark.framework.security.AuthProvider;
import tech.itpark.framework.security.TokenProvider;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements AuthProvider {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public UserRegistrationResponseDto register(UserRegistrationRequestDto dto) {
        Role roleUser = roleRepository.getByName(RoleEnum.ROLE_USER.toString());
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        User user = new User();
        user.setRoles(userRoles);
        user.setUsername(dto.getLogin());
        user.setStatus(Status.ACTIVE);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setSecret(passwordEncoder.encode(dto.getSecret()));

        return new UserRegistrationResponseDto(userRepository.save(user).getId());
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        final var user = userRepository.getByLogin(requestDto.getLogin())
                .orElseThrow(UserNotFoundException::new);
        if (user.getStatus().equals(Status.DELETED)) {
            throw new UserHasBeenRemovedException();
        }
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }
        final var token = tokenProvider.createToken();
        userRepository.save(new TokenAuth(user.getId(), token));

        return new LoginResponseDto(token);
    }

    @Override
    public Auth provide(String token) {
        return userRepository.getByToken(token).map(o -> (Auth) o).orElse(Auth.anonymous());
    }

    public PasswordResetResponseDto resetPassword(PasswordResetRequestDto requestDto) {
        final var user = userRepository.getByLogin(requestDto.getLogin())
                .orElseThrow(UserNotFoundException::new);
        if(!passwordEncoder.matches(requestDto.getSecret(), user.getSecret())) {
            throw new WrongSecretException();
        }
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.updatePassword(user);
        return new PasswordResetResponseDto(user.getId());
    }

    public User findByUserName(String userName) {
        return userRepository.getByLogin(userName).orElseThrow(UserNotFoundException::new);
    }

    public List<UserModelResponseDto> getAll(Auth auth) {
        if (!auth.hasRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()))) {
            throw new PermissionDeniedException();
        }
        return userRepository.getAll();
    }

    public UserRoleResponseDto setRole(Auth auth, UserRoleRequestDto requestDto) {
        if (!auth.hasRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()))) {
            throw new PermissionDeniedException();
        }
        final var role = roleRepository.getById(requestDto.getRoleId()).orElseThrow(RoleNotFoundException::new);
        final var user = userRepository.getById(requestDto.getUserId()).orElseThrow(UserNotFoundException::new);

        if (!user.getRoles().contains(role)) {
            final var updatedUser = userRepository.setRole(requestDto.getUserId(), role.getId())
                    .orElseThrow(DataAccessException::new);
            return UserMapper.INSTANCE.toRoleDto(updatedUser);
        }

        throw new UserAlreadyHaveThisRoleException();
    }

    public UserRoleResponseDto removeRole(Auth auth, UserRoleRequestDto requestDto) {
        if (!auth.hasRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()))) {
            throw new PermissionDeniedException();
        }
        final var role = roleRepository.getById(requestDto.getRoleId()).orElseThrow(RoleNotFoundException::new);
        final var user = userRepository.getById(requestDto.getUserId()).orElseThrow(UserNotFoundException::new);

        if (user.getRoles().contains(role)) {
            final var updatedUser = userRepository.removeRole(requestDto.getUserId(), role.getId())
                    .orElseThrow(DataAccessException::new);
            return UserMapper.INSTANCE.toRoleDto(updatedUser);
        }

        throw new UserDoesntHaveThisRoleException();
    }

    public UserRemoveByIdResponseDto deleteById(Auth auth, UserRemoveByIdRequestDto requestDto) {
        if (!auth.hasRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()))) {
            throw new PermissionDeniedException();
        }
        return new UserRemoveByIdResponseDto(userRepository.deleteById(requestDto.getId()));
    }

    public UserGetByIdResponseDto getById(Auth auth, UserGetByIdRequestDto requestDto) {
        if (!auth.hasRole(roleRepository.getByName(RoleEnum.ROLE_ADMIN.toString()))) {
            throw new PermissionDeniedException();
        }
        final var user = userRepository.getById(requestDto.getId()).orElseThrow(UserNotFoundException::new);
        return UserMapper.INSTANCE.toGetByIdDto(user);
    }
}
