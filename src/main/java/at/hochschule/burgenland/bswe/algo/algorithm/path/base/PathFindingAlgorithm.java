/**
 * ----------------------------------------------------------------------------- File:
 * BasePathFindingAlgorithm.java Package: at.hochschule.burgenland.bswe.algo.algorithm Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.algorithm.path.base;

import at.hochschule.burgenland.bswe.algo.model.Route;
import at.hochschule.burgenland.bswe.algo.structure.Edge;
import at.hochschule.burgenland.bswe.algo.structure.Graph;
import java.util.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class PathFindingAlgorithm {

  /**
   * Validates whether both the origin and destination airports exist in the graph.
   *
   * @param graph the flight graph
   * @param origin IATA code of the origin airport
   * @param destination IATA code of the destination airport
   * @return true if both airports exist in the graph, false otherwise
   */
  protected boolean validateNodes(Graph graph, String origin, String destination) {
    boolean valid =
        graph.getNodes().containsKey(origin) && graph.getNodes().containsKey(destination);
    if (!valid) {
      log.warn("Invalid IATA codes: {} or {} not found in graph.", origin, destination);
    }
    return valid;
  }

  /**
   * Builds a Route object from a list of edges.
   *
   * @param edges list of edges forming the route
   * @return Route instance
   */
  protected Route buildRouteFromEdges(List<Edge> edges) {
    if (edges == null || edges.isEmpty()) {
      log.warn("Attempted to build route from empty edge list.");
      return null;
    }

    List<Integer> flightIds = new ArrayList<>();
    double totalPrice = 0.0;
    int totalDuration = 0;

    for (Edge edge : edges) {
      flightIds.add(edge.getFlight().getId());
      totalPrice += edge.getPrice();
      totalDuration += edge.getDuration();
    }

    Route route = new Route();
    route.setId(new Random().nextInt(10000));
    route.setFlights(joinFlightIds(flightIds));
    route.setTotalPrice(totalPrice);
    route.setTotalDuration(totalDuration);
    route.setStopovers(Math.max(0, edges.size() - 1));

    log.debug(
        "Built route [{}] | Stops: {}, Duration: {} min, Price: €{}",
        route.getFlights(),
        route.getStopovers(),
        totalDuration,
        totalPrice);

    return route;
  }

  /**
   * Utility to join flight IDs into a dash-separated string.
   *
   * @param ids list of flight IDs
   * @return concatenated string, e.g. "12-45-63"
   */
  protected String joinFlightIds(List<Integer> ids) {
    return String.join("-", ids.stream().map(String::valueOf).toList());
  }
}
