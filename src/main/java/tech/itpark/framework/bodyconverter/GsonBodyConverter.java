package tech.itpark.framework.bodyconverter;

import com.google.gson.Gson;
import tech.itpark.framework.exception.ConversionException;
import tech.itpark.framework.http.ContentTypes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.Reader;
import java.io.Writer;

@RequiredArgsConstructor
public class GsonBodyConverter implements BodyConverter{
    private final Gson gson;

    @Override
    public boolean canRead(String contentType, Class<?> clazz) {
        return ContentTypes.APPLICATION_JSON.equals(contentType);
    }

    @Override
    public boolean canWrite(String contentType, Class<?> clazz) {
        return ContentTypes.APPLICATION_JSON.equals(contentType);
    }

    @Override
    public <T> T read(HttpServletRequest request, Class<T> clazz) {
        try {
            return gson.fromJson(request.getReader(), clazz);
        } catch (Exception e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public void write(HttpServletResponse response, Writer writer, Object value) {
        try {
            writer.write(gson.toJson(value));
        } catch (Exception e) {
            throw new ConversionException(e);
        }
    }
}
