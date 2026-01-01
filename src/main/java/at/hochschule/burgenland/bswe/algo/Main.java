/**
 * ----------------------------------------------------------------------------- File: Main.java
 * Package: at.hochschule.burgenland.bswe.algo
 * Authors: Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo;

import at.hochschule.burgenland.bswe.algo.algorithm.RoutingCalculator;
import at.hochschule.burgenland.bswe.algo.model.Route;
import at.hochschule.burgenland.bswe.algo.model.enums.RouteType;
import at.hochschule.burgenland.bswe.algo.service.RoutingDataService;
import at.hochschule.burgenland.bswe.algo.structure.Graph;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/** Main class to demonstrate route calculation. */
@Log4j2
public class Main {

  public static void main(String[] args) {
    try {
      Graph graph = RoutingDataService.buildGraph();
      log.info("Graph successfully built with {} airports.", graph.getNodes().size());

      RoutingCalculator calculator = new RoutingCalculator();

      String origin = "VIE"; // Vienna
      String destination = "JFK"; // New York

      log.info("Starting route calculations from {} to {}", origin, destination);

      List<Route> routes = new ArrayList<>();

      Route cheapest = calculator.findRoute(graph, origin, destination, RouteType.CHEAPEST);
      routes.add(cheapest);
      Route fastest = calculator.findRoute(graph, origin, destination, RouteType.FASTEST);
      routes.add(fastest);
      Route fewestStops = calculator.findRoute(graph, origin, destination, RouteType.FEWEST_STOPOVERS);
      routes.add(fewestStops);


      RoutingDataService.saveAllRoutes(routes);

      printRoute("Cheapest Route", cheapest);
      printRoute("Fastest Route", fastest);
      printRoute("Fewest Stopovers", fewestStops);

    } catch (Exception e) {
      log.error("An error occurred while calculating routes: {}", e.getMessage(), e);
    }
  }

  /** Utility to print a route result in a human-readable way. */
  private static void printRoute(String title, Route route) {
    System.out.println("\n--- " + title + " ---");
    if (route == null) {
      System.out.println("No route found.");
      return;
    }
    System.out.printf(
        "Flights: %s%nDuration: %d min%nPrice: €%.2f%nStopovers: %d%n",
        route.getFlights(), route.getTotalDuration(), route.getTotalPrice(), route.getStopovers());
  }
}
