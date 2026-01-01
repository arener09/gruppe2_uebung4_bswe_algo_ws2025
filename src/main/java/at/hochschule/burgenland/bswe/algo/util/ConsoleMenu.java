package at.hochschule.burgenland.bswe.algo.util;

import at.hochschule.burgenland.bswe.algo.algorithm.RoutingCalculator;
import at.hochschule.burgenland.bswe.algo.algorithm.sort.MergeSortAlgorithm;
import at.hochschule.burgenland.bswe.algo.algorithm.sort.QuickSortAlgorithm;
import at.hochschule.burgenland.bswe.algo.model.Airport;
import at.hochschule.burgenland.bswe.algo.model.Route;
import at.hochschule.burgenland.bswe.algo.model.comparator.*;
import at.hochschule.burgenland.bswe.algo.model.enums.RouteType;
import at.hochschule.burgenland.bswe.algo.repository.AirportRepository;
import at.hochschule.burgenland.bswe.algo.service.RoutingDataService;
import at.hochschule.burgenland.bswe.algo.structure.Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Provides a console-based menu interface for flight route planning operations.
 *
 * <p>This class manages user interaction through a command-line interface, offering functionality
 * for:
 *
 * <ul>
 *   <li>Calculating optimal flight routes (cheapest, fastest, fewest stopovers, slowest)
 *   <li>Sorting routes using various comparators and algorithms (stable/unstable)
 *   <li>Searching flights by origin, destination, airline, or flight number
 * </ul>
 *
 * <p>The menu runs in a loop until the user explicitly chooses to exit.
 */
public class ConsoleMenu {

  Scanner scanner = new Scanner(System.in);
  Graph graph = RoutingDataService.buildGraph();
  RoutingCalculator calculator = new RoutingCalculator();
  List<Route> routes = new ArrayList<>();
  AirportRepository airportRepository = new AirportRepository();
  List<Airport> airports;

