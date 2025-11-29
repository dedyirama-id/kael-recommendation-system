package org.kael.models;

import java.util.*;

/**
 * Struktur graf generik sederhana yang mendukung graf berarah maupun tak-berarah,
 * lengkap dengan operasi dasar seperti tambah/hapus vertex, edge, serta pencarian.
 */
public class Graph<T> {
  /**
   * Node wrapper yang menyimpan nilai vertex dan daftar neighbor.
   */
  public static class Node<T> {
    private final T value;
    private final Set<Node<T>> neighbors = new LinkedHashSet<>();

    /**
     * Membuat node baru untuk nilai tertentu.
     *
     * @param value nilai vertex yang dibungkus.
     */
    private Node(T value) {
      this.value = value;
    }

    /**
     * Mengambil nilai yang disimpan di vertex.
     *
     * @return nilai generik vertex.
     */
    public T getValue() {
      return value;
    }

    /**
     * Mengambil daftar neighbor secara tidak dapat dimodifikasi.
     *
     * @return set neighbor.
     */
    public Set<Node<T>> getNeighbors() {
      return Collections.unmodifiableSet(neighbors);
    }

    /**
     * Representasi string sederhana dari nilai node.
     *
     * @return nilai dalam bentuk string.
     */
    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  private final Map<T, Node<T>> vertices = new LinkedHashMap<>();
  private final boolean directed;

  /**
   * Membuat graf baru.
   *
   * @param directed true jika graf berarah, false jika tidak berarah.
   */
  public Graph(boolean directed) {
    this.directed = directed;
  }

  /**
   * Menambahkan vertex baru ke graf jika belum ada.
   *
   * @param value nilai vertex.
   * @return true jika vertex baru ditambahkan, false jika sudah ada.
   */
  public boolean addVertex(T value) {
    if (vertices.containsKey(value)) return false;
    vertices.put(value, new Node<>(value));
    return true;
  }

  /**
   * Mengambil atau membuat vertex berdasarkan nilai.
   *
   * @param value nilai vertex.
   * @return node yang sudah ada atau baru dibuat.
   */
  private Node<T> getOrCreateVertex(T value) {
    return vertices.computeIfAbsent(value, Node::new);
  }

  /**
   * Mengambil node berdasarkan nilai vertex.
   *
   * @param value nilai vertex.
   * @return node jika ada, null jika tidak ditemukan.
   */
  public Node<T> getNode(T value) {
    return vertices.get(value);
  }

  /**
   * Menambahkan edge dari vertex asal ke tujuan.
   * Untuk graf tak berarah, edge akan ditambahkan dua arah.
   *
   * @param from nilai vertex asal.
   * @param to   nilai vertex tujuan.
   */
  public void addEdge(T from, T to) {
    Node<T> u = getOrCreateVertex(from);
    Node<T> v = getOrCreateVertex(to);

    u.neighbors.add(v);
    if (!directed) {
      v.neighbors.add(u);
    }
  }

  /**
   * Menghapus edge antara dua vertex jika ada.
   *
   * @param from vertex asal.
   * @param to   vertex tujuan.
   */
  public void removeEdge(T from, T to) {
    Node<T> u = vertices.get(from);
    Node<T> v = vertices.get(to);
    if (u == null || v == null) return;

    u.neighbors.remove(v);
    if (!directed) {
      v.neighbors.remove(u);
    }
  }

  /**
   * Menghapus vertex dari graf beserta referensinya dari neighbor lain.
   *
   * @param value nilai vertex yang dihapus.
   */
  public void removeVertex(T value) {
    Node<T> node = vertices.remove(value);
    if (node == null) return;

    for (Node<T> n : vertices.values()) {
      n.neighbors.remove(node);
    }
  }

  /**
   * Mengecek apakah edge antara dua vertex ada.
   *
   * @param from vertex asal.
   * @param to   vertex tujuan.
   * @return true jika edge ada.
   */
  public boolean hasEdge(T from, T to) {
    Node<T> u = vertices.get(from);
    Node<T> v = vertices.get(to);
    return u != null && v != null && u.neighbors.contains(v);
  }

  /**
   * Mengambil seluruh node dalam graf.
   *
   * @return koleksi node yang tidak dapat dimodifikasi.
   */
  public Collection<Node<T>> getNodes() {
    return Collections.unmodifiableCollection(vertices.values());
  }

  /**
   * Mengetahui apakah graf berarah atau tidak.
   *
   * @return true jika graf berarah.
   */
  public boolean isDirected() {
    return directed;
  }

  /**
   * Representasi string sederhana yang menampilkan adjacency list.
   *
   * @return string adjacency list.
   */
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

  /**
   * Menggabungkan seluruh field string dari objek menjadi satu string pencarian.
   *
   * @param obj objek yang ingin dipetakan.
   * @return string gabungan field teks.
   */
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

  /**
   * Melakukan pencarian sederhana berbasis substring pada seluruh vertex.
   *
   * @param query kata kunci yang dicari.
   * @return set vertex yang field teksnya mengandung query (case-insensitive).
   */
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

  /**
   * Mengubah koleksi node ke array.
   *
   * @return array Node berukuran sesuai jumlah vertex.
   */
  public Node<T>[] toArray() {
    @SuppressWarnings("unchecked")
    Node<T>[] arr = vertices.values().toArray(new Node[0]);
    return arr;
  }
}
