package tech.itpark.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.app.dto.PasswordResetRequestDto;
import tech.itpark.app.dto.PasswordResetResponseDto;
import tech.itpark.app.dto.user.*;
import tech.itpark.app.service.UserService;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    public void register(ServerRequest request, ServerResponse response) throws IOException {
        final var requestDto = request.read(UserRegistrationRequestDto.class);
        final var responseDto = service.register(requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }

    public void login(ServerRequest request, ServerResponse response) throws IOException {
        final var requestDto = request.read(LoginRequestDto.class);
        final var responseDto = service.login(requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }

    public void getAll(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var responseDto = service.getAll(auth);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }

    public void setRole(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var requestDto = request.read(UserRoleRequestDto.class);
        final var responseDto = service.setRole(auth, requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }

    public void removeRole(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var requestDto = request.read(UserRoleRequestDto.class);
        final var responseDto = service.removeRole(auth, requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }

    public void removeById(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var requestDto = request.read(UserRemoveByIdRequestDto.class);
        final var responseDto = service.deleteById(auth, requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }

    public void getById(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var requestDto = request.read(UserGetByIdRequestDto.class);
        final var responseDto = service.getById(auth, requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }

    public void passwordReset(ServerRequest request, ServerResponse response) throws IOException {
        final var requestDto = request.read(PasswordResetRequestDto.class);
        final var responseDto = service.resetPassword(requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }
}
