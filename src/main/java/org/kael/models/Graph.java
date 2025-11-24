package org.kael.models;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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

  public Node<T> addVertex(T value) {
    return vertices.computeIfAbsent(value, Node::new);
  }

  public Node<T> getNode(T value) {
    return vertices.get(value);
  }

  public void addEdge(T from, T to) {
    Node<T> u = addVertex(from);
    Node<T> v = addVertex(to);

    u.neighbors.add(v);
    if (!directed) {
      v.neighbors.add(u);
    }
  }

  public void removeEdge(T from, T to) {
    Node<T> u = vertices.get(from);
    Node<T> v = vertices.get(to);
    if (u != null && v != null) {
      u.neighbors.remove(v);
      if (!directed) {
        v.neighbors.remove(u);
      }
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
}
