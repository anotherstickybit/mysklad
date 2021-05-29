package tech.itpark.framework.http;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerResponse {
    private final HttpServletResponse original;
    private final RequestResponseReaderWriter rw;

    public void write(Object data, String contentType) {
        rw.write(data, contentType, original);
    }

    public void error(int statusCode, Object data, String contentType) {
        rw.error(statusCode, data, contentType, original);
    }
}
