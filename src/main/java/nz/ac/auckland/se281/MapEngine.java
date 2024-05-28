package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** This class represents the main entry point for the map engine. */
public class MapEngine {
  private Map<String, Country> countryMap = new HashMap<>();
  private Graph mapGraph;

  /** Constructs a new MapEngine object. It initializes the map graph and loads the map data. */
  public MapEngine() {
    mapGraph = new Graph();
    loadMap();
    // Print the map
    System.out.println(mapGraph);
  }

  /**
   * Loads the map data from external files. It reads the country data and adjacency data, and
   * constructs the country map and map graph accordingly.
   */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    for (String countryData : countries) {
      String[] parts = countryData.split(",");
      String name = parts[0];
      String continent = parts[1];
      int tax = Integer.parseInt(parts[2]);
      Country country = new Country(name, continent, tax);
      countryMap.put(name, country); // Keyed by country name for easy lookup
    }

    for (String adjacencyData : adjacencies) {
      String[] parts = adjacencyData.split(",");
      String countryName = parts[0];
      for (int i = 1; i < parts.length; i++) {
        mapGraph.addEdge(countryMap.get(countryName), countryMap.get(parts[i]));
      }
    }
  }

  /**
   * Displays information about a specific country. This method is invoked when the user runs the
   * "info-country" command.
   */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    String input = validCountryInputScanner();
    MessageCli.COUNTRY_INFO.printMessage(
        input,
        countryMap.get(input).getContinent(),
        Integer.toString(countryMap.get(input).getTax()));
  }

  /**
   * Displays the shortest route between two countries. This method is invoked when the user runs
   * the "route" command.
   */
  public void showRoute() {
    MessageCli.INSERT_SOURCE.printMessage();
    String source = validCountryInputScanner();
    MessageCli.INSERT_DESTINATION.printMessage();
    String destination = validCountryInputScanner();
    List<Country> route =
        mapGraph.findShortestPath(countryMap.get(source), countryMap.get(destination));
    if (route.size() == 1) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    } else {
      MessageCli.ROUTE_INFO.printMessage(route.toString());

      Set<String> continents = new LinkedHashSet<>();
      for (Country country : route) {
        continents.add(country.getContinent());
      }

      // Print the continents and total tax
      MessageCli.CONTINENT_INFO.printMessage(continents.toString());
      int totalTax = 0;

      for (Country country : route.subList(1, route.size())) {
        totalTax += country.getTax();
      }
      MessageCli.TAX_INFO.printMessage(Integer.toString(totalTax));
    }
  }

  /**
   * Reads and validates user input for a country. This method will not return unless valid input is
   * given.
   *
   * @return The validated country input.
   */
  public String validCountryInputScanner() {
    String input = null;
    while (true) {
      try {
        input = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());

        // Check if the input is a valid country
        if (!countryMap.containsKey(input)) {
          throw new CountryMapException();
        } else {
          return input;
        }
      } catch (CountryMapException e) {
        MessageCli.INVALID_COUNTRY.printMessage(input);
      }
    }
  }
}
