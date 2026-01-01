/**
 * ----------------------------------------------------------------------------- File:
 * RoutingCalculator.java Package: at.hochschule.burgenland.bswe.algo.algorithm Authors: Alexander
 * R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.algorithm;

import at.hochschule.burgenland.bswe.algo.algorithm.path.BreadthFirstSearchAlgorithm;
import at.hochschule.burgenland.bswe.algo.algorithm.path.DijkstraAlgorithm;
import at.hochschule.burgenland.bswe.algo.model.Route;
import at.hochschule.burgenland.bswe.algo.model.enums.RouteType;
import at.hochschule.burgenland.bswe.algo.structure.Edge;
import at.hochschule.burgenland.bswe.algo.structure.Graph;
import lombok.extern.log4j.Log4j2;

/**
 * High-level service class providing a unified entry point for route calculation. Uses different
 * algorithms depending on the optimization criterion.
 */
@Log4j2
public class RoutingCalculator {

  private final DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
  private final BreadthFirstSearchAlgorithm bfs = new BreadthFirstSearchAlgorithm();

  /**
   * Delegates to the correct algorithm based on {@link RouteType}.
   *
   * @param graph the flight graph containing all airports and flights
   * @param origin IATA code of the starting airport
   * @param destination IATA code of the destination airport
   * @param type the route optimization strategy
   * @return the calculated {@link Route}, or {@code null} if no route exists
   */
  public Route findRoute(Graph graph, String origin, String destination, RouteType type) {
    return switch (type) {
      case CHEAPEST -> findCheapestRoute(graph, origin, destination);
      case FASTEST -> findFastestRoute(graph, origin, destination);
      case FEWEST_STOPOVERS -> findFewestStopovers(graph, origin, destination);
      case SLOWEST -> findSlowestRoute(graph, origin, destination);
    };
  }

  /**
   * Finds the cheapest route between two airports using Dijkstra's algorithm.
   *
   * @param graph the flight graph containing all airports and flights
   * @param origin IATA code of the starting airport
   * @param destination IATA code of the destination airport
   * @return the cheapest {@link Route} between the two airports, or {@code null} if none exists
   */
  private Route findCheapestRoute(Graph graph, String origin, String destination) {
    log.info("Calculating cheapest route from {} to {}", origin, destination);
    return dijkstra.findRoute(graph, origin, destination, Edge::getPrice);
  }

  /**
   * Finds the fastest route between two airports using Dijkstra's algorithm.
   *
   * @param graph the flight graph containing all airports and flights
   * @param origin IATA code of the starting airport
   * @param destination IATA code of the destination airport
   * @return the fastest {@link Route} between the two airports, or {@code null} if none exists
   */
  private Route findFastestRoute(Graph graph, String origin, String destination) {
    log.info("Calculating fastest route from {} to {}", origin, destination);
    return dijkstra.findRoute(graph, origin, destination, Edge::getDuration);
  }

  /**
   * Finds the route with the fewest stopovers using Breadth-First Search.
   *
   * @param graph the flight graph containing all airports and flights
   * @param origin IATA code of the starting airport
   * @param destination IATA code of the destination airport
   * @return the {@link Route} with the fewest stopovers, or {@code null} if none exists
   */
  private Route findFewestStopovers(Graph graph, String origin, String destination) {
    log.info("Calculating route with fewest stopovers from {} to {}", origin, destination);
    return bfs.findRoute(graph, origin, destination);
  }

  /**
   * Finds the slowest route between two airports using a modified Dijkstra's algorithm.
   *
   * <p>This method finds the route with the longest total travel duration by negating the duration
   * values and using Dijkstra's algorithm to find the "shortest" path in the negated graph, which
   * corresponds to the longest path in the original graph.
   *
   * @param graph the flight graph containing all airports and flights
   * @param origin IATA code of the starting airport
   * @param destination IATA code of the destination airport
   * @return the slowest {@link Route} between the two airports, or {@code null} if none exists
   */
  private Route findSlowestRoute(Graph graph, String origin, String destination) {
    log.info("Calculating slowest route from {} to {}", origin, destination);
    // Use negated duration to find the longest path (Dijkstra finds shortest, so negating finds longest)
    return dijkstra.findRoute(graph, origin, destination, edge -> -edge.getDuration());
  }
}
