package ca.ulaval.glo4002.flyingUnicorns.reservation.helper;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.TextNode;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateTimeJsonDeserializerTest {

  private static final String A_DATE = "2016-10-30T00:00:00Z";
  
  private static final int A_DATE_YEAR = 2016;
  private static final int A_DATE_MONTH = 10;
  private static final int A_DATE_DAY = 30;
  private static final int A_DATE_HOUR = 0;
  private static final int A_DATE_MINUTE = 0;
  private static final int A_DATE_SECOND = 0;

  private LocalDateTime dateTime;
  
  private LocalDateTimeJsonDeserializer dateTimeDeserializer;

  @Mock
  private TextNode textNode;

  @Mock
  private ObjectCodec codec;

  @Mock
  private JsonParser jsonParser;

  @Mock
  private DeserializationContext deserializationContext;

  @Before
  public void setup() throws JsonProcessingException, IOException {
    willReturn(codec).given(jsonParser).getCodec();
    willReturn(textNode).given(codec).readTree(jsonParser);
    willReturn(A_DATE).given(textNode).textValue();

    dateTimeDeserializer = new LocalDateTimeJsonDeserializer();
  }

  @Test
  public void localDateTime_deserialize_returnSameDate() throws JsonProcessingException, IOException {
    dateTime = dateTimeDeserializer.deserialize(jsonParser, deserializationContext);

    assertTrue(dateTimeIsSame(dateTime));
  }

  private boolean dateTimeIsSame(LocalDateTime dateTime) {
    boolean sameDateTime = dateTime.getYear() == A_DATE_YEAR && dateTime.getMonthValue() == A_DATE_MONTH && dateTime.getDayOfMonth() == A_DATE_DAY
        && dateTime.getHour() == A_DATE_HOUR && dateTime.getMinute() == A_DATE_MINUTE && dateTime.getSecond() == A_DATE_SECOND;
    
    return sameDateTime;
  }
  
}