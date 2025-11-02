/**
 * ----------------------------------------------------------------------------- File:
 * RouteRepository.java Package: at.hochschule.burgenland.bswe.algo.repository Authors: Alexander R.
 * Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.repository;

import at.hochschule.burgenland.bswe.algo.model.Route;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import lombok.extern.log4j.Log4j2;

/** Reads route records from routes.csv and maps them to Route objects. */
@Log4j2
public class RouteRepository {

  private static final String ROUTES_FILE = "/routes.csv";

  /**
   * Loads routes from the CSV file if it exists.
   *
   * @return list of Route objects, or an empty list if the file is missing
   * @throws IOException if an I/O error occurs while reading
   * @throws IllegalStateException if parsing fails
   */
  public List<Route> loadRoutes() throws IOException {
    InputStream stream = getClass().getResourceAsStream(ROUTES_FILE);
    if (stream == null) {
      log.warn("routes.csv not found at {}", ROUTES_FILE);
      return Collections.emptyList();
    }

    try (Reader reader = new InputStreamReader(stream)) {
      List<Route> routes =
          new CsvToBeanBuilder<Route>(reader)
              .withType(Route.class)
              .withIgnoreLeadingWhiteSpace(true)
              .build()
              .parse();
      log.info("Loaded {} routes from {}", routes.size(), ROUTES_FILE);
      return routes;
    } catch (RuntimeException parseException) {
      log.error("Failed to parse {}: {}", ROUTES_FILE, parseException.getMessage());
      throw new IllegalStateException(
          "Failed to parse routes.csv: " + parseException.getMessage(), parseException);
    }
  }

  /**
   * Writes the provided list of routes to routes.csv, overwriting existing content.
   *
   * @param routes list of Route objects to write
   */
  public void saveRoutes(List<Route> routes) {
    if (routes == null || routes.isEmpty()) {
      log.warn("Attempted to save empty route list — skipping CSV write.");
      return;
    }

    Path filePath = Path.of("src/main/resources" + ROUTES_FILE);
    try {
      Files.createDirectories(filePath.getParent());

      // Reassign IDs
      for (int i = 0; i < routes.size(); i++) {
        routes.get(i).setId(i + 1);
      }

      try (Writer writer = Files.newBufferedWriter(filePath)) {
        StatefulBeanToCsv<Route> beanToCsv =
            new StatefulBeanToCsvBuilder<Route>(writer)
                .withApplyQuotesToAll(false)
                .withOrderedResults(true)
                .build();

        writer.write("id,flights,totalDuration,totalPrice,stopovers\n");
        beanToCsv.write(routes);

        log.info("Saved {} routes to {}", routes.size(), filePath);
      }

    } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
      log.error("Failed to save routes to CSV: {}", e.getMessage(), e);
    }
  }
}
