package space.industock.industrial_stock.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import space.industock.industrial_stock.domain.ServicePicture;
import space.industock.industrial_stock.enums.PictureType;
import space.industock.industrial_stock.service.ServiceOrderService;

import java.io.InputStream;

@RestController
@RequestMapping("/pictures")
@RequiredArgsConstructor
@CrossOrigin
public class ServicePictureController {

  private final ServiceOrderService service;
  private final ServiceOrderService serviceOrderService;

  @PostMapping(value = "/{type}/{orderId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Long> uploadPicture(@PathVariable Long orderId, @RequestParam("file") MultipartFile file, @PathVariable PictureType type) throws Exception {
    ServicePicture picture = serviceOrderService.addPicture(orderId, type, file);
    return ResponseEntity.status(HttpStatus.CREATED).body(picture.getId());
  }

  @GetMapping("/{pictureId}")
  public ResponseEntity<StreamingResponseBody> getPicture(@PathVariable Long pictureId
  ) throws Exception {

    ServicePicture pic = serviceOrderService.getPicture(pictureId);
    InputStream inputStream = serviceOrderService.getPictureStream(pictureId);

    StreamingResponseBody body = outputStream -> {
      try (inputStream) {
        IOUtils.copy(inputStream, outputStream);
      }
    };

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(pic.getContentType()))
        .body(body);
  }

}
