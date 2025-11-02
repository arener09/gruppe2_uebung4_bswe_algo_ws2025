/**
 * ----------------------------------------------------------------------------- File:
 * MergeSortAlgorithm.java Package: at.hochschule.burgenland.bswe.algo.algorithm.sort Authors:
 * Alexander R. Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.algorithm.sort;

import at.hochschule.burgenland.bswe.algo.algorithm.sort.base.SortingAlgorithm;
import at.hochschule.burgenland.bswe.algo.model.Route;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.extern.log4j.Log4j2;

/**
 * Stable sorting algorithm implementation using Merge Sort.
 *
 * <p>Merge Sort is a divide-and-conquer algorithm that:
 *
 * <ul>
 *   <li>Divides the list into two halves
 *   <li>Recursively sorts both halves
 *   <li>Merges the sorted halves
 * </ul>
 *
 * <p><strong>Stability:</strong> Stable - maintains the relative order of equal elements
 *
 * <p><strong>Time Complexity:</strong> O(n log n) in all cases (best, average, worst)
 *
 * <p><strong>Space Complexity:</strong> O(n) - requires additional space for merging
 *
 * <p><strong>Why Merge Sort:</strong> Chosen for stability and predictable O(n log n) performance.
 * Ideal when preserving the relative order of equal routes is important.
 */
@Log4j2
public class MergeSortAlgorithm extends SortingAlgorithm {

  /**
   * Sorts a list of routes using Merge Sort algorithm.
   *
   * @param routes the list of routes to sort (will be modified)
   * @param comparator the comparator to determine the sort order
   */
  @Override
  public void sort(List<Route> routes, Comparator<Route> comparator) {
    if (routes == null || routes.size() <= 1) {
      return;
    }

    log.debug("Starting Merge Sort on {} routes", routes.size());
    mergeSort(routes, 0, routes.size() - 1, comparator);
    log.debug("Merge Sort completed");
  }

  /**
   * Recursively sorts the list using merge sort.
   *
   * @param routes the list to sort
   * @param left the left index (inclusive)
   * @param right the right index (inclusive)
   * @param comparator the comparator for ordering
   */
  private void mergeSort(List<Route> routes, int left, int right, Comparator<Route> comparator) {
    if (left < right) {
      int middle = left + (right - left) / 2;

      // Recursively sort both halves
      mergeSort(routes, left, middle, comparator);
      mergeSort(routes, middle + 1, right, comparator);

      // Merge the sorted halves
      merge(routes, left, middle, right, comparator);
    }
  }

  /**
   * Merges two sorted subarrays into a single sorted array.
   *
   * @param routes the list containing both subarrays
   * @param left the left index of the first subarray
   * @param middle the end index of the first subarray
   * @param right the end index of the second subarray
   * @param comparator the comparator for ordering
   */
  private void merge(
      List<Route> routes, int left, int middle, int right, Comparator<Route> comparator) {
    // Create temporary arrays for both halves
    List<Route> leftHalf = new ArrayList<>(routes.subList(left, middle + 1));
    List<Route> rightHalf = new ArrayList<>(routes.subList(middle + 1, right + 1));

    int leftHalfIndex = 0;
    int rightHalfIndex = 0;
    int targetIndex = left;

    // Merge the temporary arrays back into the original list
    while (leftHalfIndex < leftHalf.size() && rightHalfIndex < rightHalf.size()) {
      if (comparator.compare(leftHalf.get(leftHalfIndex), rightHalf.get(rightHalfIndex)) <= 0) {
        routes.set(targetIndex, leftHalf.get(leftHalfIndex));
        leftHalfIndex++;
      } else {
        routes.set(targetIndex, rightHalf.get(rightHalfIndex));
        rightHalfIndex++;
      }
      targetIndex++;
    }

    // Copy remaining elements from left half
    while (leftHalfIndex < leftHalf.size()) {
      routes.set(targetIndex, leftHalf.get(leftHalfIndex));
      leftHalfIndex++;
      targetIndex++;
    }

    // Copy remaining elements from right half
    while (rightHalfIndex < rightHalf.size()) {
      routes.set(targetIndex, rightHalf.get(rightHalfIndex));
      rightHalfIndex++;
      targetIndex++;
    }
  }
}
