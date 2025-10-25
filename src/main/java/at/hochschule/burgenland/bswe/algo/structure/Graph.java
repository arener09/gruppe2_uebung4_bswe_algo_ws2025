/**
 * ----------------------------------------------------------------------------- File: Graph.java
 * Package: at.hochschule.burgenland.bswe.algo.structure Authors: Alexander R. Brenner, Raja
 * Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.structure;

import at.hochschule.burgenland.bswe.algo.model.Airport;
import at.hochschule.burgenland.bswe.algo.model.Flight;
import java.util.*;
import lombok.Getter;

/** Graph representation connecting airports via flights. */
@Getter
public class Graph {

  /** Map of airport IATA codes to their corresponding graph nodes. */
  private final Map<String, Node> nodes = new HashMap<>();

  /**
   * Builds the graph structure from lists of airports and flights.
   *
   * @param airports list of all airports
   * @param flights list of all flights
   */
  public Graph(List<Airport> airports, List<Flight> flights) {
    // Create nodes for all airports
    for (Airport airport : airports) {
      nodes.put(airport.getIata(), new Node(airport.getIata(), new ArrayList<>()));
    }

    // Add edges for each flight
    for (Flight flight : flights) {
      Node originNode = nodes.get(flight.getOrigin());
      if (originNode != null) {
        originNode
            .getOutgoingEdges()
            .add(
                new Edge(flight.getDestination(), flight.getDuration(), flight.getPrice(), flight));
      }
    }
  }

  /**
   * Returns all flights departing from the given airport.
   *
   * @param iata airport IATA code
   * @return list of outgoing edges or empty list if none exist
   */
  public List<Edge> getOutgoingEdges(String iata) {
    Node node = nodes.get(iata);
    return node != null ? node.getOutgoingEdges() : Collections.emptyList();
  }
}
