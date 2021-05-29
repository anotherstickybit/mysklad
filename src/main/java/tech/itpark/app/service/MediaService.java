package tech.itpark.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import tech.itpark.app.dto.MediaSaveResponseDto;
import tech.itpark.app.dto.moving.MovingSaveRequestDto;
import tech.itpark.app.dto.purchase.PurchaseSaveRequestDto;
import tech.itpark.app.dto.sale.SaleSaveRequestDto;
import tech.itpark.app.exception.FileProcessingException;
import tech.itpark.app.model.RoleEnum;
import tech.itpark.app.model.docs.MovingItem;
import tech.itpark.app.model.docs.PurchaseItem;
import tech.itpark.app.model.docs.SaleItem;
import tech.itpark.app.repository.RoleRepository;
import tech.itpark.framework.exception.PermissionDeniedException;
import tech.itpark.framework.security.Auth;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaService implements InitializingBean {
    private final PurchaseService purchaseService;
    private final SaleService saleService;
    private final MovingService movingService;
    private final RoleRepository roleRepository;
    private final Path path = Path.of("/tmp/mysklad");

    public MediaSaveResponseDto save(Auth auth, Collection<Part> dto) {
        if (dto.size() == 1) {
            final var filePart = dto.iterator().next();
            return processFile(auth, filePart);
        }

        return null;
    }

    private MediaSaveResponseDto processFile(Auth auth, Part filePart) {
        final var fileName = filePart.getSubmittedFileName();
        final var filePath = path + "/" + fileName + "_" + System.currentTimeMillis();

        if (fileName.equals("purchase.csv")) {
            try {
                filePart.write(filePath);
                CSVReader csvReader = new CSVReader(new FileReader(filePath));
                final var strings = csvReader.readAll();

                ObjectMapper objectMapper = new ObjectMapper();
                List<PurchaseItem> purchaseItems = objectMapper.readValue(strings.get(1)[2], new TypeReference<List<PurchaseItem>>() {
                });
                final var purchase = new PurchaseSaveRequestDto();
                purchase.setId(Long.parseLong(strings.get(1)[0]));
                purchase.setWareHouseId(Long.parseLong(strings.get(1)[1]));
                purchase.setPurchaseItems(purchaseItems);

                final var saved = purchaseService.save(auth, purchase);

                return new MediaSaveResponseDto(filePath);
            } catch (IOException | CsvException e) {
                throw new FileProcessingException();
            }
        }
        if (fileName.equals("sale.csv")) {
            try {
                filePart.write(filePath);
                CSVReader csvReader = new CSVReader(new FileReader(filePath));
                final var strings = csvReader.readAll();

                ObjectMapper objectMapper = new ObjectMapper();
                List<SaleItem> saleItems = objectMapper.readValue(strings.get(1)[2], new TypeReference<List<SaleItem>>() {
                });
                final var sale = new SaleSaveRequestDto();
                sale.setId(Long.parseLong(strings.get(1)[0]));
                sale.setWareHouseId(Long.parseLong(strings.get(1)[1]));
                sale.setSaleItems(saleItems);

                final var saved = saleService.save(auth, sale);

                return new MediaSaveResponseDto(filePath);
            } catch (IOException | CsvException e) {
                throw new FileProcessingException();
            }
        }
        if (fileName.equals("moving.csv")) {
            try {
                filePart.write(filePath);
                CSVReader csvReader = new CSVReader(new FileReader(filePath));
                final var strings = csvReader.readAll();

                ObjectMapper objectMapper = new ObjectMapper();
                List<MovingItem> movingItems = objectMapper.readValue(strings.get(1)[3], new TypeReference<List<MovingItem>>() {
                });
                final var moving = new MovingSaveRequestDto();
                moving.setId(Long.parseLong(strings.get(1)[0]));
                moving.setWareHouseFromId(Long.parseLong(strings.get(1)[1]));
                moving.setWareHouseToId(Long.parseLong(strings.get(1)[2]));
                moving.setMovingItems(movingItems);

                final var saved = movingService.save(auth, moving);

                return new MediaSaveResponseDto(filePath);
            } catch (IOException | CsvException e) {
                throw new FileProcessingException();
            }
        }
        return null;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Files.createDirectories(path);
    }
}
