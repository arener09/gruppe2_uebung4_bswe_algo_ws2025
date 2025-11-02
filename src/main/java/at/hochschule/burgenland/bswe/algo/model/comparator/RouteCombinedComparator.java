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
 * Combined comparator for sorting routes by multiple criteria in sequence.
 *
 * <p>The comparison is performed in the following order:
 *
 * <ol>
 *   <li>First by price (ascending - cheapest first)
 *   <li>If prices are equal, then by duration (ascending - fastest first)
 *   <li>If durations are also equal, then by stopovers (ascending - fewest first)
 * </ol>
 *
 * <p>Routes with null values are considered greater than non-null routes and will be sorted to the
 * end of the collection.
 */
public class RouteCombinedComparator implements Comparator<Route> {

  /**
   * Compares two routes based on multiple criteria in sequence: price, duration, and stopovers.
   *
   * <p>The comparison first checks the total price. If the prices differ, the result is returned
   * immediately. If prices are equal, the total duration is compared. If durations are also equal,
   * the number of stopovers is compared as the final criterion.
   *
   * @param firstRoute the first route to be compared
   * @param secondRoute the second route to be compared
   * @return a negative integer, zero, or a positive integer as the first route is less than, equal
   *     to, or greater than the second route based on the combined criteria
   */
  @Override
  public int compare(Route firstRoute, Route secondRoute) {
    if (firstRoute == null && secondRoute == null) {
      return 0;
    }
    if (firstRoute == null) {
      return 1;
    }
    if (secondRoute == null) {
      return -1;
    }

    int priceComparison = Double.compare(firstRoute.getTotalPrice(), secondRoute.getTotalPrice());
    if (priceComparison != 0) {
      return priceComparison;
    }

    int durationComparison =
        Integer.compare(firstRoute.getTotalDuration(), secondRoute.getTotalDuration());
    if (durationComparison != 0) {
      return durationComparison;
    }

    return Integer.compare(firstRoute.getStopovers(), secondRoute.getStopovers());
  }
}
