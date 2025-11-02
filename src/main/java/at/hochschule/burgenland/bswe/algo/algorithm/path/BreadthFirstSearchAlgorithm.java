/**
 * ----------------------------------------------------------------------------- File:
 * BreadthFirstSearchAlgorithm.java Package: at.hochschule.burgenland.bswe.algo.algorithm.path
 * Authors: Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.algorithm.path;

import at.hochschule.burgenland.bswe.algo.algorithm.path.base.PathFindingAlgorithm;
import at.hochschule.burgenland.bswe.algo.model.Route;
import at.hochschule.burgenland.bswe.algo.structure.Edge;
import at.hochschule.burgenland.bswe.algo.structure.Graph;
import java.util.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BreadthFirstSearchAlgorithm extends PathFindingAlgorithm {

  /**
   * Finds the route between two airports with the fewest stopovers.
   *
   * @param graph the flight graph containing all airports and flights
   * @param origin IATA code of the origin airport
   * @param destination IATA code of the destination airport
   * @return a Route object representing the path with the fewest stopovers, or {@code null} if no
   *     route exists
   */
  public Route findRoute(Graph graph, String origin, String destination) {
    if (!validateNodes(graph, origin, destination)) {
      return null;
    }

    Queue<List<Edge>> queue = new LinkedList<>();

    // Initialize queue with direct flights from origin
    for (Edge edge : graph.getOutgoingEdges(origin)) {
      List<Edge> path = Collections.singletonList(edge);
      // Validate single flight
      if (isValidFlightCount(path)) {
        queue.add(path);
      }
    }

    Set<String> visited = new HashSet<>();
    visited.add(origin);

    while (!queue.isEmpty()) {
      List<Edge> path = queue.poll();
      Edge lastEdge = path.get(path.size() - 1);
      String currentAirport = lastEdge.getDestinationIata();

      if (currentAirport.equals(destination)) {
        // Final validation before returning
        if (areAllConnectionsValid(path)) {
          Route route = buildRouteFromEdges(path);
          log.info(
              "Route found from {} to {} | Stops: {}, Duration: {} min, Price: €{}",
              origin,
              destination,
              route.getStopovers(),
              route.getTotalDuration(),
              route.getTotalPrice());
          return route;
        }
      }

      // Check if we've exceeded max flights
      if (!isValidFlightCount(path)) {
        continue;
      }

      if (!visited.contains(currentAirport)) {
        visited.add(currentAirport);
        for (Edge nextEdge : graph.getOutgoingEdges(currentAirport)) {
          if (visited.contains(nextEdge.getDestinationIata())) continue;

          List<Edge> newPath = new ArrayList<>(path);
          newPath.add(nextEdge);

          // Validate: check flight count and connection times
          if (!isValidFlightCount(newPath)) {
            continue; // Skip if too many flights
          }

          if (!areAllConnectionsValid(newPath)) {
            continue; // Skip if connection times are invalid
          }

          queue.add(newPath);
        }
      }
    }

    log.warn("No route found from {} to {}", origin, destination);
    return null;
  }
}
