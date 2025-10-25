/**
 * ----------------------------------------------------------------------------- File: Edge.java
 * Package: at.hochschule.burgenland.bswe.algo.structure Authors: Alexander R. Brenner, Raja
 * Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.structure;

import at.hochschule.burgenland.bswe.algo.model.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Represents a graph edge corresponding to a single flight. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {

  /** Destination airport code (IATA). */
  private String destinationIata;

  /** Duration of the flight in minutes. */
  private int duration;

  /** Price of the flight in euros. */
  private double price;

  /** Original flight object reference. */
  private Flight flight;
}
