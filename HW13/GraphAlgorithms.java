import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various graph traversal algorithms.
 */
public class GraphAlgorithms {

  /**
   * Performs a breadth first search (bfs) on the input graph, starting at
   * the parameterized starting vertex.
   *
   * When exploring a vertex, explore in the order of toVisit returned by
   * the adjacency list. Failure to do so may cause you to lose points.
   *
   * You may import/use java.util.Set, java.util.List, java.util.Queue, and
   * any classes that implement the aforementioned interfaces, as long as they
   * are efficient.
   *
   * The only instance of java.util.Map that you should use is the adjacency
   * list from graph. DO NOT create new instances of Map for BFS
   * (storing the adjacency list in a variable is fine).
   *
   * DO NOT modify the structure of the graph. The graph should be unmodified
   * after this method terminates.
   *
   * You may assume that the passed in start vertex and graph will not be null.
   * You may assume that the start vertex exists in the graph.
   *
   * @param <T>   The generic typing of the data.
   * @param start The vertex to begin the bfs on.
   * @param graph The graph to search through.
   * @return List of vertices in visited order.
   */
  public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
    Set<Vertex<T>> visited = new HashSet<>();
    Queue<Vertex<T>> toVisit = new ArrayDeque<>();
    List<Vertex<T>> visitedOrder = new ArrayList<>();
    Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
    toVisit.add(start);
    while (
      toVisit.peek() != null && graph.getVertices().size() > visited.size()
    ) {
      Vertex<T> current = toVisit.poll();
      if (!visited.contains(current)) {
        visited.add(current);
        visitedOrder.add(current);
      }

      List<VertexDistance<T>> adjVertices = adjList.get(current);
      for (VertexDistance<T> adjVertex : adjVertices) {
        Vertex<T> neighbor = adjVertex.getVertex();
        if (!visited.contains(neighbor)) {
          toVisit.add(neighbor);
        }
      }
    }
    return visitedOrder;
  }

  /**
   * Performs a depth first search (dfs) on the input graph, starting at
   * the parameterized starting vertex.
   *
   * When exploring a vertex, explore in the order of toVisit returned by
   * the adjacency list. Failure to do so may cause you to lose points.
   *
   * NOTE: This method should be implemented recursively. You may need to
   * create a helper method.
   *
   * You may import/use java.util.Set, java.util.List, and any classes that
   * implement the aforementioned interfaces, as long as they are efficient.
   *
   * The only instance of java.util.Map that you may use is the adjacency list
   * from graph. DO NOT create new instances of Map for DFS
   * (storing the adjacency list in a variable is fine).
   *
   * DO NOT modify the structure of the graph. The graph should be unmodified
   * after this method terminates.
   *
   * You may assume that the passed in start vertex and graph will not be null.
   * You may assume that the start vertex exists in the graph.
   *
   * @param <T>   The generic typing of the data.
   * @param start The vertex to begin the dfs on.
   * @param graph The graph to search through.
   * @return List of vertices in visited order.
   */
  public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
    List<Vertex<T>> visitedOrder = new ArrayList<>();
    Set<Vertex<T>> visited = new HashSet<>();
    rDfs(start, graph, visitedOrder, visited);
    return visitedOrder;
  }

  private static <T> void rDfs(
    Vertex<T> current,
    Graph<T> graph,
    List<Vertex<T>> visitedOrder,
    Set<Vertex<T>> visited
  ) {
    if (visited.size() == graph.getVertices().size()) return;
    if (visited.contains(current)) return;

    Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
    List<VertexDistance<T>> adjVertices = adjList.get(current);

    visited.add(current);
    visitedOrder.add(current);
    for (VertexDistance<T> adjVertex : adjVertices) {
      Vertex<T> neighbor = adjVertex.getVertex();
      if (!visited.contains(neighbor)) {
        rDfs(neighbor, graph, visitedOrder, visited);
      }
      if (visited.size() == graph.getVertices().size()) return;
    }
  }

  // Tests
  public static <T> void printList(List<T> list) {
    System.out.print("List contains: ");
    Iterator<T> values = list.iterator();
    while (values.hasNext()) {
      System.out.print(values.next() + " ");
    }
    System.out.println();
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
    edges.add(new Edge<String>(vMap.get("A"), vMap.get("C"), 1));
    edges.add(new Edge<String>(vMap.get("C"), vMap.get("A"), 1));
    edges.add(new Edge<String>(vMap.get("A"), vMap.get("D"), 1));
    edges.add(new Edge<String>(vMap.get("D"), vMap.get("A"), 1));
    edges.add(new Edge<String>(vMap.get("A"), vMap.get("E"), 1));
    edges.add(new Edge<String>(vMap.get("E"), vMap.get("A"), 1));
    edges.add(new Edge<String>(vMap.get("D"), vMap.get("F"), 1));
    edges.add(new Edge<String>(vMap.get("F"), vMap.get("D"), 1));
    edges.add(new Edge<String>(vMap.get("E"), vMap.get("G"), 1));
    edges.add(new Edge<String>(vMap.get("G"), vMap.get("E"), 1));
    edges.add(new Edge<String>(vMap.get("B"), vMap.get("G"), 1));
    edges.add(new Edge<String>(vMap.get("G"), vMap.get("B"), 1));
    edges.add(new Edge<String>(vMap.get("E"), vMap.get("G"), 1));
    edges.add(new Edge<String>(vMap.get("G"), vMap.get("E"), 1));
    edges.add(new Edge<String>(vMap.get("F"), vMap.get("G"), 1));
    edges.add(new Edge<String>(vMap.get("G"), vMap.get("F"), 1));
    edges.add(new Edge<String>(vMap.get("H"), vMap.get("G"), 1));
    edges.add(new Edge<String>(vMap.get("G"), vMap.get("H"), 1));

    Graph<String> test = new Graph<>(vertices, edges);
    List<Vertex<String>> bfsResultFromA = GraphAlgorithms.bfs(
      vMap.get("A"),
      test
    );
    System.out.print("Test 1: Simple graph bfs starting with \"A\". ");
    printList(bfsResultFromA);
    List<Vertex<String>> bfsResultFromB = GraphAlgorithms.bfs(
      vMap.get("B"),
      test
    );
    System.out.print("Test 2: Simple graph bfs starting with \"B\". ");
    printList(bfsResultFromB);
    List<Vertex<String>> dfsResultFromA = GraphAlgorithms.dfs(
      vMap.get("A"),
      test
    );
    System.out.print("Test 3: Simple graph dfs starting with \"A\". ");
    printList(dfsResultFromA);
    List<Vertex<String>> dfsResultFromB = GraphAlgorithms.dfs(
      vMap.get("B"),
      test
    );
    System.out.print("Test 4: Simple graph dfs starting with \"B\". ");
    printList(dfsResultFromB);
  }
}
