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
import java.time.LocalTime;
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

  /**
   * Maximum number of stopovers allowed (3 stopovers = 4 flights maximum).
   */
  protected static final int MAX_STOPOVERS = 3;
  protected static final int MAX_FLIGHTS = MAX_STOPOVERS + 1;

  /**
   * Minimum connection time required for a stopover in minutes.
   */
  protected static final int MIN_CONNECTION_TIME_MINUTES = 20;

  /**
   * Calculates the arrival time of a flight.
   *
   * @param flightEdge the flight edge
   * @return the arrival time (departure time + duration in minutes)
   */
  protected LocalTime calculateArrivalTime(Edge flightEdge) {
    LocalTime departureTime = flightEdge.getFlight().getDepartureTime();
    return departureTime.plusMinutes(flightEdge.getDuration());
  }

  /**
   * Validates if there is sufficient connection time (at least 20 minutes) between two consecutive
   * flights.
   *
   * @param previousFlight the previous flight edge
   * @param nextFlight the next flight edge
   * @return true if the connection time is valid (at least 20 minutes), false otherwise
   */
  protected boolean isValidConnection(Edge previousFlight, Edge nextFlight) {
    LocalTime arrivalTime = calculateArrivalTime(previousFlight);
    LocalTime nextDepartureTime = nextFlight.getFlight().getDepartureTime();

    // Calculate connection time in minutes
    long connectionTimeMinutes;
    if (nextDepartureTime.isBefore(arrivalTime) || nextDepartureTime.equals(arrivalTime)) {
      // Next day scenario: calculate time until midnight + time from midnight
      long minutesUntilMidnight = java.time.Duration.between(arrivalTime, LocalTime.MAX).toMinutes() + 1;
      long minutesFromMidnight = nextDepartureTime.toSecondOfDay() / 60;
      connectionTimeMinutes = minutesUntilMidnight + minutesFromMidnight;
    } else {
      connectionTimeMinutes = java.time.Duration.between(arrivalTime, nextDepartureTime).toMinutes();
    }

    return connectionTimeMinutes >= MIN_CONNECTION_TIME_MINUTES;
  }

  /**
   * Validates if a path of edges has at most the maximum allowed number of flights.
   *
   * @param edges list of flight edges
   * @return true if the path has at most MAX_FLIGHTS flights, false otherwise
   */
  protected boolean isValidFlightCount(List<Edge> edges) {
    return edges != null && edges.size() <= MAX_FLIGHTS;
  }

  /**
   * Validates if all consecutive flights in a path have valid connection times.
   *
   * @param edges list of flight edges in order
   * @return true if all connections are valid, false otherwise
   */
  protected boolean areAllConnectionsValid(List<Edge> edges) {
    if (edges == null || edges.size() <= 1) {
      return true; // Single flight or empty path has no connections
    }

    for (int i = 0; i < edges.size() - 1; i++) {
      Edge currentFlight = edges.get(i);
      Edge nextFlight = edges.get(i + 1);

      if (!isValidConnection(currentFlight, nextFlight)) {
        log.debug(
            "Invalid connection: Flight {} arrives at {} but flight {} departs at {} (requires at least {} minutes)",
            currentFlight.getFlight().getId(),
            calculateArrivalTime(currentFlight),
            nextFlight.getFlight().getId(),
            nextFlight.getFlight().getDepartureTime(),
            MIN_CONNECTION_TIME_MINUTES);
        return false;
      }
    }

    return true;
  }
}
