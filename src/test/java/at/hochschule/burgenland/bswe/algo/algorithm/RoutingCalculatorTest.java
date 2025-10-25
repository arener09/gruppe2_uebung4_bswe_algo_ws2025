/**
 * ----------------------------------------------------------------------------- File:
 * RoutingCalculatorTest.java Package: at.hochschule.burgenland.bswe.algo.algorithm Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import at.hochschule.burgenland.bswe.algo.model.Airport;
import at.hochschule.burgenland.bswe.algo.model.Flight;
import at.hochschule.burgenland.bswe.algo.model.Route;
import at.hochschule.burgenland.bswe.algo.model.enums.RouteType;
import at.hochschule.burgenland.bswe.algo.structure.Graph;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoutingCalculatorTest {

  private RoutingCalculator calculator;
  private Graph graph;

  @BeforeEach
  void setUp() {
    calculator = new RoutingCalculator();

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
  void testFindRouteWithCheapestType() {
    Route route = calculator.findRoute(graph, "VIE", "JFK", RouteType.CHEAPEST);

    assertNotNull(route);
    assertEquals("1", route.getFlights());
    assertEquals(450.0, route.getTotalPrice());
    assertEquals(480, route.getTotalDuration());
    assertEquals(0, route.getStopovers());
  }

  @Test
  void testFindRouteWithFastestType() {
    Route route = calculator.findRoute(graph, "VIE", "JFK", RouteType.FASTEST);

    assertNotNull(route);
    assertEquals("1", route.getFlights());
    assertEquals(480, route.getTotalDuration());
    assertEquals(450.0, route.getTotalPrice());
    assertEquals(0, route.getStopovers());
  }

  @Test
  void testFindRouteWithFewestStopoversType() {
    Route route = calculator.findRoute(graph, "VIE", "JFK", RouteType.FEWEST_STOPOVERS);

    assertNotNull(route);
    assertEquals("1", route.getFlights());
    assertEquals(0, route.getStopovers());
    assertEquals(480, route.getTotalDuration());
    assertEquals(450.0, route.getTotalPrice());
  }

  @Test
  void testFindRouteWithConnectionCheapest() {
    Route route = calculator.findRoute(graph, "VIE", "CDG", RouteType.CHEAPEST);

    assertNotNull(route);
    assertEquals("2-4", route.getFlights());
    assertEquals(200.0, route.getTotalPrice());
    assertEquals(150, route.getTotalDuration());
    assertEquals(1, route.getStopovers());
  }

  @Test
  void testFindRouteWithConnectionFastest() {
    Route route = calculator.findRoute(graph, "VIE", "CDG", RouteType.FASTEST);

    assertNotNull(route);
    assertEquals("2-4", route.getFlights());
    assertEquals(150, route.getTotalDuration());
    assertEquals(200.0, route.getTotalPrice());
    assertEquals(1, route.getStopovers());
  }

  @Test
  void testFindRouteWithConnectionFewestStopovers() {
    Route route = calculator.findRoute(graph, "VIE", "CDG", RouteType.FEWEST_STOPOVERS);

    assertNotNull(route);
    assertEquals("2-4", route.getFlights());
    assertEquals(1, route.getStopovers());
    assertEquals(150, route.getTotalDuration());
    assertEquals(200.0, route.getTotalPrice());
  }

  @Test
  void testFindRouteWithAllRouteTypes() {
    // Test all route types with the same origin and destination
    Route cheapest = calculator.findRoute(graph, "VIE", "JFK", RouteType.CHEAPEST);
    Route fastest = calculator.findRoute(graph, "VIE", "JFK", RouteType.FASTEST);
    Route fewestStopovers = calculator.findRoute(graph, "VIE", "JFK", RouteType.FEWEST_STOPOVERS);

    assertNotNull(cheapest);
    assertNotNull(fastest);
    assertNotNull(fewestStopovers);

    // All should find the same route (direct flight)
    assertEquals(cheapest.getFlights(), fastest.getFlights());
    assertEquals(cheapest.getFlights(), fewestStopovers.getFlights());
  }

  @Test
  void testFindRouteWithComplexPath() {
    // Create a more complex graph with multiple paths
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
            new Flight(3, "C", "D", "Airline", "F3", 60, 100.0, LocalTime.of(12, 0)),
            new Flight(
                4, "A", "D", "Airline", "F4", 180, 50.0, LocalTime.of(10, 0)) // Direct but cheaper
            );

    Graph complexGraph = new Graph(airports, flights);

    Route cheapest = calculator.findRoute(complexGraph, "A", "D", RouteType.CHEAPEST);
    Route fastest = calculator.findRoute(complexGraph, "A", "D", RouteType.FASTEST);
    Route fewestStopovers =
        calculator.findRoute(complexGraph, "A", "D", RouteType.FEWEST_STOPOVERS);

    assertNotNull(cheapest);
    assertNotNull(fastest);
    assertNotNull(fewestStopovers);

    // Cheapest should be the direct flight
    assertEquals("4", cheapest.getFlights());
    assertEquals(50.0, cheapest.getTotalPrice());

    // Fastest should be the direct flight
    assertEquals("4", fastest.getFlights());
    assertEquals(180, fastest.getTotalDuration());

    // Fewest stopovers should be the direct flight
    assertEquals("4", fewestStopovers.getFlights());
    assertEquals(0, fewestStopovers.getStopovers());
  }
}
