package space.industock.industrial_stock.infra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonMapper {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private JsonMapper() {}

  // ===== base: JSON -> objeto =====
  public static <T> T fromJson(String json, Class<T> clazz) {
    try {
      return MAPPER.readValue(json, clazz);
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Erro ao converter JSON para " + clazz.getSimpleName(), e
      );
    }
  }

  // ===== lista: reaproveita o método base =====
  public static <T> List<T> listFromJson(String json, Class<T> clazz) {
    try {
      JsonNode node = MAPPER.readTree(json);

      if (!node.isArray()) {
        throw new IllegalArgumentException("JSON não é um array");
      }

      List<T> result = new ArrayList<>();
      for (JsonNode element : node) {
        result.add(fromJson(element.toString(), clazz));
      }

      return result;
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Erro ao converter JSON para List<" + clazz.getSimpleName() + ">", e
      );
    }
  }

}
