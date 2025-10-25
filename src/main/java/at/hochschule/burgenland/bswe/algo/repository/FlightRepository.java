/**
 * ----------------------------------------------------------------------------- File:
 * FlightRepository.java Package: at.hochschule.burgenland.bswe.algo.repository Authors: Alexander
 * R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.repository;

import at.hochschule.burgenland.bswe.algo.model.Flight;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import lombok.extern.log4j.Log4j2;

/** Reads flight records from the flights.csv resource and maps them to Flight objects. */
@Log4j2
public class FlightRepository {

  private static final String FLIGHTS_FILE = "/flights.csv";

  /**
   * Loads the flights from the CSV file under /flights.csv.
   *
   * @return a list of Flight objects parsed from the CSV
   * @throws FileNotFoundException if the resource file cannot be found
   * @throws IOException if an I/O error occurs while reading
   * @throws IllegalStateException if parsing the CSV fails
   */
  public List<Flight> loadFlights() throws IOException {
    InputStream stream = getClass().getResourceAsStream(FLIGHTS_FILE);
    if (stream == null) {
      log.error("Resource not found: {}", FLIGHTS_FILE);
      throw new FileNotFoundException("Resource not found: " + FLIGHTS_FILE);
    }

    try (Reader reader = new InputStreamReader(stream)) {
      List<Flight> flights =
          new CsvToBeanBuilder<Flight>(reader)
              .withType(Flight.class)
              .withIgnoreLeadingWhiteSpace(true)
              .build()
              .parse();
      log.info("Loaded {} flights from {}", flights.size(), FLIGHTS_FILE);
      return flights;
    } catch (RuntimeException parseException) {
      log.error("Failed to parse {}: {}", FLIGHTS_FILE, parseException.getMessage());
      throw new IllegalStateException(
          "Failed to parse " + FLIGHTS_FILE + ": " + parseException.getMessage(), parseException);
    }
  }
}
