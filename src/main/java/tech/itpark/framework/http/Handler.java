package tech.itpark.framework.http;

@FunctionalInterface
public interface Handler {
    void handle(ServerRequest request, ServerResponse response) throws Exception;
}
