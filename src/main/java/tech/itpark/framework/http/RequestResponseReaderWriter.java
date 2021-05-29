package tech.itpark.framework.http;

import tech.itpark.framework.bodyconverter.BodyConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RequestResponseReaderWriter {
    private final List<BodyConverter> converters;

    public <T> T read(Class<T> clazz, HttpServletRequest request) {
        for (final var converter : converters) {
            if (!converter.canRead(request.getContentType(), clazz)) {
                continue;
            }

            return converter.read(request, clazz);
        }
        // TODO: convert to special exception
        throw new RuntimeException("no converters support given content type");
    }

    public void write(Object data, String contentType, HttpServletResponse response) {
        for (final var converter : converters) {
            if (!converter.canWrite(contentType, data.getClass())) {
                continue;
            }

            try {
                response.setContentType(contentType);
                converter.write(response, response.getWriter(), data);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: convert to special exception
                throw new RuntimeException(e);
            }
        }
        // TODO: convert to special exception
        throw new RuntimeException("no converters support given content type");
    }

    public void error(
            int statusCode,
            Object data, String contentType, HttpServletResponse response) {
        response.setStatus(statusCode);
        write(data, contentType, response);
    }
}
