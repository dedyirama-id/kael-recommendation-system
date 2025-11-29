package org.kael.algorithms;

import org.kael.models.Graph;

import java.util.*;

/**
 * Utility untuk algoritma personalisasi (BFS scoring, PPR, dll.).
 */
public final class Personalization {

  private Personalization() {
  }

  /**
   * BFS-based scoring:
   * - Mulai dari startVertex, BFS sampai maxLevel.
   * - Setiap Node yang dikunjungi menambah skor +1 pada value-nya di map scores.
   * - Skor diakumulasi ke Map<T, Integer> yang diberikan pemanggil.
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

      // Tambah skor untuk value node ini
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
