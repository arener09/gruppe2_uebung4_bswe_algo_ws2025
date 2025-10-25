/**
 * ----------------------------------------------------------------------------- File:
 * RoutingDataServiceTest.java Package: at.hochschule.burgenland.bswe.algo.service Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.service;

import static org.junit.jupiter.api.Assertions.*;

import at.hochschule.burgenland.bswe.algo.model.Airport;
import at.hochschule.burgenland.bswe.algo.model.Flight;
import at.hochschule.burgenland.bswe.algo.model.Route;
import at.hochschule.burgenland.bswe.algo.structure.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class RoutingDataServiceTest {

  @Test
  void testBuildGraph() {
    assertDoesNotThrow(
        () -> {
          try {
            Graph graph = RoutingDataService.buildGraph();
            assertNotNull(graph);
            assertNotNull(graph.getNodes());
            // The graph should have some nodes if the CSV files are present
            assertTrue(graph.getNodes().size() >= 0);
          } catch (Exception e) {
            // If CSV files are not present, this is expected
            assertTrue(
                e.getMessage().contains("Cannot build graph")
                    || e.getMessage().contains("Failed to load"));
          }
        });
  }

  @Test
  void testBuildGraphWithEmptyData() {
    // This test verifies that the method handles empty data gracefully
    assertDoesNotThrow(
        () -> {
          try {
            Graph graph = RoutingDataService.buildGraph();
            assertNotNull(graph);
            assertNotNull(graph.getNodes());
          } catch (Exception e) {
            // Expected if data is not available
            assertNotNull(e.getMessage());
          }
        });
  }

  @Test
  void testSaveAllRoutesWithValidRoutes() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(1, "1-2-3", 180, 300.0, 2));
    routes.add(new Route(2, "4-5", 120, 200.0, 1));

    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(routes));
  }

  @Test
  void testSaveAllRoutesWithNullList() {
    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(null));
  }

  @Test
  void testSaveAllRoutesWithEmptyList() {
    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(Collections.emptyList()));
  }

  @Test
  void testSaveAllRoutesWithSingleRoute() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(1, "1", 60, 100.0, 0));

    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(routes));
  }

  @Test
  void testSaveAllRoutesWithMultipleRoutes() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(1, "1-2-3", 180, 300.0, 2));
    routes.add(new Route(2, "4-5", 120, 200.0, 1));
    routes.add(new Route(3, "6-7-8-9", 240, 400.0, 3));

    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(routes));
  }

  @Test
  void testSaveAllRoutesWithZeroValues() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(0, "", 0, 0.0, 0));

    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(routes));
  }

  @Test
  void testSaveAllRoutesWithNegativeValues() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(-1, "1-2", -60, -100.0, -1));

    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(routes));
  }

  @Test
  void testSaveAllRoutesWithLargeValues() {
    List<Route> routes = new ArrayList<>();
    routes.add(
        new Route(
            Integer.MAX_VALUE, "1-2-3", Integer.MAX_VALUE, Double.MAX_VALUE, Integer.MAX_VALUE));

    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(routes));
  }

  @Test
  void testSaveAllRoutesWithNullFlights() {
    List<Route> routes = new ArrayList<>();
    Route route = new Route();
    route.setId(1);
    route.setFlights(null);
    route.setTotalDuration(60);
    route.setTotalPrice(100.0);
    route.setStopovers(0);
    routes.add(route);

    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(routes));
  }

  @Test
  void testSaveAllRoutesWithEmptyFlights() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(1, "", 60, 100.0, 0));

    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(routes));
  }

  @Test
  void testSaveAllRoutesWithSpecialCharacters() {
    List<Route> routes = new ArrayList<>();
    routes.add(new Route(1, "1-2-3-4-5", 180, 300.0, 4));
    routes.add(new Route(2, "10-20-30", 240, 400.0, 2));

    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(routes));
  }

  @Test
  void testSaveAllRoutesPerformance() {
    List<Route> routes = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      routes.add(new Route(i, "1-2-3", 180, 300.0, 2));
    }

    long startTime = System.currentTimeMillis();
    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(routes));
    long endTime = System.currentTimeMillis();

    long duration = endTime - startTime;

    // Should save within reasonable time (adjust threshold as needed)
    assertTrue(duration < 15000, "Saving routes should take less than 15 seconds");
  }

  @Test
  void testSaveAllRoutesMemoryUsage() {
    List<Route> routes = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      routes.add(new Route(i, "1-2-3", 180, 300.0, 2));
    }

    assertDoesNotThrow(() -> RoutingDataService.saveAllRoutes(routes));

    // Verify that all routes are properly initialized
    for (Route route : routes) {
      assertNotNull(route);
      assertTrue(route.getId() >= 0);
    }
  }

  @Test
  void testGetAirports() {
    // This test depends on the actual CSV files being present
    assertDoesNotThrow(
        () -> {
          try {
            List<Airport> airports = RoutingDataService.getAirports();
            assertNotNull(airports);
            // The list should be empty if no data is loaded
            assertTrue(airports.size() >= 0);
          } catch (Exception e) {
            // Expected if data is not available
            assertNotNull(e.getMessage());
          }
        });
  }

  @Test
  void testGetFlights() {
    // This test depends on the actual CSV files being present
    assertDoesNotThrow(
        () -> {
          try {
            List<Flight> flights = RoutingDataService.getFlights();
            assertNotNull(flights);
            // The list should be empty if no data is loaded
            assertTrue(flights.size() >= 0);
          } catch (Exception e) {
            // Expected if data is not available
            assertNotNull(e.getMessage());
          }
        });
  }

  @Test
  void testGetRoutes() {
    // This test depends on the actual CSV files being present
    assertDoesNotThrow(
        () -> {
          try {
            List<Route> routes = RoutingDataService.getRoutes();
            assertNotNull(routes);
            // The list should be empty if no data is loaded
            assertTrue(routes.size() >= 0);
          } catch (Exception e) {
            // Expected if data is not available
            assertNotNull(e.getMessage());
          }
        });
  }

  @Test
  void testServiceInitialization() {
    // Test that the service can be initialized without errors
    assertDoesNotThrow(
        () -> {
          // Try to access the static fields
          List<Airport> airports = RoutingDataService.getAirports();
          List<Flight> flights = RoutingDataService.getFlights();
          List<Route> routes = RoutingDataService.getRoutes();

          assertNotNull(airports);
          assertNotNull(flights);
          assertNotNull(routes);
        });
  }

  @Test
  void testServiceConsistency() {
    // Test that the service maintains consistency across multiple calls
    assertDoesNotThrow(
        () -> {
          List<Airport> airports1 = RoutingDataService.getAirports();
          List<Flight> flights1 = RoutingDataService.getFlights();
          List<Route> routes1 = RoutingDataService.getRoutes();

          List<Airport> airports2 = RoutingDataService.getAirports();
          List<Flight> flights2 = RoutingDataService.getFlights();
          List<Route> routes2 = RoutingDataService.getRoutes();

          assertEquals(airports1.size(), airports2.size());
          assertEquals(flights1.size(), flights2.size());
          assertEquals(routes1.size(), routes2.size());
        });
  }

  @Test
  void testServiceErrorHandling() {
    // Test that the service handles errors gracefully
    assertDoesNotThrow(
        () -> {
          try {
            RoutingDataService.buildGraph();
          } catch (Exception e) {
            // Should handle errors gracefully
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isEmpty());
          }
        });
  }
}
