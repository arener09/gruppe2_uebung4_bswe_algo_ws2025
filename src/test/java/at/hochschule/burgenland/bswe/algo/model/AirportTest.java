/**
 * ----------------------------------------------------------------------------- File:
 * AirportTest.java Package: at.hochschule.burgenland.bswe.algo.model Authors: Alexander R. Brenner,
 * Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AirportTest {

  private Airport airport;

  @BeforeEach
  void setUp() {
    airport = new Airport();
  }

  @Test
  void testDefaultConstructor() {
    assertNotNull(airport);
    assertEquals(0, airport.getId());
    assertNull(airport.getIata());
    assertNull(airport.getCity());
    assertNull(airport.getCountry());
    assertEquals(0.0, airport.getLatitude());
    assertEquals(0.0, airport.getLongitude());
  }

  @Test
  void testAllArgsConstructor() {
    Airport testAirport = new Airport(1, "VIE", "Vienna", "Austria", 48.1103, 16.5697);

    assertEquals(1, testAirport.getId());
    assertEquals("VIE", testAirport.getIata());
    assertEquals("Vienna", testAirport.getCity());
    assertEquals("Austria", testAirport.getCountry());
    assertEquals(48.1103, testAirport.getLatitude());
    assertEquals(16.5697, testAirport.getLongitude());
  }

  @Test
  void testSettersAndGetters() {
    airport.setId(42);
    airport.setIata("JFK");
    airport.setCity("New York");
    airport.setCountry("United States");
    airport.setLatitude(40.6413);
    airport.setLongitude(-73.7781);

    assertEquals(42, airport.getId());
    assertEquals("JFK", airport.getIata());
    assertEquals("New York", airport.getCity());
    assertEquals("United States", airport.getCountry());
    assertEquals(40.6413, airport.getLatitude());
    assertEquals(-73.7781, airport.getLongitude());
  }

  @Test
  void testEqualsAndHashCode() {
    Airport airport1 = new Airport(1, "VIE", "Vienna", "Austria", 48.1103, 16.5697);
    Airport airport2 = new Airport(1, "VIE", "Vienna", "Austria", 48.1103, 16.5697);
    Airport airport3 = new Airport(2, "JFK", "New York", "United States", 40.6413, -73.7781);

    assertEquals(airport1, airport2);
    assertNotEquals(airport1, airport3);
    assertEquals(airport1.hashCode(), airport2.hashCode());
    assertNotEquals(airport1.hashCode(), airport3.hashCode());
  }

  @Test
  void testToString() {
    airport.setId(1);
    airport.setIata("VIE");
    airport.setCity("Vienna");
    airport.setCountry("Austria");
    airport.setLatitude(48.1103);
    airport.setLongitude(16.5697);

    String result = airport.toString();
    assertTrue(result.contains("Airport"));
    assertTrue(result.contains("id=1"));
    assertTrue(result.contains("iata=VIE"));
    assertTrue(result.contains("city=Vienna"));
    assertTrue(result.contains("country=Austria"));
  }

  @Test
  void testNegativeCoordinates() {
    airport.setLatitude(-90.0);
    airport.setLongitude(-180.0);

    assertEquals(-90.0, airport.getLatitude());
    assertEquals(-180.0, airport.getLongitude());
  }

  @Test
  void testNullValues() {
    airport.setIata(null);
    airport.setCity(null);
    airport.setCountry(null);

    assertNull(airport.getIata());
    assertNull(airport.getCity());
    assertNull(airport.getCountry());
  }

  @Test
  void testEmptyStringValues() {
    airport.setIata("");
    airport.setCity("");
    airport.setCountry("");

    assertEquals("", airport.getIata());
    assertEquals("", airport.getCity());
    assertEquals("", airport.getCountry());
  }
}
