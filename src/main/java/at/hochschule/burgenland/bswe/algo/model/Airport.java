/**
 * ----------------------------------------------------------------------------- File: Airport.java
 * Package: at.hochschule.burgenland.bswe.algo.model Authors: Alexander R. Brenner, Raja Abdulhadi,
 * Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing an airport record. Each airport is uniquely identified by its numeric ID
 * and IATA code. The class holds information about its city, country, and coordinates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {

  /** Unique numeric identifier for the airport. */
  @CsvBindByName(column = "id")
  private int id;

  /** Three-letter IATA code (e.g., VIE, JFK). */
  @CsvBindByName(column = "iata")
  private String iata;

  /** City where the airport is located. */
  @CsvBindByName(column = "city")
  private String city;

  /** Country of the airport. */
  @CsvBindByName(column = "country")
  private String country;

  /** Geographic latitude coordinate. */
  @CsvBindByName(column = "latitude")
  private double latitude;

  /** Geographic longitude coordinate. */
  @CsvBindByName(column = "longitude")
  private double longitude;
}
