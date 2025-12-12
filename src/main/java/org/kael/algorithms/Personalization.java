package org.kael.algorithms;

import java.util.*;
import org.kael.models.Graph;

/**
 * Kelas utilitas yang menyediakan algoritma personalisasi berbasis {@link Graph}.
 */
public final class Personalization {

  /**
   * Mencegah pembuatan instance dari kelas utilitas ini.
   */
  private Personalization() {}

  /**
   * Melakukan penilaian berbasis Breadth-First Search (BFS) mulai dari satu simpul.
   * <p>
   * Untuk setiap simpul yang dikunjungi hingga batas kedalaman tertentu,
   * metode ini menambah skor sebesar {@code 1.0} pada map skor yang diberikan.
   * Skor ditambahkan secara kumulatif dan map skor dimodifikasi langsung.
   * </p>
   *
   * <p>
   * Nilai skor yang dihasilkan berupa frekuensi kunjungan.
   * Untuk mengubahnya menjadi distribusi probabilitas di rentang {@code [0, 1]}
   * dengan total {@code 1.0}, gunakan {@link #normalizeScores(Map)}.
   * </p>
   *
   * @param graph       graf yang akan ditelusuri; jika {@code null}, tidak dilakukan apa pun
   * @param startVertex simpul awal BFS; jika {@code null} atau tidak terdapat dalam graf, proses dihentikan
   * @param maxDepth    kedalaman maksimum BFS; harus bernilai ≥ 0
   * @param scores      map skor yang akan diperbarui; tidak boleh {@code null}
   * @param <T>         tipe nilai simpul
   */
  public static <T> void bfsScoring(
      Graph<T> graph,
      T startVertex,
      int maxDepth,
      Map<T, Double> scores
  ) {
    if (graph == null || startVertex == null || maxDepth < 0 || scores == null) {
      return;
    }

    Graph.Node<T> start = graph.getNode(startVertex);
    if (start == null) {
      return;
    }

    Set<Graph.Node<T>> visited = new HashSet<>();
    Deque<Graph.Node<T>> queue = new ArrayDeque<>();
    Deque<Integer> levels = new ArrayDeque<>();

    visited.add(start);
    queue.add(start);
    levels.add(0);

    while (!queue.isEmpty()) {
      Graph.Node<T> current = queue.poll();
      int level = levels.poll();

      if (level > maxDepth) continue;

      T value = current.getValue();
      scores.merge(value, 1.0, Double::sum);

      if (level == maxDepth) continue;

      for (Graph.Node<T> neighbor : current.getNeighbors()) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.add(neighbor);
          levels.add(level + 1);
        }
      }
    }
  }

  /**
   * Menormalkan nilai skor sehingga total seluruh skor bernilai {@code 1.0}.
   * <p>
   * Jika map skor kosong, {@code null}, atau total skor tidak positif,
   * metode ini tidak melakukan perubahan apa pun.
   * </p>
   *
   * @param scores map skor yang akan dinormalisasi; dimodifikasi langsung
   * @param <T>    tipe nilai simpul
   */
  public static <T> void normalizeScores(Map<T, Double> scores) {
    if (scores == null || scores.isEmpty()) return;

    double sum = 0.0;
    for (double v : scores.values()) sum += v;
    if (sum <= 0.0) return;

    for (Map.Entry<T, Double> e : scores.entrySet()) {
      e.setValue(e.getValue() / sum);
    }
  }

  /**
   * Menghasilkan rekomendasi berbasis BFS menggunakan beberapa simpul awal.
   * <p>
   * Setiap simpul awal diproses menggunakan {@link #bfsScoring(Graph, Object, int, Map)},
   * kemudian seluruh skor yang diperoleh dinormalisasi menjadi distribusi probabilitas.
   * </p>
   *
   * @param graph         graf yang akan diproses; jika {@code null}, hasil berupa map kosong
   * @param startVertices kumpulan simpul awal; entri {@code null} akan diabaikan
   * @param maxDepth      kedalaman maksimum BFS
   * @param <T>           tipe nilai simpul
   * @return map skor hasil normalisasi dengan rentang nilai {@code [0, 1]}
   */
  public static <T> Map<T, Double> bfsRecommendation(
      Graph<T> graph,
      Iterable<T> startVertices,
      int maxDepth
  ) {
    Map<T, Double> scores = new HashMap<>();
    if (graph == null || startVertices == null || maxDepth < 0) {
      return scores;
    }

    for (T start : startVertices) {
      if (start == null) continue;
      bfsScoring(graph, start, maxDepth, scores);
    }

    normalizeScores(scores);
    return scores;
  }

  /**
   * Mengonversi map skor menjadi daftar {@link Scored}.
   * <p>
   * Setiap entri map dikonversi menjadi sebuah objek {@code Scored<T>}
   * dengan nilai skor bertipe {@code double}.
   * </p>
   *
   * @param scores map skor yang akan dikonversi
   * @param <T>    tipe entitas yang diberi skor
   * @return daftar objek {@code Scored<T>}
   */
  public static <T> List<Scored<T>> toScoredList(Map<T, ? extends Number> scores) {
    List<Scored<T>> list = new ArrayList<>();
    for (Map.Entry<T, ? extends Number> e : scores.entrySet()) {
      double score = e.getValue().doubleValue();
      list.add(Scored.of(e.getKey(), score));
    }
    return list;
  }

  /**
   * Menghitung nilai Personalized PageRank (PPR) untuk seluruh simpul dalam graf.
   * <p>
   * Graf diasumsikan memiliki bobot sisi seragam. Vektor personalisasi dapat berisi
   * nilai mentah (tidak perlu dinormalisasi). Entri dengan nilai nol, negatif, atau
   * tidak terdapat dalam graf akan diabaikan. Jika seluruh nilai personalisasi tidak valid,
   * digunakan distribusi uniform.
   * </p>
   *
   * @param graph           graf yang dihitung PPR-nya; jika {@code null}, hasil berupa map kosong
   * @param personalization map nilai personalisasi; boleh {@code null}
   * @param alpha           probabilitas restart (0 < alpha < 1)
   * @param maxIterations   jumlah iterasi maksimum; harus > 0
   * @param tolerance       ambang konvergensi; harus > 0
   * @param <T>             tipe nilai simpul
   * @return map nilai PPR untuk setiap simpul
   * @throws IllegalArgumentException jika parameter numerik tidak valid
   */
  public static <T> Map<T, Double> personalizedPageRank(
      Graph<T> graph,
      Map<T, Double> personalization,
      double alpha,
      int maxIterations,
      double tolerance
  ) {
    if (graph == null) {
      return Map.of();
    }
    if (alpha <= 0.0 || alpha >= 1.0) {
      throw new IllegalArgumentException("alpha harus berada dalam rentang (0, 1)");
    }
    if (maxIterations <= 0) {
      throw new IllegalArgumentException("maxIterations harus > 0");
    }
    if (tolerance <= 0.0) {
      throw new IllegalArgumentException("tolerance harus > 0");
    }

    // Mengambil daftar node
    List<Graph.Node<T>> nodes = new ArrayList<>(graph.getNodes());
    int n = nodes.size();
    if (n == 0) {
      return Map.of();
    }

    // Memetakan node dan value ke indeks array
    Map<Graph.Node<T>, Integer> nodeIndex = new HashMap<>(n);
    Map<T, Integer> valueIndex = new HashMap<>(n);

    for (int i = 0; i < n; i++) {
      Graph.Node<T> node = nodes.get(i);
      nodeIndex.put(node, i);
      valueIndex.put(node.getValue(), i);
    }

    // Membangun adjacency list
    int[][] neighbors = new int[n][];
    for (int i = 0; i < n; i++) {
      Graph.Node<T> node = nodes.get(i);
      Set<Graph.Node<T>> neighSet = node.getNeighbors();
      int[] arr = new int[neighSet.size()];
      int k = 0;
      for (Graph.Node<T> neigh : neighSet) {
        Integer idx = nodeIndex.get(neigh);
        if (idx != null) arr[k++] = idx;
      }
      if (k < arr.length) {
        neighbors[i] = Arrays.copyOf(arr, k);
      } else {
        neighbors[i] = arr;
      }
    }

    // Menyusun vektor personalisasi
    double[] p = new double[n];
    if (personalization != null && !personalization.isEmpty()) {
      for (Map.Entry<T, Double> e : personalization.entrySet()) {
        Double w = e.getValue();
        if (w == null || w <= 0.0) continue;

        Integer idx = valueIndex.get(e.getKey());
        if (idx != null) p[idx] += w;
      }
    }

    double totalP = 0.0;
    for (double v : p) totalP += v;

    if (totalP <= 0.0) {
      double uniform = 1.0 / n;
      Arrays.fill(p, uniform);
    } else {
      for (int i = 0; i < n; i++) p[i] /= totalP;
    }

    // Inisialisasi vektor peringkat
    double[] r = Arrays.copyOf(p, n);
    double[] rNext = new double[n];

    // Iterasi PageRank
    for (int iter = 0; iter < maxIterations; iter++) {
      for (int i = 0; i < n; i++) rNext[i] = alpha * p[i];

      for (int i = 0; i < n; i++) {
        int[] out = neighbors[i];
        if (out.length == 0) continue;

        double share = (1 - alpha) * r[i] / out.length;
        for (int j : out) rNext[j] += share;
      }

      double maxDiff = 0.0;
      for (int i = 0; i < n; i++) {
        double diff = Math.abs(rNext[i] - r[i]);
        if (diff > maxDiff) maxDiff = diff;
        r[i] = rNext[i];
      }

      if (maxDiff < tolerance) break;
    }

    // Konversi hasil ke map
    Map<T, Double> result = new HashMap<>(n);
    for (int i = 0; i < n; i++) {
      result.put(nodes.get(i).getValue(), r[i]);
    }
    return result;
  }
}
