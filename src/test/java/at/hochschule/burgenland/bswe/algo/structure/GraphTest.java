/**
 * ----------------------------------------------------------------------------- File:
 * GraphTest.java Package: at.hochschule.burgenland.bswe.algo.structure Authors: Alexander R.
 * Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.structure;

import static org.junit.jupiter.api.Assertions.*;

import at.hochschule.burgenland.bswe.algo.model.Airport;
import at.hochschule.burgenland.bswe.algo.model.Flight;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {

  private List<Airport> airports;
  private List<Flight> flights;
  private Graph graph;

  @BeforeEach
  void setUp() {
    // Create test airports
    airports = new ArrayList<>();
    airports.add(new Airport(1, "VIE", "Vienna", "Austria", 48.1103, 16.5697));
    airports.add(new Airport(2, "JFK", "New York", "United States", 40.6413, -73.7781));
    airports.add(new Airport(3, "LHR", "London", "United Kingdom", 51.4700, -0.4543));
    airports.add(new Airport(4, "CDG", "Paris", "France", 49.0097, 2.5479));

    // Create test flights
    flights = new ArrayList<>();
    flights.add(
        new Flight(1, "VIE", "JFK", "Austrian Airlines", "OS35", 480, 450.0, LocalTime.of(10, 35)));
    flights.add(
        new Flight(2, "VIE", "LHR", "Austrian Airlines", "OS45", 90, 120.0, LocalTime.of(14, 20)));
    flights.add(
        new Flight(3, "LHR", "JFK", "British Airways", "BA123", 420, 380.0, LocalTime.of(16, 30)));
    flights.add(
        new Flight(4, "LHR", "CDG", "British Airways", "BA456", 60, 80.0, LocalTime.of(18, 45)));
    flights.add(
        new Flight(5, "CDG", "JFK", "Air France", "AF123", 450, 400.0, LocalTime.of(20, 15)));

    graph = new Graph(airports, flights);
  }

  @Test
  void testGraphConstruction() {
    assertNotNull(graph);
    assertNotNull(graph.getNodes());
    assertEquals(4, graph.getNodes().size());
  }

  @Test
  void testNodesContainAllAirports() {
    assertTrue(graph.getNodes().containsKey("VIE"));
    assertTrue(graph.getNodes().containsKey("JFK"));
    assertTrue(graph.getNodes().containsKey("LHR"));
    assertTrue(graph.getNodes().containsKey("CDG"));
  }

  @Test
  void testNodeStructure() {
    Node vieNode = graph.getNodes().get("VIE");
    assertNotNull(vieNode);
    assertEquals("VIE", vieNode.getIata());
    assertEquals(2, vieNode.getOutgoingEdges().size());
  }

  @Test
  void testOutgoingEdges() {
    List<Edge> vieEdges = graph.getOutgoingEdges("VIE");
    assertEquals(2, vieEdges.size());

    // Check that edges point to correct destinations
    List<String> destinations = vieEdges.stream().map(Edge::getDestinationIata).sorted().toList();
    assertEquals(List.of("JFK", "LHR"), destinations);
  }

  @Test
  void testOutgoingEdgesForNonExistentAirport() {
    List<Edge> edges = graph.getOutgoingEdges("NONEXISTENT");
    assertNotNull(edges);
    assertTrue(edges.isEmpty());
  }

  @Test
  void testOutgoingEdgesForNullAirport() {
    List<Edge> edges = graph.getOutgoingEdges(null);
    assertNotNull(edges);
    assertTrue(edges.isEmpty());
  }

  @Test
  void testEdgeProperties() {
    List<Edge> vieEdges = graph.getOutgoingEdges("VIE");

    // Find the edge to JFK
    Edge jfkEdge =
        vieEdges.stream()
            .filter(edge -> "JFK".equals(edge.getDestinationIata()))
            .findFirst()
            .orElse(null);

    assertNotNull(jfkEdge);
    assertEquals(480, jfkEdge.getDuration());
    assertEquals(450.0, jfkEdge.getPrice());
    assertNotNull(jfkEdge.getFlight());
    assertEquals(1, jfkEdge.getFlight().getId());
  }

  @Test
  void testEmptyAirportsList() {
    Graph emptyGraph = new Graph(Collections.emptyList(), flights);
    assertNotNull(emptyGraph.getNodes());
    assertTrue(emptyGraph.getNodes().isEmpty());
  }

  @Test
  void testEmptyFlightsList() {
    Graph emptyGraph = new Graph(airports, Collections.emptyList());
    assertNotNull(emptyGraph.getNodes());
    assertEquals(4, emptyGraph.getNodes().size());

    // All nodes should have empty outgoing edges
    for (Node node : emptyGraph.getNodes().values()) {
      assertTrue(node.getOutgoingEdges().isEmpty());
    }
  }

  @Test
  void testNullAirportsList() {
    assertThrows(NullPointerException.class, () -> new Graph(null, flights));
  }

  @Test
  void testNullFlightsList() {
    assertThrows(NullPointerException.class, () -> new Graph(airports, null));
  }

  @Test
  void testFlightWithNonExistentOrigin() {
    List<Flight> flightsWithInvalidOrigin = new ArrayList<>();
    flightsWithInvalidOrigin.add(
        new Flight(99, "INVALID", "JFK", "Test Airline", "TA99", 60, 100.0, LocalTime.of(12, 0)));

    Graph graphWithInvalidFlight = new Graph(airports, flightsWithInvalidOrigin);

    // The graph should still be created, but the invalid flight should be ignored
    assertNotNull(graphWithInvalidFlight);
    assertEquals(4, graphWithInvalidFlight.getNodes().size());

    // No edges should be created for the invalid flight
    for (Node node : graphWithInvalidFlight.getNodes().values()) {
      assertTrue(node.getOutgoingEdges().isEmpty());
    }
  }

  @Test
  void testMultipleFlightsFromSameOrigin() {
    List<Flight> multipleFlights = new ArrayList<>();
    multipleFlights.add(
        new Flight(1, "VIE", "JFK", "Airline1", "F1", 480, 450.0, LocalTime.of(10, 35)));
    multipleFlights.add(
        new Flight(2, "VIE", "LHR", "Airline2", "F2", 90, 120.0, LocalTime.of(14, 20)));
    multipleFlights.add(
        new Flight(3, "VIE", "CDG", "Airline3", "F3", 120, 150.0, LocalTime.of(16, 0)));

    Graph multiFlightGraph = new Graph(airports, multipleFlights);

    List<Edge> vieEdges = multiFlightGraph.getOutgoingEdges("VIE");
    assertEquals(3, vieEdges.size());
  }

  @Test
  void testGraphWithSingleAirport() {
    List<Airport> singleAirport =
        List.of(new Airport(1, "VIE", "Vienna", "Austria", 48.1103, 16.5697));
    List<Flight> noFlights = Collections.emptyList();

    Graph singleAirportGraph = new Graph(singleAirport, noFlights);

    assertEquals(1, singleAirportGraph.getNodes().size());
    assertTrue(singleAirportGraph.getNodes().containsKey("VIE"));
    assertTrue(singleAirportGraph.getOutgoingEdges("VIE").isEmpty());
  }

  @Test
  void testGraphWithSingleFlight() {
    List<Airport> twoAirports =
        List.of(
            new Airport(1, "VIE", "Vienna", "Austria", 48.1103, 16.5697),
            new Airport(2, "JFK", "New York", "United States", 40.6413, -73.7781));
    List<Flight> singleFlight =
        List.of(
            new Flight(1, "VIE", "JFK", "Test Airline", "TA1", 480, 450.0, LocalTime.of(10, 35)));

    Graph singleFlightGraph = new Graph(twoAirports, singleFlight);

    assertEquals(2, singleFlightGraph.getNodes().size());
    assertEquals(1, singleFlightGraph.getOutgoingEdges("VIE").size());
    assertTrue(singleFlightGraph.getOutgoingEdges("JFK").isEmpty());
  }
}
