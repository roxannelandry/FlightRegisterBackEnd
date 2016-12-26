package ca.ulaval.glo4002.flyingUnicorns.reservation.helper;

import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateTimeJsonSerializerTest {
  
  private Instant instant;
  
  private LocalDateTimeJsonSerializer dateTimeSerializer;
  
  private LocalDateTime dateTime;

  @Mock
  private JsonGenerator jsonGenerator;

  @Mock
  private SerializerProvider serializerProvider;

  @Before
  public void setup() {
    dateTimeSerializer = new LocalDateTimeJsonSerializer();
    dateTime = LocalDateTime.now();
    instant = dateTime.toInstant(ZoneOffset.UTC);
  }

  @Test
  public void serializeInstant_jsonGeneratorWriteStringIsCalled() throws JsonProcessingException, IOException {
    dateTimeSerializer.serialize(dateTime, jsonGenerator, serializerProvider);

    verify(jsonGenerator).writeString(DateTimeFormatter.ISO_INSTANT.format(instant));
  }

}