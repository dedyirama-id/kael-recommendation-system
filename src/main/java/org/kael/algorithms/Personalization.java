package org.kael.algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kael.models.Graph;

/**
 * Utility untuk algoritma personalisasi (BFS scoring, PPR, dll.).
 */
public final class Personalization {

  /**
   * Mencegah instansiasi karena kelas hanya berisi utilitas statis.
   */
  private Personalization() {
  }

  /**
   * BFS-based scoring:
   * - Mulai dari startVertex, BFS sampai maxLevel.
   * - Setiap Node yang dikunjungi menambah skor +1 pada value-nya di map scores.
   * - Skor diakumulasi ke Map<T, Integer> yang diberikan pemanggil.
   *
   * @param graph       graf yang ditelusuri.
   * @param startVertex simpul awal penelusuran.
   * @param maxLevel    kedalaman maksimum BFS.
   * @param scores      map akumulator skor; akan dimodifikasi in-place.
   */
  public static <T> void bfsScoring(
      Graph<T> graph,
      T startVertex,
      int maxLevel,
      Map<T, Integer> scores
  ) {
    if (graph == null || startVertex == null || maxLevel < 0 || scores == null) {
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

      if (level > maxLevel) {
        continue;
      }

      T value = current.getValue();
      scores.merge(value, 1, Integer::sum);

      if (level == maxLevel) {
        continue;
      }

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
   * Utility untuk mengubah map skor menjadi list Scored<T>.
   *
   * @param scores map berisi entity serta nilai skor numerik.
   * @param <T>    tipe nilai yang dibungkus.
   * @return list Scored yang dapat diurutkan.
   */
  public static <T> List<Scored<T>> toScoredList(Map<T, ? extends Number> scores) {
    List<Scored<T>> list = new ArrayList<>();
    for (Map.Entry<T, ? extends Number> e : scores.entrySet()) {
      double score = e.getValue().doubleValue();
      list.add(Scored.of(e.getKey(), score));
    }
    return list;
  }
}
