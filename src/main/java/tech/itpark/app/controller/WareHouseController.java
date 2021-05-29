package tech.itpark.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import tech.itpark.app.dto.WareHouseSaveRequestDto;
import tech.itpark.app.service.WareHouseService;
import tech.itpark.framework.bodyconverter.BodyConverter;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WareHouseController {
    private final WareHouseService service;

    public void getAll(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var data = service.getAll(auth);
        response.write(data, ContentTypes.APPLICATION_JSON);
    }


    public void save(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var requestDto = request.read(WareHouseSaveRequestDto.class);
        final var responseDto = service.save(auth, requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }
}
