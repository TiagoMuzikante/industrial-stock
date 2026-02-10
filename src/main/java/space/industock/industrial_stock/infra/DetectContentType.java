package space.industock.industrial_stock.infra;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
public class DetectContentType {

  private static final Tika TIKA = new Tika();

  public static String detectByFile(Path file) {
    try {
      return TIKA.detect(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String detectByName(String fileName) {
    String type = TIKA.detect(fileName);
    return type != null ? type : "application/octet-stream";
  }


}
