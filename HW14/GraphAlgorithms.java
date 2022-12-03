import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of Prim's algorithm.
 */
public class GraphAlgorithms {

  /**
   * Runs Prim's algorithm on the given graph and returns the Minimum
   * Spanning Tree (MST) in the form of a set of Edges. If the graph is
   * disconnected and therefore no valid MST exists, return null.
   *
   * You may assume that the passed in graph is undirected. In this framework,
   * this means that if (u, v, 3) is in the graph, then the opposite edge
   * (v, u, 3) will also be in the graph, though as a separate Edge object.
   *
   * The returned set of edges should form an undirected graph. This means
   * that every time you add an edge to your return set, you should add the
   * reverse edge to the set as well. This is for testing purposes. This
   * reverse edge does not need to be the one from the graph itself; you can
   * just make a new edge object representing the reverse edge.
   *
   * You may assume that there will only be one valid MST that can be formed.
   *
   * You should NOT allow self-loops or parallel edges in the MST.
   *
   * You may import/use java.util.PriorityQueue, java.util.Set, and any
   * class that implements the aforementioned interface.
   *
   * DO NOT modify the structure of the graph. The graph should be unmodified
   * after this method terminates.
   *
   * The only instance of java.util.Map that you may use is the adjacency
   * list from graph. DO NOT create new instances of Map for this method
   * (storing the adjacency list in a variable is fine).
   *
   * You may assume that the passed in start vertex and graph will not be null.
   * You may assume that the start vertex exists in the graph.
   *
   * @param <T>   The generic typing of the data.
   * @param start The vertex to begin Prims on.
   * @param graph The graph we are applying Prims to.
   * @return The MST of the graph or null if there is no valid MST.
   */
  public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
    Set<Edge<T>> mstSet = new HashSet<>();
    Set<Vertex<T>> visited = new HashSet<>();
    PriorityQueue<Edge<T>> edgesQueue = new PriorityQueue<>();
    Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
    int vSetSize = graph.getVertices().size();

    List<VertexDistance<T>> neighbors = adjList.get(start);
    visited.add(start);
    for (VertexDistance<T> neighbor : neighbors) {
      edgesQueue.add(
        new Edge<T>(start, neighbor.getVertex(), neighbor.getDistance())
      );
      edgesQueue.add(
        new Edge<T>(neighbor.getVertex(), start, neighbor.getDistance())
      );
    }

    while (vSetSize - mstSet.size() > 1 && edgesQueue.size() > 0) {
      Edge<T> shortestEdge = edgesQueue.poll();
      Vertex<T> currentVertex = shortestEdge.getV();

      if (!visited.contains(currentVertex)) {
        visited.add(currentVertex);
        mstSet.add(shortestEdge);
        neighbors = adjList.get(currentVertex);
        for (VertexDistance<T> neighbor : neighbors) {
          Vertex<T> neighborVertex = neighbor.getVertex();
          if (!visited.contains(neighborVertex)) {
            edgesQueue.add(
              new Edge<T>(currentVertex, neighborVertex, neighbor.getDistance())
            );
            edgesQueue.add(
              new Edge<T>(neighborVertex, currentVertex, neighbor.getDistance())
            );
          }
        }
      }
    }

    return mstSet;
  }

  // Tests
  public static <T> void printEdgeSet(Set<T> set) {
    System.out.println("Set contains: ");
    Iterator<T> values = set.iterator();
    while (values.hasNext()) {
      System.out.println(values.next() + "");
    }
  }

  public static void main(String[] args) {
    String[] values = { "A", "B", "C", "D", "E", "F", "G", "H" };
    Set<Vertex<String>> vertices = new HashSet<>();
    Map<String, Vertex<String>> vMap = new HashMap<>();
    for (int i = 0; i < values.length; i++) {
      Vertex<String> temp = new Vertex<String>(values[i]);
      vertices.add(temp);
      vMap.put(values[i], temp);
    }

    Set<Edge<String>> edges = new HashSet<>();
    edges.add(new Edge<String>(vMap.get("A"), vMap.get("B"), 1));
    edges.add(new Edge<String>(vMap.get("B"), vMap.get("A"), 1));
    edges.add(new Edge<String>(vMap.get("A"), vMap.get("C"), 5));
    edges.add(new Edge<String>(vMap.get("C"), vMap.get("A"), 5));
    edges.add(new Edge<String>(vMap.get("B"), vMap.get("C"), 7));
    edges.add(new Edge<String>(vMap.get("C"), vMap.get("B"), 7));
    edges.add(new Edge<String>(vMap.get("B"), vMap.get("D"), 9));
    edges.add(new Edge<String>(vMap.get("D"), vMap.get("B"), 9));
    edges.add(new Edge<String>(vMap.get("B"), vMap.get("G"), 1));
    edges.add(new Edge<String>(vMap.get("G"), vMap.get("B"), 1));
    edges.add(new Edge<String>(vMap.get("C"), vMap.get("G"), 13));
    edges.add(new Edge<String>(vMap.get("G"), vMap.get("C"), 13));
    edges.add(new Edge<String>(vMap.get("D"), vMap.get("F"), 4));
    edges.add(new Edge<String>(vMap.get("F"), vMap.get("D"), 4));
    edges.add(new Edge<String>(vMap.get("F"), vMap.get("G"), 2));
    edges.add(new Edge<String>(vMap.get("G"), vMap.get("F"), 2));
    edges.add(new Edge<String>(vMap.get("F"), vMap.get("H"), 0));
    edges.add(new Edge<String>(vMap.get("H"), vMap.get("F"), 0));
    edges.add(new Edge<String>(vMap.get("G"), vMap.get("H"), 3));
    edges.add(new Edge<String>(vMap.get("H"), vMap.get("G"), 3));
    edges.add(new Edge<String>(vMap.get("G"), vMap.get("E"), 10));
    edges.add(new Edge<String>(vMap.get("E"), vMap.get("G"), 10));

    Graph<String> test = new Graph<>(vertices, edges);

    Set<Edge<String>> testResult = prims(vMap.get("A"), test);
    printEdgeSet(testResult);
  }
}
