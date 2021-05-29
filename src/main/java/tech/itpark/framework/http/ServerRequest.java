package tech.itpark.framework.http;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import tech.itpark.framework.security.Auth;
import tech.itpark.framework.security.HttpServletRequestAuth;

@RequiredArgsConstructor
public class ServerRequest {
    private final HttpServletRequest original;
    private final RequestResponseReaderWriter rw;

    public <T> T read(Class<T> clazz) {
        return rw.read(clazz, original);
    }

    public Auth auth() {
        return HttpServletRequestAuth.auth(original);
    }
}
