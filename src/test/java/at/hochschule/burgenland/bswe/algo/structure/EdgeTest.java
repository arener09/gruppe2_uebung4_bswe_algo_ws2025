/**
 * ----------------------------------------------------------------------------- File: EdgeTest.java
 * Package: at.hochschule.burgenland.bswe.algo.structure Authors: Alexander R. Brenner, Raja
 * Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.structure;

import static org.junit.jupiter.api.Assertions.*;

import at.hochschule.burgenland.bswe.algo.model.Flight;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EdgeTest {

  private Edge edge;
  private Flight flight;

  @BeforeEach
  void setUp() {
    edge = new Edge();
    flight =
        new Flight(1, "VIE", "JFK", "Austrian Airlines", "OS35", 480, 450.0, LocalTime.of(10, 35));
  }

  @Test
  void testDefaultConstructor() {
    assertNotNull(edge);
    assertNull(edge.getDestinationIata());
    assertEquals(0, edge.getDuration());
    assertEquals(0.0, edge.getPrice());
    assertNull(edge.getFlight());
  }

  @Test
  void testAllArgsConstructor() {
    Edge testEdge = new Edge("JFK", 480, 450.0, flight);

    assertEquals("JFK", testEdge.getDestinationIata());
    assertEquals(480, testEdge.getDuration());
    assertEquals(450.0, testEdge.getPrice());
    assertEquals(flight, testEdge.getFlight());
  }

  @Test
  void testSettersAndGetters() {
    edge.setDestinationIata("LHR");
    edge.setDuration(90);
    edge.setPrice(120.50);
    edge.setFlight(flight);

    assertEquals("LHR", edge.getDestinationIata());
    assertEquals(90, edge.getDuration());
    assertEquals(120.50, edge.getPrice());
    assertEquals(flight, edge.getFlight());
  }

  @Test
  void testEqualsAndHashCode() {
    Edge edge1 = new Edge("JFK", 480, 450.0, flight);
    Edge edge2 = new Edge("JFK", 480, 450.0, flight);
    Edge edge3 = new Edge("LHR", 90, 120.0, flight);

    assertEquals(edge1, edge2);
    assertNotEquals(edge1, edge3);
    assertEquals(edge1.hashCode(), edge2.hashCode());
    assertNotEquals(edge1.hashCode(), edge3.hashCode());
  }

  @Test
  void testToString() {
    edge.setDestinationIata("JFK");
    edge.setDuration(480);
    edge.setPrice(450.0);
    edge.setFlight(flight);

    String result = edge.toString();
    assertTrue(result.contains("Edge"));
    assertTrue(result.contains("destinationIata=JFK"));
    assertTrue(result.contains("duration=480"));
    assertTrue(result.contains("price=450.0"));
  }

  @Test
  void testNullDestinationIata() {
    edge.setDestinationIata(null);
    assertNull(edge.getDestinationIata());
  }

  @Test
  void testEmptyDestinationIata() {
    edge.setDestinationIata("");
    assertEquals("", edge.getDestinationIata());
  }

  @Test
  void testNegativeDuration() {
    edge.setDuration(-10);
    assertEquals(-10, edge.getDuration());
  }

  @Test
  void testNegativePrice() {
    edge.setPrice(-50.0);
    assertEquals(-50.0, edge.getPrice());
  }

  @Test
  void testZeroValues() {
    edge.setDuration(0);
    edge.setPrice(0.0);

    assertEquals(0, edge.getDuration());
    assertEquals(0.0, edge.getPrice());
  }

  @Test
  void testNullFlight() {
    edge.setFlight(null);
    assertNull(edge.getFlight());
  }

  @Test
  void testLargeValues() {
    edge.setDuration(Integer.MAX_VALUE);
    edge.setPrice(Double.MAX_VALUE);

    assertEquals(Integer.MAX_VALUE, edge.getDuration());
    assertEquals(Double.MAX_VALUE, edge.getPrice());
  }

  @Test
  void testPrecisionInPrice() {
    edge.setPrice(123.456789);
    assertEquals(123.456789, edge.getPrice());
  }

  @Test
  void testEdgeWithDifferentFlight() {
    Flight flight1 =
        new Flight(1, "VIE", "JFK", "Austrian Airlines", "OS35", 480, 450.0, LocalTime.of(10, 35));
    Flight flight2 =
        new Flight(2, "VIE", "JFK", "Austrian Airlines", "OS36", 485, 455.0, LocalTime.of(11, 35));

    Edge edge1 = new Edge("JFK", 480, 450.0, flight1);
    Edge edge2 = new Edge("JFK", 480, 450.0, flight2);

    // Edges with same properties but different flight objects should not be equal
    assertNotEquals(edge1, edge2);
    assertNotEquals(edge1.hashCode(), edge2.hashCode());
  }

  @Test
  void testEdgeWithSameFlight() {
    Edge edge1 = new Edge("JFK", 480, 450.0, flight);
    Edge edge2 = new Edge("JFK", 480, 450.0, flight);

    assertEquals(edge1, edge2);
    assertEquals(edge1.hashCode(), edge2.hashCode());
  }
}
