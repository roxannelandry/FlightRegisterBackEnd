package ca.ulaval.glo4002.flyingUnicorns.reservation.helper;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

public class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
    String dateString = extractStringFromJsonParser(jp);
    Instant instant = Instant.parse(dateString);
    LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

    return dateTime;
  }

  private String extractStringFromJsonParser(JsonParser jp) throws JsonProcessingException, IOException {
    ObjectCodec codec = jp.getCodec();
    TextNode node = (TextNode) codec.readTree(jp);

    return node.textValue();
  }

}