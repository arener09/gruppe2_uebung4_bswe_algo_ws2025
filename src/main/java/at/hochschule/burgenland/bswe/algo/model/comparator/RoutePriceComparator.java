/**
 * ----------------------------------------------------------------------------- File:
 * RoutePriceComparator.java Package: at.hochschule.burgenland.bswe.algo.model.comparator Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.model.comparator;

import at.hochschule.burgenland.bswe.algo.model.Route;
import java.util.Comparator;

/** Comparator for sorting routes by price in ascending order (cheapest first). */
public class RoutePriceComparator implements Comparator<Route> {

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

    // Compare by totalPrice (ascending - cheapest first)
    return Double.compare(firstRoute.getTotalPrice(), secondRoute.getTotalPrice());
  }
}
