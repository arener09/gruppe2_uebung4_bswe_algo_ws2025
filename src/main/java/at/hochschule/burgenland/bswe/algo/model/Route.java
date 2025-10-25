/**
 * ----------------------------------------------------------------------------- File: Route.java
 * Package: at.hochschule.burgenland.bswe.algo.model Authors: Alexander R. Brenner, Raja Abdulhadi,
 * Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a flight route record. A route is composed of multiple flights
 * represented by their IDs joined with hyphens (e.g., "1-47-18"), as well as aggregate values for
 * duration, price, and stopovers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {

  /** Unique numeric identifier for the route. */
  @CsvBindByPosition(position = 0)
  @CsvBindByName(column = "id")
  private int id;

  /** Hyphen-separated list of flight IDs (e.g., "1-47-18"). */
  @CsvBindByPosition(position = 1)
  @CsvBindByName(column = "flights")
  private String flights;

  /** Total duration of the route in minutes. */
  @CsvBindByPosition(position = 2)
  @CsvBindByName(column = "totalDuration")
  private int totalDuration;

  /** Total price of the route in euros. */
  @CsvBindByPosition(position = 3)
  @CsvBindByName(column = "totalPrice")
  private double totalPrice;

  /** Number of stopovers (connecting flights). */
  @CsvBindByPosition(position = 4)
  @CsvBindByName(column = "stopovers")
  private int stopovers;
}
