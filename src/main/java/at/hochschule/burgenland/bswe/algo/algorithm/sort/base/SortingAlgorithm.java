/**
 * ----------------------------------------------------------------------------- File:
 * SortingAlgorithm.java Package: at.hochschule.burgenland.bswe.algo.algorithm.sort.base Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.algorithm.sort.base;

import at.hochschule.burgenland.bswe.algo.model.Route;
import java.util.Comparator;
import java.util.List;

/**
 * Abstract base class for sorting algorithms that work on Route objects.
 *
 * <p>All sorting algorithms must implement the sort method which takes a list of routes and a
 * comparator to determine the sort order.
 */
public abstract class SortingAlgorithm {

  /**
   * Sorts a list of routes in-place using the specified comparator.
   *
   * @param routes the list of routes to sort (will be modified)
   * @param comparator the comparator to determine the sort order
   */
  public abstract void sort(List<Route> routes, Comparator<Route> comparator);

  /**
   * Helper method to swap two elements in a list.
   *
   * @param routes the list containing the elements
   * @param firstIndex index of the first element
   * @param secondIndex index of the second element
   */
  protected void swap(List<Route> routes, int firstIndex, int secondIndex) {
    Route temporaryRoute = routes.get(firstIndex);
    routes.set(firstIndex, routes.get(secondIndex));
    routes.set(secondIndex, temporaryRoute);
  }
}
