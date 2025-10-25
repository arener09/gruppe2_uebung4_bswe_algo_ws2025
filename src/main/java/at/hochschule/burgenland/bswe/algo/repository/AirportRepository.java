/**
 * ----------------------------------------------------------------------------- File:
 * AirportRepository.java Package: at.hochschule.burgenland.bswe.algo.repository Authors: Alexander
 * R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.repository;

import at.hochschule.burgenland.bswe.algo.model.Airport;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.*;
import java.util.List;
import lombok.extern.log4j.Log4j2;

/** Reads Airport data from the CSV resource and maps each line to an Airport object. */
@Log4j2
public class AirportRepository {

  private static final String AIRPORTS_FILE = "/airports.csv";

  /**
   * Loads the airports from the CSV file under /airports.csv.
   *
   * @return a list of Airport objects parsed from the CSV
   * @throws FileNotFoundException if the resource file cannot be found
   * @throws IOException if an I/O error occurs while reading
   * @throws IllegalStateException if parsing the CSV fails
   */
  public List<Airport> loadAirports() throws IOException {
    InputStream stream = getClass().getResourceAsStream(AIRPORTS_FILE);
    if (stream == null) {
      log.error("Resource not found: {}", AIRPORTS_FILE);
      throw new FileNotFoundException("Resource not found: " + AIRPORTS_FILE);
    }

    try (Reader reader = new InputStreamReader(stream)) {
      List<Airport> airports =
          new CsvToBeanBuilder<Airport>(reader)
              .withType(Airport.class)
              .withIgnoreLeadingWhiteSpace(true)
              .build()
              .parse();
      log.info("Loaded {} airports from {}", airports.size(), AIRPORTS_FILE);
      return airports;
    } catch (RuntimeException parseException) {
      log.error("Failed to parse {}: {}", AIRPORTS_FILE, parseException.getMessage());
      throw new IllegalStateException(
          "Failed to parse " + AIRPORTS_FILE + ": " + parseException.getMessage(), parseException);
    }
  }
}
