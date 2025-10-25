/**
 * ----------------------------------------------------------------------------- File: Flight.java
 * Package: at.hochschule.burgenland.bswe.algo.model Authors: Alexander R. Brenner, Raja Abdulhadi,
 * Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.model;

import at.hochschule.burgenland.bswe.algo.util.LocalTimeConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a flight record. Each flight connects two airports and includes metadata
 * such as airline, flight number, duration, price, and departure time.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

  /** Unique numeric identifier for the flight. */
  @CsvBindByName(column = "id")
  private int id;

  /** IATA code of the origin airport. */
  @CsvBindByName(column = "origin")
  private String origin;

  /** IATA code of the destination airport. */
  @CsvBindByName(column = "destination")
  private String destination;

  /** Name of the airline operating the flight. */
  @CsvBindByName(column = "airline")
  private String airline;

  /** Airline-specific flight number (e.g., OS35). */
  @CsvBindByName(column = "flightNumber")
  private String flightNumber;

  /** Flight duration in minutes. */
  @CsvBindByName(column = "duration")
  private int duration;

  /** Ticket price in euros. */
  @CsvBindByName(column = "price")
  private double price;

  /** Scheduled departure time in HH:mm format. */
  @CsvCustomBindByName(column = "departureTime", converter = LocalTimeConverter.class)
  private LocalTime departureTime;
}
