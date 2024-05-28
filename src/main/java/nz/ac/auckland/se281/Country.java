package nz.ac.auckland.se281;

public class Country {
  private String name;
  private String continent;
  private int tax;

  /** Represents a country with its name, continent, and tax rate. */
  public Country(String name, String continent, int tax) {
    this.name = name;
    this.continent = continent;
    this.tax = tax;
  }

  // Getters
  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

  public int getTax() {
    return tax;
  }

  @Override
  public String toString() {
    return name;
  }
}
