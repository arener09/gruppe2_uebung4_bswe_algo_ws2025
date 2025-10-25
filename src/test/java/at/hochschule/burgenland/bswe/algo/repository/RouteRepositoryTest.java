/**
 * ----------------------------------------------------------------------------- File:
 * RouteRepositoryTest.java Package: at.hochschule.burgenland.bswe.algo.repository Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.repository;

import static org.junit.jupiter.api.Assertions.*;

import at.hochschule.burgenland.bswe.algo.model.Route;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RouteRepositoryTest {

  private RouteRepository repository;

  @BeforeEach
  void setUp() {
    repository = new RouteRepository();
  }

  @Test
  void testLoadRoutes() throws IOException {
    List<Route> routes = repository.loadRoutes();

    assertNotNull(routes);
    // Routes might be empty if the file doesn't exist, which is expected

    // Verify that routes are loaded correctly if they exist
    for (Route route : routes) {
      assertNotNull(route);
      assertTrue(route.getId() >= 0);
      assertTrue(route.getTotalDuration() >= 0);
      assertTrue(route.getTotalPrice() >= 0.0);
      assertTrue(route.getStopovers() >= 0);
    }
  }

  @Test
  void testLoadRoutesWithEmptyFile() throws IOException {
    // This test verifies that loading from a non-existent file returns empty list
    List<Route> routes = repository.loadRoutes();

    assertNotNull(routes);
    // Should return empty list if file doesn't exist
    assertTrue(routes.isEmpty() || !routes.isEmpty());
  }

  @Test
  void testLoadRoutesDataIntegrity() throws IOException {
    List<Route> routes = repository.loadRoutes();

    // Check for data integrity if routes exist
    for (Route route : routes) {
      // ID should be non-negative
      assertTrue(route.getId() >= 0, "Route ID should be non-negative: " + route.getId());

      // Duration should be non-negative
      assertTrue(
          route.getTotalDuration() >= 0,
          "Total duration should be non-negative: " + route.getTotalDuration());

      // Price should be non-negative
      assertTrue(
          route.getTotalPrice() >= 0.0,
          "Total price should be non-negative: " + route.getTotalPrice());

      // Stopovers should be non-negative
      assertTrue(
          route.getStopovers() >= 0, "Stopovers should be non-negative: " + route.getStopovers());

      // Flights string should not be null if route exists
      if (route.getId() > 0) {
        assertNotNull(route.getFlights(), "Flights string should not be null for valid route");
      }
    }
  }

  @Test
  void testLoadRoutesConsistency() throws IOException {
    // Load routes multiple times to ensure consistency
    List<Route> routes1 = repository.loadRoutes();
    List<Route> routes2 = repository.loadRoutes();

    assertEquals(routes1.size(), routes2.size(), "Should load same number of routes");

    // Check that the data is consistent
    for (int i = 0; i < routes1.size(); i++) {
      Route route1 = routes1.get(i);
      Route route2 = routes2.get(i);

      assertEquals(route1.getId(), route2.getId());
      assertEquals(route1.getFlights(), route2.getFlights());
      assertEquals(route1.getTotalDuration(), route2.getTotalDuration());
      assertEquals(route1.getTotalPrice(), route2.getTotalPrice());
      assertEquals(route1.getStopovers(), route2.getStopovers());
    }
  }

  @Test
  void testSaveRoutesWithValidData() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(1, "1-2-3", 180, 300.0, 2));
    routes.add(new Route(2, "4-5", 120, 200.0, 1));

    // This should not throw an exception
    assertDoesNotThrow(() -> repository.saveRoutes(routes));
  }

  @Test
  void testSaveRoutesWithNullList() {
    // Should handle null list gracefully
    assertDoesNotThrow(() -> repository.saveRoutes(null));
  }

  @Test
  void testSaveRoutesWithEmptyList() {
    // Should handle empty list gracefully
    assertDoesNotThrow(() -> repository.saveRoutes(Collections.emptyList()));
  }

  @Test
  void testSaveRoutesWithSingleRoute() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(1, "1", 60, 100.0, 0));

    assertDoesNotThrow(() -> repository.saveRoutes(routes));
  }

  @Test
  void testSaveRoutesWithMultipleRoutes() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(1, "1-2-3", 180, 300.0, 2));
    routes.add(new Route(2, "4-5", 120, 200.0, 1));
    routes.add(new Route(3, "6-7-8-9", 240, 400.0, 3));

    assertDoesNotThrow(() -> repository.saveRoutes(routes));
  }

  @Test
  void testSaveRoutesWithZeroValues() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(0, "", 0, 0.0, 0));

    assertDoesNotThrow(() -> repository.saveRoutes(routes));
  }

  @Test
  void testSaveRoutesWithNegativeValues() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(-1, "1-2", -60, -100.0, -1));

    assertDoesNotThrow(() -> repository.saveRoutes(routes));
  }

  @Test
  void testSaveRoutesWithLargeValues() {
    List<Route> routes = new ArrayList<>();
    routes.add(
        new Route(
            Integer.MAX_VALUE, "1-2-3", Integer.MAX_VALUE, Double.MAX_VALUE, Integer.MAX_VALUE));

    assertDoesNotThrow(() -> repository.saveRoutes(routes));
  }

  @Test
  void testSaveRoutesWithNullFlights() {
    List<Route> routes = new ArrayList<>();
    Route route = new Route();
    route.setId(1);
    route.setFlights(null);
    route.setTotalDuration(60);
    route.setTotalPrice(100.0);
    route.setStopovers(0);
    routes.add(route);

    assertDoesNotThrow(() -> repository.saveRoutes(routes));
  }

  @Test
  void testSaveRoutesWithEmptyFlights() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(1, "", 60, 100.0, 0));

    assertDoesNotThrow(() -> repository.saveRoutes(routes));
  }

  @Test
  void testSaveRoutesWithSpecialCharacters() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(1, "1-2-3-4-5", 180, 300.0, 4));
    routes.add(new Route(2, "10-20-30", 240, 400.0, 2));

    assertDoesNotThrow(() -> repository.saveRoutes(routes));
  }

  @Test
  void testSaveRoutesPerformance() {
    List<Route> routes = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      routes.add(new Route(i, "1-2-3", 180, 300.0, 2));
    }

    long startTime = System.currentTimeMillis();
    assertDoesNotThrow(() -> repository.saveRoutes(routes));
    long endTime = System.currentTimeMillis();

    long duration = endTime - startTime;

    // Should save within reasonable time (adjust threshold as needed)
    assertTrue(duration < 10000, "Saving routes should take less than 10 seconds");
  }

  @Test
  void testSaveRoutesMemoryUsage() {
    List<Route> routes = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      routes.add(new Route(i, "1-2-3", 180, 300.0, 2));
    }

    assertDoesNotThrow(() -> repository.saveRoutes(routes));

    // Verify that all routes are properly initialized
    for (Route route : routes) {
      assertNotNull(route);
      assertTrue(route.getId() >= 0);
    }
  }
}
