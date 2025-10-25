/**
 * ----------------------------------------------------------------------------- File:
 * RouteTypeTest.java Package: at.hochschule.burgenland.bswe.algo.model.enums Authors: Alexander R.
 * Brenner, Raja Abdulhadi, Julia Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo.model.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RouteTypeTest {

  @Test
  void testEnumValues() {
    RouteType[] values = RouteType.values();
    assertEquals(3, values.length);
    assertEquals(RouteType.CHEAPEST, values[0]);
    assertEquals(RouteType.FASTEST, values[1]);
    assertEquals(RouteType.FEWEST_STOPOVERS, values[2]);
  }

  @Test
  void testValueOf() {
    assertEquals(RouteType.CHEAPEST, RouteType.valueOf("CHEAPEST"));
    assertEquals(RouteType.FASTEST, RouteType.valueOf("FASTEST"));
    assertEquals(RouteType.FEWEST_STOPOVERS, RouteType.valueOf("FEWEST_STOPOVERS"));
  }
}
