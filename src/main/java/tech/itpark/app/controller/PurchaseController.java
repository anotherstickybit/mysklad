package tech.itpark.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import tech.itpark.app.dto.purchase.PurchaseByWareHouseIdRequestDto;
import tech.itpark.app.dto.purchase.PurchaseSaveRequestDto;
import tech.itpark.app.service.PurchaseService;
import tech.itpark.framework.http.ContentTypes;
import tech.itpark.framework.http.ServerRequest;
import tech.itpark.framework.http.ServerResponse;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    public void savePurchase(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var dto = request.read(PurchaseSaveRequestDto.class);
        final var data = purchaseService.save(auth, dto);
        response.write(data, ContentTypes.APPLICATION_JSON);
    }

    public void getAllByWareHouseId(ServerRequest request, ServerResponse response) throws IOException {
        final var auth = request.auth();
        final var dto = request.read(PurchaseByWareHouseIdRequestDto.class);
        final var data = purchaseService.getAllForWareHouse(auth, dto);
        response.write(data, ContentTypes.APPLICATION_JSON);
    }
}
