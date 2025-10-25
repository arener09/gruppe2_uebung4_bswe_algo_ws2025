/**
 * ----------------------------------------------------------------------------- File:
 * DijkstraAlgorithmTest.java Package: at.hochschule.burgenland.bswe.algo.algorithm.path Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.algorithm.path;

import static org.junit.jupiter.api.Assertions.*;

import at.hochschule.burgenland.bswe.algo.model.Airport;
import at.hochschule.burgenland.bswe.algo.model.Flight;
import at.hochschule.burgenland.bswe.algo.model.Route;
import at.hochschule.burgenland.bswe.algo.structure.Edge;
import at.hochschule.burgenland.bswe.algo.structure.Graph;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DijkstraAlgorithmTest {

  private DijkstraAlgorithm algorithm;
  private Graph graph;

  @BeforeEach
  void setUp() {
    algorithm = new DijkstraAlgorithm();

    // Create test data
    List<Airport> airports =
        List.of(
            new Airport(1, "VIE", "Vienna", "Austria", 48.1103, 16.5697),
            new Airport(2, "JFK", "New York", "United States", 40.6413, -73.7781),
            new Airport(3, "LHR", "London", "United Kingdom", 51.4700, -0.4543),
            new Airport(4, "CDG", "Paris", "France", 49.0097, 2.5479));

    List<Flight> flights =
        List.of(
            new Flight(
                1, "VIE", "JFK", "Austrian Airlines", "OS35", 480, 450.0, LocalTime.of(10, 35)),
            new Flight(
                2, "VIE", "LHR", "Austrian Airlines", "OS45", 90, 120.0, LocalTime.of(14, 20)),
            new Flight(
                3, "LHR", "JFK", "British Airways", "BA123", 420, 380.0, LocalTime.of(16, 30)),
            new Flight(4, "LHR", "CDG", "British Airways", "BA456", 60, 80.0, LocalTime.of(18, 45)),
            new Flight(5, "CDG", "JFK", "Air France", "AF123", 450, 400.0, LocalTime.of(20, 15)));

    graph = new Graph(airports, flights);
  }

  @Test
  void testFindRouteWithValidPath() {
    Route route = algorithm.findRoute(graph, "VIE", "JFK", Edge::getPrice);

    assertNotNull(route);
    assertEquals("1", route.getFlights());
    assertEquals(480, route.getTotalDuration());
    assertEquals(450.0, route.getTotalPrice());
    assertEquals(0, route.getStopovers());
  }

  @Test
  void testFindRouteWithConnection() {
    Route route = algorithm.findRoute(graph, "VIE", "CDG", Edge::getPrice);

    assertNotNull(route);
    assertEquals("2-4", route.getFlights());
    assertEquals(150, route.getTotalDuration());
    assertEquals(200.0, route.getTotalPrice());
    assertEquals(1, route.getStopovers());
  }

  @Test
  void testFindRouteWithMultipleConnections() {
    Route route = algorithm.findRoute(graph, "VIE", "JFK", Edge::getDuration);

    assertNotNull(route);
    // Should find the fastest route (direct flight)
    assertEquals("1", route.getFlights());
    assertEquals(480, route.getTotalDuration());
  }

  @Test
  void testFindRouteWithInvalidOrigin() {
    Route route = algorithm.findRoute(graph, "INVALID", "JFK", Edge::getPrice);
    assertNull(route);
  }

  @Test
  void testFindRouteWithInvalidDestination() {
    Route route = algorithm.findRoute(graph, "VIE", "INVALID", Edge::getPrice);
    assertNull(route);
  }

  @Test
  void testFindRouteWithNullOrigin() {
    Route route = algorithm.findRoute(graph, null, "JFK", Edge::getPrice);
    assertNull(route);
  }

  @Test
  void testFindRouteWithNullDestination() {
    Route route = algorithm.findRoute(graph, "VIE", null, Edge::getPrice);
    assertNull(route);
  }

  @Test
  void testFindRouteWithPriceOptimization() {
    Route route = algorithm.findRoute(graph, "VIE", "JFK", Edge::getPrice);

    assertNotNull(route);
    // Should find the cheapest route (direct flight at 450.0)
    assertEquals(450.0, route.getTotalPrice());
  }

  @Test
  void testFindRouteWithDurationOptimization() {
    Route route = algorithm.findRoute(graph, "VIE", "JFK", Edge::getDuration);

    assertNotNull(route);
    // Should find the fastest route (direct flight at 480 minutes)
    assertEquals(480, route.getTotalDuration());
  }

  @Test
  void testFindRouteWithCustomWeightFunction() {
    // Test with a custom weight function that prioritizes shorter flights
    Route route = algorithm.findRoute(graph, "VIE", "JFK", edge -> edge.getDuration() * 0.1);

    assertNotNull(route);
    // Should still find a valid route
    assertTrue(route.getTotalDuration() > 0);
  }

  @Test
  void testFindRouteWithNegativeWeights() {
    // Test with negative weights (should still work with Dijkstra)
    Route route = algorithm.findRoute(graph, "VIE", "JFK", edge -> -edge.getPrice());

    assertNotNull(route);
    // Should find a route (though the interpretation of "shortest" changes)
    assertTrue(route.getTotalPrice() > 0);
  }

  @Test
  void testFindRouteWithZeroWeights() {
    // Test with zero weights
    Route route = algorithm.findRoute(graph, "VIE", "JFK", edge -> 0.0);

    assertNotNull(route);
    // Should find a route
    assertTrue(route.getTotalDuration() > 0);
  }

  @Test
  void testFindRouteWithLargeGraph() {
    // Create a larger graph to test performance
    List<Airport> airports =
        List.of(
            new Airport(1, "A", "Airport A", "Country A", 0.0, 0.0),
            new Airport(2, "B", "Airport B", "Country B", 1.0, 1.0),
            new Airport(3, "C", "Airport C", "Country C", 2.0, 2.0),
            new Airport(4, "D", "Airport D", "Country D", 3.0, 3.0),
            new Airport(5, "E", "Airport E", "Country E", 4.0, 4.0));

    List<Flight> flights =
        List.of(
            new Flight(1, "A", "B", "Airline", "F1", 60, 100.0, LocalTime.of(10, 0)),
            new Flight(2, "B", "C", "Airline", "F2", 60, 100.0, LocalTime.of(11, 0)),
            new Flight(3, "C", "D", "Airline", "F3", 60, 100.0, LocalTime.of(12, 0)),
            new Flight(4, "D", "E", "Airline", "F4", 60, 100.0, LocalTime.of(13, 0)),
            new Flight(
                5, "A", "E", "Airline", "F5", 240, 50.0, LocalTime.of(10, 0)) // Direct but cheaper
            );

    Graph largeGraph = new Graph(airports, flights);

    Route route = algorithm.findRoute(largeGraph, "A", "E", Edge::getPrice);

    assertNotNull(route);
    // Should find the direct flight (cheapest)
    assertEquals("5", route.getFlights());
    assertEquals(50.0, route.getTotalPrice());
  }
}
