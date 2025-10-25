/**
 * ----------------------------------------------------------------------------- File:
 * BreadthFirstSearchAlgorithmTest.java Package: at.hochschule.burgenland.bswe.algo.algorithm.path
 * Authors: Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.algorithm.path;

import static org.junit.jupiter.api.Assertions.*;

import at.hochschule.burgenland.bswe.algo.model.Airport;
import at.hochschule.burgenland.bswe.algo.model.Flight;
import at.hochschule.burgenland.bswe.algo.model.Route;
import at.hochschule.burgenland.bswe.algo.structure.Graph;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BreadthFirstSearchAlgorithmTest {

  private BreadthFirstSearchAlgorithm algorithm;
  private Graph graph;

  @BeforeEach
  void setUp() {
    algorithm = new BreadthFirstSearchAlgorithm();

    // Create test data
    List<Airport> airports =
        List.of(
            new Airport(1, "VIE", "Vienna", "Austria", 48.1103, 16.5697),
            new Airport(2, "JFK", "New York", "United States", 40.6413, -73.7781),
            new Airport(3, "LHR", "London", "United Kingdom", 51.4700, -0.4543),
            new Airport(4, "CDG", "Paris", "France", 49.0097, 2.5479),
            new Airport(5, "FRA", "Frankfurt", "Germany", 50.0379, 8.5622));

    List<Flight> flights =
        List.of(
            new Flight(
                1, "VIE", "JFK", "Austrian Airlines", "OS35", 480, 450.0, LocalTime.of(10, 35)),
            new Flight(
                2, "VIE", "LHR", "Austrian Airlines", "OS45", 90, 120.0, LocalTime.of(14, 20)),
            new Flight(
                3, "LHR", "JFK", "British Airways", "BA123", 420, 380.0, LocalTime.of(16, 30)),
            new Flight(4, "LHR", "CDG", "British Airways", "BA456", 60, 80.0, LocalTime.of(18, 45)),
            new Flight(5, "CDG", "JFK", "Air France", "AF123", 450, 400.0, LocalTime.of(20, 15)),
            new Flight(
                6, "VIE", "FRA", "Austrian Airlines", "OS67", 60, 100.0, LocalTime.of(12, 0)),
            new Flight(7, "FRA", "JFK", "Lufthansa", "LH400", 480, 420.0, LocalTime.of(14, 0)));

    graph = new Graph(airports, flights);
  }

  @Test
  void testFindRouteWithDirectFlight() {
    Route route = algorithm.findRoute(graph, "VIE", "JFK");

    assertNotNull(route);
    assertEquals("1", route.getFlights());
    assertEquals(480, route.getTotalDuration());
    assertEquals(450.0, route.getTotalPrice());
    assertEquals(0, route.getStopovers());
  }

  @Test
  void testFindRouteWithOneStopover() {
    Route route = algorithm.findRoute(graph, "VIE", "CDG");

    assertNotNull(route);
    assertEquals("2-4", route.getFlights());
    assertEquals(150, route.getTotalDuration());
    assertEquals(200.0, route.getTotalPrice());
    assertEquals(1, route.getStopovers());
  }

  @Test
  void testFindRouteWithMultiplePaths() {
    // There are multiple paths from VIE to JFK:
    // 1. Direct: VIE -> JFK (1 stopover = 0)
    // 2. Via LHR: VIE -> LHR -> JFK (2 stopovers = 1)
    // 3. Via FRA: VIE -> FRA -> JFK (2 stopovers = 1)
    // BFS should find the path with fewest stopovers (direct flight)
    Route route = algorithm.findRoute(graph, "VIE", "JFK");

    assertNotNull(route);
    assertEquals("1", route.getFlights());
    assertEquals(0, route.getStopovers());
  }

  @Test
  void testFindRouteWithInvalidOrigin() {
    Route route = algorithm.findRoute(graph, "INVALID", "JFK");
    assertNull(route);
  }

  @Test
  void testFindRouteWithInvalidDestination() {
    Route route = algorithm.findRoute(graph, "VIE", "INVALID");
    assertNull(route);
  }

  @Test
  void testFindRouteWithNullOrigin() {
    Route route = algorithm.findRoute(graph, null, "JFK");
    assertNull(route);
  }

  @Test
  void testFindRouteWithNullDestination() {
    Route route = algorithm.findRoute(graph, "VIE", null);
    assertNull(route);
  }

  @Test
  void testFindRouteWithNoPath() {
    // Create a graph with no connection between VIE and CDG
    List<Airport> airports =
        List.of(
            new Airport(1, "VIE", "Vienna", "Austria", 48.1103, 16.5697),
            new Airport(2, "CDG", "Paris", "France", 49.0097, 2.5479));

    List<Flight> flights =
        List.of(
            new Flight(
                1, "VIE", "JFK", "Austrian Airlines", "OS35", 480, 450.0, LocalTime.of(10, 35)));

    Graph disconnectedGraph = new Graph(airports, flights);

    Route route = algorithm.findRoute(disconnectedGraph, "VIE", "CDG");
    assertNull(route);
  }

  @Test
  void testFindRouteWithComplexPath() {
    // Test a more complex path: VIE -> FRA -> JFK
    Route route = algorithm.findRoute(graph, "VIE", "JFK");

    assertNotNull(route);
    // Should find the direct flight (fewest stopovers)
    assertEquals("1", route.getFlights());
    assertEquals(0, route.getStopovers());
  }

  @Test
  void testFindRouteWithTwoStopovers() {
    // Create a graph where we need 2 stopovers
    List<Airport> airports =
        List.of(
            new Airport(1, "A", "Airport A", "Country A", 0.0, 0.0),
            new Airport(2, "B", "Airport B", "Country B", 1.0, 1.0),
            new Airport(3, "C", "Airport C", "Country C", 2.0, 2.0),
            new Airport(4, "D", "Airport D", "Country D", 3.0, 3.0));

    List<Flight> flights =
        List.of(
            new Flight(1, "A", "B", "Airline", "F1", 60, 100.0, LocalTime.of(10, 0)),
            new Flight(2, "B", "C", "Airline", "F2", 60, 100.0, LocalTime.of(11, 0)),
            new Flight(3, "C", "D", "Airline", "F3", 60, 100.0, LocalTime.of(12, 0)));

    Graph complexGraph = new Graph(airports, flights);

    Route route = algorithm.findRoute(complexGraph, "A", "D");

    assertNotNull(route);
    assertEquals("1-2-3", route.getFlights());
    assertEquals(180, route.getTotalDuration());
    assertEquals(300.0, route.getTotalPrice());
    assertEquals(2, route.getStopovers());
  }
}