  {
    try {
      airports = airportRepository.loadAirports();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Starts the main menu loop and handles user interactions.
   *
   * <p>Displays a menu with options for route calculation, route sorting, flight searching, and
   * program exit. The method runs continuously until the user selects the exit option (9).
   *
   * <p>Menu options:
   *
   * <ul>
   *   <li>1 - Calculate flight route
   *   <li>2 - Sort flight routes
   *   <li>3 - Search flights
   *   <li>9 - Exit program
   * </ul>
   */
  public void startMenu() {
    boolean running = true;

    while (running) {
      String displayMenu = """
        xxxxx Welcome to our flight-route planner xxxxx
        1 - Calculate flightroute
        2 - Sort flightroute
        3 - Searching
        9 - Exit
        Please select under following options:
        """;
      System.out.println(displayMenu);
      String userInput = scanner.nextLine();
      switch (userInput) {
        case "1" -> {
          calculateRoute();
        }
        case "2" -> {
          sortRoute();
        }
        case "3" -> {
          searchFlight();
        }
        case "9" -> {
          System.out.println(" Exit program.");
          running = false;
        }
        default -> {
          System.out.println("Invalid choice, please try again.");
        }
      }
    }

  }

  /**
   * Calculates and displays a flight route based on user input.
   *
   * <p>Prompts the user to enter origin and destination IATA codes, then allows selection of an
   * optimization criterion:
   *
   * <ul>
   *   <li>1 - Cheapest route (lowest total price)
   *   <li>2 - Fastest route (shortest total duration)
   *   <li>3 - Least stopovers (fewest connecting flights)
   *   <li>4 - Slowest route (longest total duration)
   * </ul>
   *
   * <p>Validates that origin and destination are different. If a route is found, it is displayed
   * using {@link Helper#printRoute(String, Route)}. Otherwise, a "no route found" message is
   * shown.
   */
  private void calculateRoute() {
    printIataCodes(airports);
    String departure = getNonEmptyInput("Enter origin IATA code (e.g., VIE): ");

    String destination;
    while (true) {
      destination = getNonEmptyInput("Enter destination IATA code (e.g., JFK): ");
      if (!destination.equalsIgnoreCase(departure)) {
        break;
      }
      System.out.println("Origin and destination must be different. Please enter a different destination.");
    }

    System.out.println("Calculating flight from " + departure + " to " + destination + ".");

    String subMenu = """
      xxxxx Choose criteria: xxxxx
      1 - Cheapest route
      2 - Fastest route
      3 - Least stopovers
      4 - Slowest route
      """;
    System.out.println(subMenu);
    String choice = getNonEmptyInput("Your choice: ");
    Route route = null;
    switch (choice) {
      case "1" -> route = calculator.findRoute(graph, departure, destination, RouteType.CHEAPEST);
      case "2" -> route = calculator.findRoute(graph, departure, destination, RouteType.FASTEST);
      case "3" -> route = calculator.findRoute(graph, departure, destination, RouteType.FEWEST_STOPOVERS);
      case "4" -> route = calculator.findRoute(graph, departure, destination, RouteType.SLOWEST);
      default -> System.out.println("Invalid choice.");
    }
    if(route != null) {
      Helper.printRoute("Result Route:", route);
    } else {
      System.out.println("No route found.");
    }
  }

  /**
   * Sorts flight routes based on user-selected criteria and algorithm.
   *
   * <p>Prompts the user to:
   *
   * <ol>
   *   <li>Enter a comma-separated list of route IDs to sort
   *   <li>Choose a sorting algorithm (stable MergeSort or unstable QuickSort)
   *   <li>Select a comparator for the sort order
   * </ol>
   *
   * <p>Available comparators:
   *
   * <ul>
   *   <li>1 - Price (ascending - cheapest first)
   *   <li>2 - Duration (ascending - fastest first)
   *   <li>3 - Stopovers (ascending - fewest first)
   *   <li>4 - Airline (descending - Z to A)
   *   <li>5 - Flight Number (descending - Z to A)
   *   <li>6 - Composite (by airline, then duration, then stopovers)
   * </ul>
   *
   * <p>Validates route IDs and displays error messages for invalid or missing IDs. If no valid
   * routes are found, the operation is aborted. Otherwise, the sorted routes are displayed.
   */
  private void sortRoute() {
    System.out.println("xxxxx Sort Routes xxxxx");
    System.out.print("Enter a comma-separated list of route IDs to sort: ");
    String idsInput = scanner.nextLine();
    String[] ids = idsInput.split(",");
    List<Route> routesToSort = new ArrayList<>();
    for(String idStr : ids) {
      try {
        int parsedId = Integer.parseInt(idStr.trim());
        boolean found = false;
        for(Route route : routes) {
            if(route.getId() == parsedId) {
                routesToSort.add(route);
                found = true;
                break;
            }
        }
        if(!found) {
            System.out.println("Route ID not found: " + parsedId);
        }
      } catch(NumberFormatException e) {
        System.out.println("Invalid ID: " + idStr);
      }
    }
    if(routesToSort.isEmpty()) {
        System.out.println("No valid routes found for sorting.");
        return;
    }
    System.out.println("Choose sorting algorithm: ");
    System.out.println("1 - Stable (MergeSort)");
    System.out.println("2 - Unstable (QuickSort)");
    String sortChoice = scanner.nextLine();
    System.out.println("You have chosen: " + (sortChoice.equals("1") ? "Stable" : "Unstable"));

    System.out.println("Choose comparator:");
    System.out.println("1 - Price (ascending)");
    System.out.println("2 - Duration (ascending)");
    System.out.println("3 - Duration (descending)");
    System.out.println("4 - Stopovers (ascending)");
    System.out.println("5 - Composite (Airline, Duration, Stopover)");
    System.out.print("Your choice: ");

    int comparatorChoice;
    try {
      comparatorChoice = Integer.parseInt(scanner.nextLine());
    } catch(Exception e) {
      System.out.println("Invalid input. Using price comparator.");
      comparatorChoice = 1;
    }

    Comparator<Route> comparator;
    switch (comparatorChoice) {
        case 1 -> comparator = new RoutePriceComparator();
        case 2 -> comparator = new RouteDurationComparator();
        case 3 -> comparator = new RouteSlowestComparator();
        case 4 -> comparator = new RouteStopoversComparator();
        case 5 -> comparator = new RouteCombinedComparator();
        default -> {
            System.out.println("Unknown comparator. Using price comparator.");
            comparator = new RoutePriceComparator();
        }
    }

    if(sortChoice.equals("1")) {
        // Stable Sort: Use MergeSortAlgorithm
        MergeSortAlgorithm mergeSort = new MergeSortAlgorithm();
        mergeSort.sort(routesToSort, comparator);
    } else {
        // Unstable Sort: Use QuickSortAlgorithm
        QuickSortAlgorithm quickSort = new QuickSortAlgorithm();
        quickSort.sort(routesToSort, comparator);
    }
    System.out.println("Sorted routes:");
    for(Route route : routesToSort) {
        System.out.println(route);
    }
    return;
  }

  /**
   * Searches for flights based on user-selected criteria.
   *
   * <p>Displays a search menu with the following options:
   *
   * <ul>
   *   <li>1 - Search by origin airport (IATA code)
   *   <li>2 - Search by destination airport (IATA code)
   *   <li>3 - Search by airline name
   *   <li>4 - Search by flight number
   * </ul>
   *
   * <p>For each search type, the user is prompted to enter the search term. All matching flights
   * are displayed. If no flights match the criteria, an appropriate message is shown.
   *
   * <p>Searches are case-insensitive and performed on all flights loaded from the data source.
   */
  private void searchFlight() {
    System.out.println("===== Search Menu =====");
    System.out.println("1 - By origin");
    System.out.println("2 - By destination");
    System.out.println("3 - By airline");
    System.out.println("4 - By flight number");
    System.out.print("Your choice: ");

    String input = scanner.nextLine();
    List<at.hochschule.burgenland.bswe.algo.model.Flight> flights = at.hochschule.burgenland.bswe.algo.service.RoutingDataService.getFlights();

    switch (input) {
      case "1" -> {
        String iata = getNonEmptyInput("Enter origin IATA code: ");
        boolean found = false;
        for (at.hochschule.burgenland.bswe.algo.model.Flight flight : flights) {
          if(flight.getOrigin().equalsIgnoreCase(iata)) {
            System.out.println(flight);
            found = true;
          }
        }
        if (!found) {
          System.out.println("No flights found for this origin.");
        }
      }
      case "2" -> {
        String iata = getNonEmptyInput("Enter destination IATA code: ");
        boolean found = false;
        for (at.hochschule.burgenland.bswe.algo.model.Flight flight : flights) {
          if(flight.getDestination().equalsIgnoreCase(iata)) {
            System.out.println(flight);
            found = true;
          }
        }
        if (!found) {
          System.out.println("No flights found for this destination.");
        }
      }
      case "3" -> {
        String airline = getNonEmptyInput("Enter airline name: ");
        boolean found = false;
        for (at.hochschule.burgenland.bswe.algo.model.Flight flight : flights) {
          if(flight.getAirline().equalsIgnoreCase(airline)) {
            System.out.println(flight);
            found = true;
          }
        }
        if (!found) {
          System.out.println("No flights found for this airline.");
        }
      }
      case "4" -> {
        String flightNumber = getNonEmptyInput("Enter flight number: ");
        boolean found = false;
        for (at.hochschule.burgenland.bswe.algo.model.Flight flight : flights) {
          if(flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
            System.out.println("Flight details found:");
            System.out.println(flight);
            found = true;
          }
        }
        if (!found) {
          System.out.println("No flight found with this flight number.");
        }
      }
      default -> System.out.println("Invalid selection.");
    }
  }

  /**
   * Prompts the user for input and ensures it is not empty.
   *
   * <p>Displays the provided prompt and reads a line from standard input. If the input is empty
   * (after trimming whitespace), the user is asked to try again. This continues until a non-empty
   * input is provided.
   *
   * @param prompt the message to display to the user when requesting input
   * @return a non-empty string entered by the user (trimmed of leading/trailing whitespace)
   */
  public String getNonEmptyInput(String prompt) {
    String input;

    while (true) {
      System.out.print(prompt);
      input = scanner.nextLine().trim();

      if (!input.isEmpty()) {
        return input;
      }

      System.out.println("Input must not be empty. Try again.");
    }
  }

  /**
   * Prints all available IATA airport codes in a formatted manner.
   *
   * <p>Displays the IATA codes of all airports in the provided list, formatted as uppercase
   * strings. The codes are printed 10 per line for better readability. If the list is null or
   * empty, a message indicating no airports are available is displayed.
   *
   * <p>Only airports with non-null and non-blank IATA codes are included in the output.
   *
   * @param airports the list of airports whose IATA codes should be displayed
   */
  public static void printIataCodes(List<Airport> airports) {
    if (airports == null || airports.isEmpty()) {
      System.out.println("No airports available.");
      return;
    }

    System.out.println("IATA-Codes of available airports:");

    int count = 0;
    for (Airport airport : airports) {
      if (airport != null && airport.getIata() != null && !airport.getIata().isBlank()) {
        System.out.print(airport.getIata().toUpperCase() + " ");
        count++;

        if (count % 10 == 0) {
          System.out.println();
        }
      }
    }

    if (count % 10 != 0) {
      System.out.println(); // Abschluss-Zeilenumbruch, falls nötig
    }
  }
}

