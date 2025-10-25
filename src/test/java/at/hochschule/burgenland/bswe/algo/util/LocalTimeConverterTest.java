/**
 * ----------------------------------------------------------------------------- File:
 * LocalTimeConverterTest.java Authors: Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B,
 * Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalTimeConverterTest {

  private LocalTimeConverter converter;

  @BeforeEach
  void setUp() {
    converter = new LocalTimeConverter();
  }

  @Test
  void testConvertValidTime() {
    LocalTime result = converter.convert("10:35");
    assertEquals(LocalTime.of(10, 35), result);
  }

  @Test
  void testConvertNullValue() {
    assertNull(converter.convert(null));
  }

  @Test
  void testConvertEmptyString() {
    assertNull(converter.convert(""));
  }

  @Test
  void testConvertBlankString() {
    assertNull(converter.convert("   "));
  }

  @Test
  void testConvertInvalidFormat() {
    assertThrows(DateTimeParseException.class, () -> converter.convert("10-35"));
  }
}
