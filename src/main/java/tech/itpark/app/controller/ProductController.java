package tech.itpark.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.app.dto.product.ProductByIdRequestDto;
import tech.itpark.app.dto.product.ProductSaveRequestDto;
import tech.itpark.app.dto.product.ProductsByWareHouseIdRequestDto;
import tech.itpark.app.dto.product.ProductsByWareHouseIdResponseDto;
import tech.itpark.app.service.ProductService;
import tech.itpark.framework.bodyconverter.BodyConverter;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    public void getById(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var requestDto = request.read(ProductByIdRequestDto.class);
        final var data = service.getById(auth, requestDto);
        response.write(data, ContentTypes.APPLICATION_JSON);
    }


    public void save(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var requestDto = request.read(ProductSaveRequestDto.class);
        final var responseDto = service.save(auth, requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }

    public void getAllByWareHouseId(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var requestDto = request.read(ProductsByWareHouseIdRequestDto.class);
        final var responseDto = service.getAllByWareHouseId(auth, requestDto);
        response.write(responseDto, ContentTypes.APPLICATION_JSON);
    }
}
