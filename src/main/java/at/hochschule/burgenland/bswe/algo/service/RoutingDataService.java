/**
 * ----------------------------------------------------------------------------- File:
 * RoutingDataService.java Package: at.hochschule.burgenland.bswe.algo.service Authors: Alexander R.
 * Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.service;

import at.hochschule.burgenland.bswe.algo.model.Airport;
import at.hochschule.burgenland.bswe.algo.model.Flight;
import at.hochschule.burgenland.bswe.algo.model.Route;
import at.hochschule.burgenland.bswe.algo.repository.AirportRepository;
import at.hochschule.burgenland.bswe.algo.repository.FlightRepository;
import at.hochschule.burgenland.bswe.algo.repository.RouteRepository;
import at.hochschule.burgenland.bswe.algo.structure.Graph;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/** Provides static methods for initializing and accessing all routing-related data. */
@Log4j2
public final class RoutingDataService {

  private static final AirportRepository airportRepository = new AirportRepository();
  private static final FlightRepository flightRepository = new FlightRepository();
  private static final RouteRepository routeRepository = new RouteRepository();

  /** List of all loaded airports. */
  @Getter private static List<Airport> airports = Collections.emptyList();

  /** List of all loaded flights. */
  @Getter private static List<Flight> flights = Collections.emptyList();

  /** List of all loaded routes. */
  @Getter private static List<Route> routes = Collections.emptyList();

  /**
   * Builds and returns a graph representation of all airports and flights.
   *
   * @return Graph instance containing all airports and flights
   * @throws IllegalStateException if data could not be loaded or is incomplete
   */
  public static Graph buildGraph() {
    loadAllData();
    if (airports.isEmpty() || flights.isEmpty()) {
      throw new IllegalStateException(
          "Cannot build graph before loading airports and flights. Call loadAllData() first.");
    }

    return new Graph(airports, flights);
  }

  /**
   * Saves all currently loaded routes to the routes.csv file.
   *
   * @param updatedRoutes list of routes to persist
   */
  public static void saveAllRoutes(List<Route> updatedRoutes) {
    if (updatedRoutes == null || updatedRoutes.isEmpty()) {
      log.warn("Attempted to save empty route list — operation skipped.");
      return;
    }

    log.info("Saving {} routes to CSV...", updatedRoutes.size());
    routeRepository.saveRoutes(updatedRoutes);
    loadAllData();
  }

  /** Loads all datasets (airports, flights, and routes) from their respective CSV files. */
  private static void loadAllData() {
    try {
      airports = airportRepository.loadAirports();
      flights = flightRepository.loadFlights();
      routes = routeRepository.loadRoutes();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load routing data: " + e.getMessage(), e);
    }
  }
}
