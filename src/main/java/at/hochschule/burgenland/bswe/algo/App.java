package at.hochschule.burgenland.bswe.algo;

import at.hochschule.burgenland.bswe.algo.util.ConsoleMenu;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class App {

  public static void main(String[] args) {
    try {

      ConsoleMenu consoleMenu = new ConsoleMenu();
      consoleMenu.startMenu();

    } catch (Exception e) {
      log.error("An error occurred while calculating routes: {}", e.getMessage(), e);
    }
  }
}
