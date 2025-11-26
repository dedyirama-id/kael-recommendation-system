package org.kael.models;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * General Graph class
 */
public class Graph<T> {
  public static class Node<T> {
    private final T value;
    private final Set<Node<T>> neighbors = new LinkedHashSet<>();
    public int score = 0;

    private Node(T value) {
      this.value = value;
    }

    public T getValue() {
      return value;
    }

    public Set<Node<T>> getNeighbors() {
      return Collections.unmodifiableSet(neighbors);
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  private final Map<T, Node<T>> vertices = new LinkedHashMap<>();
  private final boolean directed;

  public Graph(boolean directed) {
    this.directed = directed;
  }

  public boolean addVertex(T value) {
    if (vertices.containsKey(value)) return false;
    vertices.put(value, new Node<>(value));
    return true;
  }

  private Node<T> getOrCreateVertex(T value) {
    return vertices.computeIfAbsent(value, Node::new);
  }

  public Node<T> getNode(T value) {
    return vertices.get(value);
  }

  public void addEdge(T from, T to) {
    Node<T> u = getOrCreateVertex(from);
    Node<T> v = getOrCreateVertex(to);

    u.neighbors.add(v);
    if (!directed) {
      v.neighbors.add(u);
    }
  }

  public void removeEdge(T from, T to) {
    Node<T> u = vertices.get(from);
    Node<T> v = vertices.get(to);
    if (u == null || v == null) return;

    u.neighbors.remove(v);
    if (!directed) {
      v.neighbors.remove(u);
    }
  }

  public void removeVertex(T value) {
    Node<T> node = vertices.remove(value);
    if (node == null) return;

    for (Node<T> n : vertices.values()) {
      n.neighbors.remove(node);
    }
  }

  public boolean hasEdge(T from, T to) {
    Node<T> u = vertices.get(from);
    Node<T> v = vertices.get(to);
    return u != null && v != null && u.neighbors.contains(v);
  }

  public Collection<Node<T>> getNodes() {
    return Collections.unmodifiableCollection(vertices.values());
  }

  public boolean isDirected() {
    return directed;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Node<T> n : vertices.values()) {
      sb.append(n.value).append(" -> ");
      for (Node<T> neigh : n.neighbors) {
        sb.append(neigh.value).append(" ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
  
  private String flattenObject(T obj) {
    StringBuilder sb = new StringBuilder();
    try {
      for (var field : obj.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        Object value = field.get(obj);
        if (value instanceof String s) {
          sb.append(s.toLowerCase()).append(" ");
        }
      }
    } catch (Exception ignored) {}
    return sb.toString();
  }

  public Set<T> search(String query) {
    Set<T> results = new LinkedHashSet<>();
    String q = query.toLowerCase();

    for (T value : vertices.keySet()) {
      String flat = flattenObject(value);
      if (flat.contains(q)) {
        results.add(value);
      }
    }
    return results;
  }

  public Node<T>[] toArray() {
    @SuppressWarnings("unchecked")
    Node<T>[] arr = vertices.values().toArray(new Node[0]);
    return arr;
  }

  public Map<T, Integer> getScores() {
    Map<T, Integer> scores = new LinkedHashMap<>();
    for (Node<T> node : vertices.values()) {
      scores.put(node.value, node.score);
    }
    return Collections.unmodifiableMap(scores);
  }

  public void bfsScoring(T startVertex, int maxLevel) {
    Node<T> start = vertices.get(startVertex);
    if (start == null) {
      System.out.println("Start vertex not found: " + startVertex);
      return;
    }

    Set<Node<T>> visited = new HashSet<>();
    Queue<Node<T>> queue = new ArrayDeque<>();
    Queue<Integer> levels = new ArrayDeque<>();

    visited.add(start);
    queue.add(start);
    levels.add(0);

    while (!queue.isEmpty()) {
      Node<T> current = queue.poll();
      int level = levels.poll();

      if (level > maxLevel) continue;
      current.score += 1;

      if (level == maxLevel) continue;
      for (Node<T> neighbor : current.neighbors) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.add(neighbor);
          levels.add(level + 1);
        }
      }
    }
  }
}
