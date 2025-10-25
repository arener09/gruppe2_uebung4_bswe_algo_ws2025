/**
 * ----------------------------------------------------------------------------- File: NodeTest.java
 * Package: at.hochschule.burgenland.bswe.algo.structure Authors: Alexander R. Brenner, Raja
 * Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.structure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NodeTest {

  private Node node;

  @BeforeEach
  void setUp() {
    node = new Node();
  }

  @Test
  void testDefaultConstructor() {
    assertNotNull(node);
    assertNull(node.getIata());
    assertNotNull(node.getOutgoingEdges());
    assertTrue(node.getOutgoingEdges().isEmpty());
  }

  @Test
  void testAllArgsConstructor() {
    List<Edge> edges = new ArrayList<>();
    edges.add(new Edge("JFK", 480, 450.0, null));
    edges.add(new Edge("LHR", 90, 120.0, null));

    Node testNode = new Node("VIE", edges);

    assertEquals("VIE", testNode.getIata());
    assertEquals(2, testNode.getOutgoingEdges().size());
    assertEquals(edges, testNode.getOutgoingEdges());
  }

  @Test
  void testSettersAndGetters() {
    List<Edge> edges = new ArrayList<>();
    edges.add(new Edge("CDG", 120, 200.0, null));

    node.setIata("LHR");
    node.setOutgoingEdges(edges);

    assertEquals("LHR", node.getIata());
    assertEquals(edges, node.getOutgoingEdges());
    assertEquals(1, node.getOutgoingEdges().size());
  }

  @Test
  void testEqualsAndHashCode() {
    List<Edge> edges1 = new ArrayList<>();
    edges1.add(new Edge("JFK", 480, 450.0, null));

    List<Edge> edges2 = new ArrayList<>();
    edges2.add(new Edge("JFK", 480, 450.0, null));

    Node node1 = new Node("VIE", edges1);
    Node node2 = new Node("VIE", edges2);
    Node node3 = new Node("LHR", edges1);

    assertEquals(node1, node2);
    assertNotEquals(node1, node3);
    assertEquals(node1.hashCode(), node2.hashCode());
    assertNotEquals(node1.hashCode(), node3.hashCode());
  }

  @Test
  void testToString() {
    node.setIata("VIE");
    List<Edge> edges = new ArrayList<>();
    edges.add(new Edge("JFK", 480, 450.0, null));
    node.setOutgoingEdges(edges);

    String result = node.toString();
    assertTrue(result.contains("Node"));
    assertTrue(result.contains("iata=VIE"));
  }

  @Test
  void testNullIata() {
    node.setIata(null);
    assertNull(node.getIata());
  }

  @Test
  void testEmptyIata() {
    node.setIata("");
    assertEquals("", node.getIata());
  }

  @Test
  void testNullOutgoingEdges() {
    node.setOutgoingEdges(null);
    assertNull(node.getOutgoingEdges());
  }

  @Test
  void testEmptyOutgoingEdges() {
    node.setOutgoingEdges(new ArrayList<>());
    assertNotNull(node.getOutgoingEdges());
    assertTrue(node.getOutgoingEdges().isEmpty());
  }

  @Test
  void testMultipleEdges() {
    List<Edge> edges = new ArrayList<>();
    edges.add(new Edge("JFK", 480, 450.0, null));
    edges.add(new Edge("LHR", 90, 120.0, null));
    edges.add(new Edge("CDG", 120, 200.0, null));

    node.setIata("VIE");
    node.setOutgoingEdges(edges);

    assertEquals(3, node.getOutgoingEdges().size());
    assertTrue(node.getOutgoingEdges().contains(edges.get(0)));
    assertTrue(node.getOutgoingEdges().contains(edges.get(1)));
    assertTrue(node.getOutgoingEdges().contains(edges.get(2)));
  }

  @Test
  void testEdgeModification() {
    List<Edge> edges = new ArrayList<>();
    edges.add(new Edge("JFK", 480, 450.0, null));

    node.setIata("VIE");
    node.setOutgoingEdges(edges);

    // Modify the original list
    edges.add(new Edge("LHR", 90, 120.0, null));

    // The node's edges should reflect the modification
    assertEquals(2, node.getOutgoingEdges().size());
  }
}
