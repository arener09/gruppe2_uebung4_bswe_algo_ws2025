/**
 * ----------------------------------------------------------------------------- File:
 * DijkstraAlgorithm.java Package: at.hochschule.burgenland.bswe.algo.algorithm.path Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.algorithm.path;

import at.hochschule.burgenland.bswe.algo.algorithm.path.base.PathFindingAlgorithm;
import at.hochschule.burgenland.bswe.algo.model.Route;
import at.hochschule.burgenland.bswe.algo.structure.Edge;
import at.hochschule.burgenland.bswe.algo.structure.Graph;
import java.util.*;
import java.util.function.ToDoubleFunction;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DijkstraAlgorithm extends PathFindingAlgorithm {

  /**
   * Calculates the shortest path between two airports.
   *
   * @param graph the flight graph containing all airports and flights
   * @param origin IATA code of the origin airport
   * @param destination IATA code of the destination airport
   * @param weightFunc function to determine the weight of each edge (e.g. {@code Edge::getPrice})
   * @return a Route object representing the optimal path, or {@code null} if no route exists
   */
  public Route findRoute(
      Graph graph, String origin, String destination, ToDoubleFunction<Edge> weightFunc) {

    if (!validateNodes(graph, origin, destination)) {
      return null;
    }

    Map<String, Double> distance = new HashMap<>();
    Map<String, String> predecessor = new HashMap<>();
    Map<String, Edge> incomingEdge = new HashMap<>();
    Set<String> visited = new HashSet<>();

    // Initialize distances
    for (String iata : graph.getNodes().keySet()) {
      distance.put(iata, Double.POSITIVE_INFINITY);
    }
    distance.put(origin, 0.0);

    PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));
    queue.add(origin);

    while (!queue.isEmpty()) {
      String current = queue.poll();
      if (visited.contains(current)) continue;
      visited.add(current);

      // Stop early if destination reached
      if (current.equals(destination)) break;

      for (Edge edge : graph.getOutgoingEdges(current)) {
        String neighbor = edge.getDestinationIata();
        if (visited.contains(neighbor)) continue;

        double newDist = distance.get(current) + weightFunc.applyAsDouble(edge);
        if (newDist < distance.get(neighbor)) {
          distance.put(neighbor, newDist);
          predecessor.put(neighbor, current);
          incomingEdge.put(neighbor, edge);
          queue.add(neighbor);
        }
      }
    }

    // No path found
    if (!predecessor.containsKey(destination) && !origin.equals(destination)) {
      log.warn("No Route found from {} to {}", origin, destination);
      return null;
    }

    // Reconstruct edge path
    List<Edge> path = new ArrayList<>();
    String current = destination;
    while (current != null && incomingEdge.containsKey(current)) {
      path.add(0, incomingEdge.get(current));
      current = predecessor.get(current);
    }

    Route route = buildRouteFromEdges(path);
    log.info(
        "Route found from {} to {} | Stops: {}, Duration: {} min, Price: €{}",
        origin,
        destination,
        route != null ? route.getStopovers() : "-",
        route != null ? route.getTotalDuration() : "-",
        route != null ? route.getTotalPrice() : "-");
    return route;
  }
}
