/**
 * ----------------------------------------------------------------------------- File: Main.java
 * Package: at.hochschule.burgenland.bswe.algo Authors: Alexander R. Brenner, Raja Abdulhadi, Julia
 * Michler BSWE3B, Hochschule Burgenland
 * -----------------------------------------------------------------------------
 */
package at.hochschule.burgenland.bswe.algo;

import at.hochschule.burgenland.bswe.algo.util.ConsoleMenu;
import lombok.extern.log4j.Log4j2;

/** Main class to demonstrate route calculation. */
@Log4j2
public class Main {

  public static void main(String[] args) {
    try {

      ConsoleMenu consoleMenu = new ConsoleMenu();
      consoleMenu.startMenu();

    } catch (Exception e) {
      log.error("An error occurred while calculating routes: {}", e.getMessage(), e);
    }
  }
}
