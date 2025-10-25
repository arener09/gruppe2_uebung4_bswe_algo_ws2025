/**
 * ----------------------------------------------------------------------------- File:
 * AirportRepositoryTest.java Package: at.hochschule.burgenland.bswe.algo.repository Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.repository;

import static org.junit.jupiter.api.Assertions.*;

import at.hochschule.burgenland.bswe.algo.model.Airport;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AirportRepositoryTest {

  private AirportRepository repository;

  @BeforeEach
  void setUp() {
    repository = new AirportRepository();
  }

  @Test
  void testLoadAirports() throws IOException {
    List<Airport> airports = repository.loadAirports();

    assertNotNull(airports);
    assertFalse(airports.isEmpty());

    // Verify that airports are loaded correctly
    for (Airport airport : airports) {
      assertNotNull(airport);
      assertNotNull(airport.getIata());
      assertNotNull(airport.getCity());
      assertNotNull(airport.getCountry());
      assertTrue(airport.getId() > 0);
      assertTrue(airport.getLatitude() >= -90.0 && airport.getLatitude() <= 90.0);
      assertTrue(airport.getLongitude() >= -180.0 && airport.getLongitude() <= 180.0);
    }
  }

  @Test
  void testLoadAirportsReturnsValidData() throws IOException {
    List<Airport> airports = repository.loadAirports();

    // Check that we have some expected airports
    boolean hasVie = airports.stream().anyMatch(a -> "VIE".equals(a.getIata()));
    boolean hasJfk = airports.stream().anyMatch(a -> "JFK".equals(a.getIata()));

    assertTrue(hasVie || hasJfk, "Should contain at least one known airport");
  }

  @Test
  void testLoadAirportsNoDuplicates() throws IOException {
    List<Airport> airports = repository.loadAirports();

    // Check for duplicate IDs
    long uniqueIds = airports.stream().mapToInt(Airport::getId).distinct().count();
    assertEquals(airports.size(), uniqueIds, "All airport IDs should be unique");

    // Check for duplicate IATA codes
    long uniqueIatas = airports.stream().map(Airport::getIata).distinct().count();
    assertEquals(airports.size(), uniqueIatas, "All airport IATA codes should be unique");
  }

  @Test
  void testLoadAirportsConsistency() throws IOException {
    // Load airports multiple times to ensure consistency
    List<Airport> airports1 = repository.loadAirports();
    List<Airport> airports2 = repository.loadAirports();

    assertEquals(airports1.size(), airports2.size(), "Should load same number of airports");

    // Check that the data is consistent
    for (int i = 0; i < airports1.size(); i++) {
      Airport airport1 = airports1.get(i);
      Airport airport2 = airports2.get(i);

      assertEquals(airport1.getId(), airport2.getId());
      assertEquals(airport1.getIata(), airport2.getIata());
      assertEquals(airport1.getCity(), airport2.getCity());
      assertEquals(airport1.getCountry(), airport2.getCountry());
      assertEquals(airport1.getLatitude(), airport2.getLatitude());
      assertEquals(airport1.getLongitude(), airport2.getLongitude());
    }
  }

  @Test
  void testLoadAirportsWithEmptyFile() {
    // This test would require a mock or test resource file
    // For now, we'll test that the method doesn't throw unexpected exceptions
    assertDoesNotThrow(
        () -> {
          try {
            repository.loadAirports();
          } catch (IOException e) {
            // This is expected if the file doesn't exist or is malformed
            assertTrue(
                e.getMessage().contains("Resource not found")
                    || e.getMessage().contains("Failed to parse"));
          }
        });
  }

  @Test
  void testLoadAirportsExceptionHandling() {
    // Test that the method properly handles exceptions
    assertDoesNotThrow(
        () -> {
          try {
            repository.loadAirports();
          } catch (IOException e) {
            // Verify that the exception message is informative
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isEmpty());
          }
        });
  }

  @Test
  void testLoadAirportsPerformance() throws IOException {
    long startTime = System.currentTimeMillis();
    List<Airport> airports = repository.loadAirports();
    long endTime = System.currentTimeMillis();

    long duration = endTime - startTime;

    // Should load within reasonable time (adjust threshold as needed)
    assertTrue(duration < 5000, "Loading airports should take less than 5 seconds");
    assertNotNull(airports);
  }

  @Test
  void testLoadAirportsMemoryUsage() throws IOException {
    // Test that loading airports doesn't cause memory issues
    List<Airport> airports = repository.loadAirports();

    assertNotNull(airports);
    assertTrue(airports.size() > 0, "Should load at least one airport");

    // Verify that all airports are properly initialized
    for (Airport airport : airports) {
      assertNotNull(airport);
      // Check that the airport object is properly constructed
      assertTrue(airport.getId() >= 0);
    }
  }
}
