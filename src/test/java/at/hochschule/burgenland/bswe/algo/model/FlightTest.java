/**
 * ----------------------------------------------------------------------------- File:
 * FlightTest.java Package: at.hochschule.burgenland.bswe.algo.model Authors: Alexander R. Brenner,
 * Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlightTest {

  private Flight flight;

  @BeforeEach
  void setUp() {
    flight = new Flight();
  }

  @Test
  void testDefaultConstructor() {
    assertNotNull(flight);
    assertEquals(0, flight.getId());
    assertNull(flight.getOrigin());
    assertNull(flight.getDestination());
    assertNull(flight.getAirline());
    assertNull(flight.getFlightNumber());
    assertEquals(0, flight.getDuration());
    assertEquals(0.0, flight.getPrice());
    assertNull(flight.getDepartureTime());
  }

  @Test
  void testAllArgsConstructor() {
    LocalTime departureTime = LocalTime.of(10, 35);
    Flight testFlight =
        new Flight(1, "VIE", "JFK", "Austrian Airlines", "OS35", 480, 450.0, departureTime);

    assertEquals(1, testFlight.getId());
    assertEquals("VIE", testFlight.getOrigin());
    assertEquals("JFK", testFlight.getDestination());
    assertEquals("Austrian Airlines", testFlight.getAirline());
    assertEquals("OS35", testFlight.getFlightNumber());
    assertEquals(480, testFlight.getDuration());
    assertEquals(450.0, testFlight.getPrice());
    assertEquals(departureTime, testFlight.getDepartureTime());
  }

  @Test
  void testSettersAndGetters() {
    LocalTime departureTime = LocalTime.of(14, 20);

    flight.setId(42);
    flight.setOrigin("LHR");
    flight.setDestination("CDG");
    flight.setAirline("British Airways");
    flight.setFlightNumber("BA123");
    flight.setDuration(90);
    flight.setPrice(120.50);
    flight.setDepartureTime(departureTime);

    assertEquals(42, flight.getId());
    assertEquals("LHR", flight.getOrigin());
    assertEquals("CDG", flight.getDestination());
    assertEquals("British Airways", flight.getAirline());
    assertEquals("BA123", flight.getFlightNumber());
    assertEquals(90, flight.getDuration());
    assertEquals(120.50, flight.getPrice());
    assertEquals(departureTime, flight.getDepartureTime());
  }

  @Test
  void testEqualsAndHashCode() {
    LocalTime departureTime = LocalTime.of(10, 35);
    Flight flight1 =
        new Flight(1, "VIE", "JFK", "Austrian Airlines", "OS35", 480, 450.0, departureTime);
    Flight flight2 =
        new Flight(1, "VIE", "JFK", "Austrian Airlines", "OS35", 480, 450.0, departureTime);
    Flight flight3 =
        new Flight(2, "LHR", "CDG", "British Airways", "BA123", 90, 120.50, departureTime);

    assertEquals(flight1, flight2);
    assertNotEquals(flight1, flight3);
    assertEquals(flight1.hashCode(), flight2.hashCode());
    assertNotEquals(flight1.hashCode(), flight3.hashCode());
  }

  @Test
  void testToString() {
    LocalTime departureTime = LocalTime.of(10, 35);
    flight.setId(1);
    flight.setOrigin("VIE");
    flight.setDestination("JFK");
    flight.setAirline("Austrian Airlines");
    flight.setFlightNumber("OS35");
    flight.setDuration(480);
    flight.setPrice(450.0);
    flight.setDepartureTime(departureTime);

    String result = flight.toString();
    assertTrue(result.contains("Flight"));
    assertTrue(result.contains("id=1"));
    assertTrue(result.contains("origin=VIE"));
    assertTrue(result.contains("destination=JFK"));
    assertTrue(result.contains("airline=Austrian Airlines"));
    assertTrue(result.contains("flightNumber=OS35"));
    assertTrue(result.contains("duration=480"));
    assertTrue(result.contains("price=450.0"));
  }

  @Test
  void testNegativeDuration() {
    flight.setDuration(-10);
    assertEquals(-10, flight.getDuration());
  }

  @Test
  void testNegativePrice() {
    flight.setPrice(-50.0);
    assertEquals(-50.0, flight.getPrice());
  }

  @Test
  void testZeroValues() {
    flight.setDuration(0);
    flight.setPrice(0.0);
    flight.setDepartureTime(LocalTime.of(0, 0));

    assertEquals(0, flight.getDuration());
    assertEquals(0.0, flight.getPrice());
    assertEquals(LocalTime.of(0, 0), flight.getDepartureTime());
  }

  @Test
  void testNullValues() {
    flight.setOrigin(null);
    flight.setDestination(null);
    flight.setAirline(null);
    flight.setFlightNumber(null);
    flight.setDepartureTime(null);

    assertNull(flight.getOrigin());
    assertNull(flight.getDestination());
    assertNull(flight.getAirline());
    assertNull(flight.getFlightNumber());
    assertNull(flight.getDepartureTime());
  }

  @Test
  void testEmptyStringValues() {
    flight.setOrigin("");
    flight.setDestination("");
    flight.setAirline("");
    flight.setFlightNumber("");

    assertEquals("", flight.getOrigin());
    assertEquals("", flight.getDestination());
    assertEquals("", flight.getAirline());
    assertEquals("", flight.getFlightNumber());
  }

  @Test
  void testLargeValues() {
    flight.setId(Integer.MAX_VALUE);
    flight.setDuration(Integer.MAX_VALUE);
    flight.setPrice(Double.MAX_VALUE);

    assertEquals(Integer.MAX_VALUE, flight.getId());
    assertEquals(Integer.MAX_VALUE, flight.getDuration());
    assertEquals(Double.MAX_VALUE, flight.getPrice());
  }
}
