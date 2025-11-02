/**
 * ----------------------------------------------------------------------------- File:
 * RoutePriceComparator.java Package: at.hochschule.burgenland.bswe.algo.model.comparator Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.model.comparator;

import at.hochschule.burgenland.bswe.algo.model.Route;
import java.util.Comparator;

/**
 * Comparator for sorting routes by price in ascending order (cheapest first).
 *
 * <p>Routes with null values are considered greater than non-null routes and will be sorted to the
 * end of the collection.
 */
public class RoutePriceComparator implements Comparator<Route> {

  /**
   * Compares two routes based on their total price in ascending order.
   *
   * <p>Returns a negative integer if the first route is cheaper, zero if both routes have the same
   * price, or a positive integer if the first route is more expensive.
   *
   * @param firstRoute the first route to be compared
   * @param secondRoute the second route to be compared
   * @return a negative integer, zero, or a positive integer as the first route's price is less
   *     than, equal to, or greater than the second route's price
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

    return Double.compare(firstRoute.getTotalPrice(), secondRoute.getTotalPrice());
  }
}
