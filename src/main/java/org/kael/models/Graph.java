package org.kael.models;

import java.util.*;

/**
 * General Graph class
 */
public class Graph<T> {
  public static class Node<T> {
    private final T value;
    private final Set<Node<T>> neighbors = new LinkedHashSet<>();

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
    } catch (Exception ignored) {
    }
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
}
