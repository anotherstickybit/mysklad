package tech.itpark.framework.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import tech.itpark.framework.http.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class FrontController extends HttpServlet {
    private Map<String, Map<String, Handler>> routes;
    private RequestResponseReaderWriter rw;
    private final Handler notFoundHandler = (request, response) -> response
            .error(404, "Not found", ContentTypes.TEXT_PLAIN);

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final var path = request.getServletPath();
        final var method = request.getMethod();

        try {
            Optional.ofNullable(routes.get(path))
                    .map(o -> o.get(method))
                    .orElse(notFoundHandler)
                    .handle(new ServerRequest(request, rw), new ServerResponse(response, rw));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            final var context = (ApplicationContext) getServletContext().getAttribute("CONTEXT");
            rw = context.getBean(RequestResponseReaderWriter.class);
            routes = (Map<String, Map<String, Handler>>) context.getBean("routes");
        } catch (Exception e) {
            throw new UnavailableException(e.getMessage());
        }
    }
}
