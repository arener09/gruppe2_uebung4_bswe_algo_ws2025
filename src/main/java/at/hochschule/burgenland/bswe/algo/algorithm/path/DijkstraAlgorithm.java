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

    // Store the path to each node for validation
    Map<String, List<Edge>> paths = new HashMap<>();
    Map<String, Double> distance = new HashMap<>();
    Set<String> visited = new HashSet<>();

    // Initialize distances and paths
    for (String iata : graph.getNodes().keySet()) {
      distance.put(iata, Double.POSITIVE_INFINITY);
      paths.put(iata, new ArrayList<>());
    }
    distance.put(origin, 0.0);
    paths.put(origin, new ArrayList<>());

    PriorityQueue<String> queue =
        new PriorityQueue<>(Comparator.comparingDouble(distance::get));
    queue.add(origin);

    while (!queue.isEmpty()) {
      String current = queue.poll();
      if (visited.contains(current)) continue;
      visited.add(current);

      // Stop early if destination reached
      if (current.equals(destination)) break;

      List<Edge> currentPath = paths.get(current);

      // Check if we've exceeded max flights
      if (!isValidFlightCount(currentPath)) {
        continue;
      }

      for (Edge edge : graph.getOutgoingEdges(current)) {
        String neighbor = edge.getDestinationIata();
        if (visited.contains(neighbor)) continue;

        // Build new path with this edge
        List<Edge> newPath = new ArrayList<>(currentPath);
        newPath.add(edge);

        // Validate: check flight count and connection times
        if (!isValidFlightCount(newPath)) {
          continue; // Skip if too many flights
        }

        if (!areAllConnectionsValid(newPath)) {
          continue; // Skip if connection times are invalid
        }

        double newDist = distance.get(current) + weightFunc.applyAsDouble(edge);
        if (newDist < distance.get(neighbor)) {
          distance.put(neighbor, newDist);
          paths.put(neighbor, newPath);
          queue.add(neighbor);
        }
      }
    }

    // Check if we found a path
    List<Edge> finalPath = paths.get(destination);
    if (finalPath == null || finalPath.isEmpty()) {
      if (!origin.equals(destination)) {
        log.warn("No Route found from {} to {}", origin, destination);
      }
      return null;
    }

    Route route = buildRouteFromEdges(finalPath);
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
