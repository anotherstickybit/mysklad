package tech.itpark.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.app.dto.moving.MovingSaveRequestDto;
import tech.itpark.app.model.docs.Moving;
import tech.itpark.app.service.MovingService;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovingController {
    private final MovingService movingService;

    public void save(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var dto = request.read(MovingSaveRequestDto.class);
        final var data = movingService.save(auth, dto);
        response.write(data, ContentTypes.APPLICATION_JSON);
    }

    public void getAll(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var responseDto = movingService.getAll(auth);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }
}
