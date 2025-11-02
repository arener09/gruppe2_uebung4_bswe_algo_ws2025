/**
 * ----------------------------------------------------------------------------- File:
 * QuickSortAlgorithm.java Package: at.hochschule.burgenland.bswe.algo.algorithm.sort Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.algorithm.sort;

import at.hochschule.burgenland.bswe.algo.algorithm.sort.base.SortingAlgorithm;
import at.hochschule.burgenland.bswe.algo.model.Route;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import lombok.extern.log4j.Log4j2;

/**
 * Unstable sorting algorithm implementation using Quick Sort.
 *
 * <p>Quick Sort is a divide-and-conquer algorithm that:
 *
 * <ul>
 *   <li>Selects a pivot element
 *   <li>Partitions the list around the pivot (smaller elements to left, larger to right)
 *   <li>Recursively sorts the subarrays
 * </ul>
 *
 * <p><strong>Stability:</strong> Unstable - may change the relative order of equal elements
 *
 * <p><strong>Time Complexity:</strong> O(n log n) average case, O(n²) worst case (rarely occurs
 * with randomized pivot)
 *
 * <p><strong>Space Complexity:</strong> O(log n) average case for recursion stack
 *
 * <p><strong>Why Quick Sort:</strong> Chosen for its excellent average-case performance (O(n log
 * n)) and low memory overhead. Despite being unstable, it's often faster in practice than stable
 * algorithms like Merge Sort due to better cache locality and fewer comparisons.
 */
@Log4j2
public class QuickSortAlgorithm extends SortingAlgorithm {

  private final Random random = new Random();

  /**
   * Sorts a list of routes using Quick Sort algorithm.
   *
   * @param routes the list of routes to sort (will be modified)
   * @param comparator the comparator to determine the sort order
   */
  @Override
  public void sort(List<Route> routes, Comparator<Route> comparator) {
    if (routes == null || routes.size() <= 1) {
      return;
    }

    log.debug("Starting Quick Sort on {} routes", routes.size());
    quickSort(routes, 0, routes.size() - 1, comparator);
    log.debug("Quick Sort completed");
  }

  /**
   * Recursively sorts the list using quick sort.
   *
   * @param routes the list to sort
   * @param low the low index (inclusive)
   * @param high the high index (inclusive)
   * @param comparator the comparator for ordering
   */
  private void quickSort(List<Route> routes, int low, int high, Comparator<Route> comparator) {
    if (low < high) {
      // Partition the array and get the pivot index
      int pivotIndex = partition(routes, low, high, comparator);

      // Recursively sort elements before and after the partition
      quickSort(routes, low, pivotIndex - 1, comparator);
      quickSort(routes, pivotIndex + 1, high, comparator);
    }
  }

  /**
   * Partitions the array around a pivot element.
   *
   * <p>Uses randomized pivot selection to avoid worst-case O(n²) performance.
   *
   * @param routes the list to partition
   * @param low the low index (inclusive)
   * @param high the high index (inclusive)
   * @param comparator the comparator for ordering
   * @return the final position of the pivot element
   */
  private int partition(List<Route> routes, int low, int high, Comparator<Route> comparator) {
    // Randomized pivot selection to avoid worst-case scenarios
    int randomPivotIndex = low + random.nextInt(high - low + 1);
    swap(routes, randomPivotIndex, high);

    // Use the last element as pivot (after randomization)
    Route pivotElement = routes.get(high);
    int partitionIndex = low - 1; // Index tracking the position of smaller elements

    for (int currentIndex = low; currentIndex < high; currentIndex++) {
      // If current element is smaller than or equal to pivot
      if (comparator.compare(routes.get(currentIndex), pivotElement) <= 0) {
        partitionIndex++;
        swap(routes, partitionIndex, currentIndex);
      }
    }

    // Place pivot in its correct position
    swap(routes, partitionIndex + 1, high);
    return partitionIndex + 1;
  }
}
