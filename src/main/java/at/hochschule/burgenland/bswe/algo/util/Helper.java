package at.hochschule.burgenland.bswe.algo.util;

import at.hochschule.burgenland.bswe.algo.model.Route;

public class Helper {
  /** Utility to print a route result in a human-readable way. */
  public static void printRoute(String title, Route route) {
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
