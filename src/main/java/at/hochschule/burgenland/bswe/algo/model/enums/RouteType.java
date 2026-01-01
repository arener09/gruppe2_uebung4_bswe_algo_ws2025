/**
 * ----------------------------------------------------------------------------- File:
 * RouteType.java Package: at.hochschule.burgenland.bswe.algo.model.enums Authors: Alexander R.
 * Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.model.enums;

/** Defines optimization modes for route calculation. */
public enum RouteType {
  /** Finds the route with the lowest total ticket price. */
  CHEAPEST,

  /** Finds the route with the shortest total travel duration. */
  FASTEST,

  /** Finds the route with the fewest stopovers (least number of flights). */
  FEWEST_STOPOVERS,

  /** Finds the route with the longest total travel duration (slowest). */
  SLOWEST
}
