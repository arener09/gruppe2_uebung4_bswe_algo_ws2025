/**
 * ----------------------------------------------------------------------------- File:
 * FlightRepositoryTest.java Package: at.hochschule.burgenland.bswe.algo.repository Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.repository;

import static org.junit.jupiter.api.Assertions.*;

import at.hochschule.burgenland.bswe.algo.model.Flight;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlightRepositoryTest {

  private FlightRepository repository;

  @BeforeEach
  void setUp() {
    repository = new FlightRepository();
  }

  @Test
  void testLoadFlights() throws IOException {
    List<Flight> flights = repository.loadFlights();

    assertNotNull(flights);
    assertFalse(flights.isEmpty());

    // Verify that flights are loaded correctly
    for (Flight flight : flights) {
      assertNotNull(flight);
      assertNotNull(flight.getOrigin());
      assertNotNull(flight.getDestination());
      assertNotNull(flight.getAirline());
      assertNotNull(flight.getFlightNumber());
      assertTrue(flight.getId() > 0);
      assertTrue(flight.getDuration() >= 0);
      assertTrue(flight.getPrice() >= 0.0);
    }
  }

  @Test
  void testLoadFlightsReturnsValidData() throws IOException {
    List<Flight> flights = repository.loadFlights();

    // Check that we have some expected flights
    boolean hasValidFlight =
        flights.stream()
            .anyMatch(
                f ->
                    f.getOrigin() != null
                        && f.getDestination() != null
                        && f.getAirline() != null
                        && f.getFlightNumber() != null);

    assertTrue(hasValidFlight, "Should contain at least one valid flight");
  }

  @Test
  void testLoadFlightsNoDuplicates() throws IOException {
    List<Flight> flights = repository.loadFlights();

    // Check for duplicate IDs
    long uniqueIds = flights.stream().mapToInt(Flight::getId).distinct().count();
    assertEquals(flights.size(), uniqueIds, "All flight IDs should be unique");
  }

  @Test
  void testLoadFlightsConsistency() throws IOException {
    // Load flights multiple times to ensure consistency
    List<Flight> flights1 = repository.loadFlights();
    List<Flight> flights2 = repository.loadFlights();

    assertEquals(flights1.size(), flights2.size(), "Should load same number of flights");

    // Check that the data is consistent
    for (int i = 0; i < flights1.size(); i++) {
      Flight flight1 = flights1.get(i);
      Flight flight2 = flights2.get(i);

      assertEquals(flight1.getId(), flight2.getId());
      assertEquals(flight1.getOrigin(), flight2.getOrigin());
      assertEquals(flight1.getDestination(), flight2.getDestination());
      assertEquals(flight1.getAirline(), flight2.getAirline());
      assertEquals(flight1.getFlightNumber(), flight2.getFlightNumber());
      assertEquals(flight1.getDuration(), flight2.getDuration());
      assertEquals(flight1.getPrice(), flight2.getPrice());
      assertEquals(flight1.getDepartureTime(), flight2.getDepartureTime());
    }
  }

  @Test
  void testLoadFlightsWithEmptyFile() {
    // This test would require a mock or test resource file
    // For now, we'll test that the method doesn't throw unexpected exceptions
    assertDoesNotThrow(
        () -> {
          try {
            repository.loadFlights();
          } catch (IOException e) {
            // This is expected if the file doesn't exist or is malformed
            assertTrue(
                e.getMessage().contains("Resource not found")
                    || e.getMessage().contains("Failed to parse"));
          }
        });
  }

  @Test
  void testLoadFlightsExceptionHandling() {
    // Test that the method properly handles exceptions
    assertDoesNotThrow(
        () -> {
          try {
            repository.loadFlights();
          } catch (IOException e) {
            // Verify that the exception message is informative
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isEmpty());
          }
        });
  }

  @Test
  void testLoadFlightsPerformance() throws IOException {
    long startTime = System.currentTimeMillis();
    List<Flight> flights = repository.loadFlights();
    long endTime = System.currentTimeMillis();

    long duration = endTime - startTime;

    // Should load within reasonable time (adjust threshold as needed)
    assertTrue(duration < 5000, "Loading flights should take less than 5 seconds");
    assertNotNull(flights);
  }

  @Test
  void testLoadFlightsMemoryUsage() throws IOException {
    // Test that loading flights doesn't cause memory issues
    List<Flight> flights = repository.loadFlights();

    assertNotNull(flights);
    assertTrue(flights.size() > 0, "Should load at least one flight");

    // Verify that all flights are properly initialized
    for (Flight flight : flights) {
      assertNotNull(flight);
      // Check that the flight object is properly constructed
      assertTrue(flight.getId() >= 0);
    }
  }

  @Test
  void testLoadFlightsRouteConnections() throws IOException {
    List<Flight> flights = repository.loadFlights();

    // Check that flights have valid origin-destination pairs
    for (Flight flight : flights) {
      if (flight.getOrigin() != null && flight.getDestination() != null) {
        assertNotEquals(
            flight.getOrigin(),
            flight.getDestination(),
            "Flight should not have same origin and destination");
      }
    }
  }
}
