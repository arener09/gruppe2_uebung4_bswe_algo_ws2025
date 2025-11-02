/**
 * ----------------------------------------------------------------------------- File:
 * RouteCombinedComparator.java Package: at.hochschule.burgenland.bswe.algo.model.comparator
 * Authors: Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.model.comparator;

import at.hochschule.burgenland.bswe.algo.model.Route;
import java.util.Comparator;

/**
 * Combined comparator for sorting routes by multiple criteria in sequence: 1. First by price
 * (ascending - cheapest first) 2. If prices are equal, then by duration (ascending - fastest first)
 * 3. If durations are also equal, then by stopovers (ascending - fewest first)
 */
public class RouteCombinedComparator implements Comparator<Route> {

  @Override
  public int compare(Route firstRoute, Route secondRoute) {
    if (firstRoute == null && secondRoute == null) {
      return 0;
    }
    if (firstRoute == null) {
      return 1; // null routes go to the end
    }
    if (secondRoute == null) {
      return -1; // null routes go to the end
    }

    // First comparison: by price
    int priceComparison = Double.compare(firstRoute.getTotalPrice(), secondRoute.getTotalPrice());
    if (priceComparison != 0) {
      return priceComparison;
    }

    // Second comparison: by duration (if prices are equal)
    int durationComparison =
        Integer.compare(firstRoute.getTotalDuration(), secondRoute.getTotalDuration());
    if (durationComparison != 0) {
      return durationComparison;
    }

    // Third comparison: by stopovers (if prices and durations are equal)
    return Integer.compare(firstRoute.getStopovers(), secondRoute.getStopovers());
  }
}
