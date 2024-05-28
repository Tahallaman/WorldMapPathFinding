package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/** Represents a graph data structure. */
public class Graph {
  private Map<Country, LinkedHashSet<Country>> adjacencyMap;

  /** Constructs an empty graph. */
  public Graph() {
    adjacencyMap = new HashMap<>();
  }

  /**
   * Adds an edge between two countries in the graph (bidirectional).
   *
   * @param country1 the first country
   * @param country2 the second country
   */
  public void addEdge(Country country1, Country country2) {
    adjacencyMap.putIfAbsent(country1, new LinkedHashSet<>());
    adjacencyMap.putIfAbsent(country2, new LinkedHashSet<>());
    adjacencyMap.get(country1).add(country2);
  }

  /**
   * Finds the shortest path between two countries in the graph using breadth-first search.
   *
   * @param start the starting country
   * @param end the ending country
   * @return a list of countries representing the shortest path from start to end
   */
  public List<Country> findShortestPath(Country start, Country end) {
    Map<Country, Country> parentMap = new HashMap<>();
    Queue<Country> queue = new LinkedList<>();
    queue.add(start);
    parentMap.put(start, null);

    while (!queue.isEmpty() && !parentMap.containsKey(end)) {
      Country country = queue.poll();
      for (Country neighbor : adjacencyMap.get(country)) {
        if (!parentMap.containsKey(neighbor)) {
          queue.add(neighbor);
          parentMap.put(neighbor, country);
        }
      }
    }
    List<Country> path = new LinkedList<>();
    for (Country country = end; country != null; country = parentMap.get(country)) {
      path.add(0, country);
    }

    return path;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((adjacencyMap == null) ? 0 : adjacencyMap.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Graph other = (Graph) obj;
    if (adjacencyMap == null) {
      if (other.adjacencyMap != null) return false;
    } else if (!adjacencyMap.equals(other.adjacencyMap)) return false;
    return true;
  }
}
