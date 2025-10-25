/**
 * ----------------------------------------------------------------------------- File:
 * LocalTimeConverter.java Package: at.hochschule.burgenland.bswe.algo.util Authors: Alexander R.
 * Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.util;

import com.opencsv.bean.AbstractBeanField;
import java.time.LocalTime;

/**
 * Converter implementation for parsing {@link LocalTime} values from CSV input. The OpenCSV
 * framework uses this class when a field in a model class is annotated with
 * {@code @CsvCustomBindByName(converter = LocalTimeConverter.class)}. It expects the time to be
 * provided in the 24-hour format {@code HH:mm}. Example:
 *
 * <pre>
 * 10:35 -> LocalTime.of(10, 35)
 * </pre>
 */
public class LocalTimeConverter extends AbstractBeanField<LocalTime, String> {

  /**
   * Converts a CSV string value (formatted as HH:mm) into a {@link LocalTime} object.
   *
   * @param value the CSV field value as a string
   * @return the corresponding {@link LocalTime} instance, or {@code null} if empty
   */
  @Override
  protected LocalTime convert(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }
    return LocalTime.parse(value);
  }
}
