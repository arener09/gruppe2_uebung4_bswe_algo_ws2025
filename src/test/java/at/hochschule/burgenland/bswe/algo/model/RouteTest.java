/**
 * ----------------------------------------------------------------------------- File:
 * RouteTest.java Package: at.hochschule.burgenland.bswe.algo.model Authors: Alexander R. Brenner,
 * Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RouteTest {

  private Route route;

  @BeforeEach
  void setUp() {
    route = new Route();
  }

  @Test
  void testDefaultConstructor() {
    assertNotNull(route);
    assertEquals(0, route.getId());
    assertNull(route.getFlights());
    assertEquals(0, route.getTotalDuration());
    assertEquals(0.0, route.getTotalPrice());
    assertEquals(0, route.getStopovers());
  }

  @Test
  void testAllArgsConstructor() {
    Route testRoute = new Route(1, "1-47-18", 480, 450.0, 2);

    assertEquals(1, testRoute.getId());
    assertEquals("1-47-18", testRoute.getFlights());
    assertEquals(480, testRoute.getTotalDuration());
    assertEquals(450.0, testRoute.getTotalPrice());
    assertEquals(2, testRoute.getStopovers());
  }

  @Test
  void testSettersAndGetters() {
    route.setId(42);
    route.setFlights("12-45-63");
    route.setTotalDuration(720);
    route.setTotalPrice(320.75);
    route.setStopovers(3);

    assertEquals(42, route.getId());
    assertEquals("12-45-63", route.getFlights());
    assertEquals(720, route.getTotalDuration());
    assertEquals(320.75, route.getTotalPrice());
    assertEquals(3, route.getStopovers());
  }

  @Test
  void testEqualsAndHashCode() {
    Route route1 = new Route(1, "1-47-18", 480, 450.0, 2);
    Route route2 = new Route(1, "1-47-18", 480, 450.0, 2);
    Route route3 = new Route(2, "12-45-63", 720, 320.75, 3);

    assertEquals(route1, route2);
    assertNotEquals(route1, route3);
    assertEquals(route1.hashCode(), route2.hashCode());
    assertNotEquals(route1.hashCode(), route3.hashCode());
  }

  @Test
  void testToString() {
    route.setId(1);
    route.setFlights("1-47-18");
    route.setTotalDuration(480);
    route.setTotalPrice(450.0);
    route.setStopovers(2);

    String result = route.toString();
    assertTrue(result.contains("Route"));
    assertTrue(result.contains("id=1"));
    assertTrue(result.contains("flights=1-47-18"));
    assertTrue(result.contains("totalDuration=480"));
    assertTrue(result.contains("totalPrice=450.0"));
    assertTrue(result.contains("stopovers=2"));
  }

  @Test
  void testNegativeValues() {
    route.setId(-1);
    route.setTotalDuration(-100);
    route.setTotalPrice(-50.0);
    route.setStopovers(-2);

    assertEquals(-1, route.getId());
    assertEquals(-100, route.getTotalDuration());
    assertEquals(-50.0, route.getTotalPrice());
    assertEquals(-2, route.getStopovers());
  }

  @Test
  void testZeroValues() {
    route.setId(0);
    route.setTotalDuration(0);
    route.setTotalPrice(0.0);
    route.setStopovers(0);

    assertEquals(0, route.getId());
    assertEquals(0, route.getTotalDuration());
    assertEquals(0.0, route.getTotalPrice());
    assertEquals(0, route.getStopovers());
  }

  @Test
  void testNullFlights() {
    route.setFlights(null);
    assertNull(route.getFlights());
  }

  @Test
  void testEmptyFlights() {
    route.setFlights("");
    assertEquals("", route.getFlights());
  }

  @Test
  void testSingleFlight() {
    route.setFlights("1");
    route.setStopovers(0);
    assertEquals("1", route.getFlights());
    assertEquals(0, route.getStopovers());
  }

  @Test
  void testMultipleFlights() {
    route.setFlights("1-2-3-4-5");
    route.setStopovers(4);
    assertEquals("1-2-3-4-5", route.getFlights());
    assertEquals(4, route.getStopovers());
  }

  @Test
  void testLargeValues() {
    route.setId(Integer.MAX_VALUE);
    route.setTotalDuration(Integer.MAX_VALUE);
    route.setTotalPrice(Double.MAX_VALUE);
    route.setStopovers(Integer.MAX_VALUE);

    assertEquals(Integer.MAX_VALUE, route.getId());
    assertEquals(Integer.MAX_VALUE, route.getTotalDuration());
    assertEquals(Double.MAX_VALUE, route.getTotalPrice());
    assertEquals(Integer.MAX_VALUE, route.getStopovers());
  }

  @Test
  void testPrecisionInPrice() {
    route.setTotalPrice(123.456789);
    assertEquals(123.456789, route.getTotalPrice());
  }
}
