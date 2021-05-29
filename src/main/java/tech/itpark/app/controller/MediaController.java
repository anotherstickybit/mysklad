package tech.itpark.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.app.service.MediaService;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;

import java.io.IOException;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    public void save(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var requestDto = request.read(Collection.class);
        final var responseDto = mediaService.save(auth, requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }
}
