package org.kael.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kael.Screen;
import org.kael.algorithms.Personalization;
import org.kael.algorithms.Scored;
import org.kael.algorithms.Sort;
import org.kael.entities.Event;
import org.kael.models.Graph;
import org.kael.utils.Terminal;
import org.kael.utils.Text;

/**
 * Layar untuk memberikan rekomendasi event berbasis input deskripsi profil.
 */
public class RecommendEventScreen implements Screen {
  private final Terminal terminal;
  private final Graph<Object> graph;

  /**
   * Membuat layar rekomendasi event.
   *
   * @param terminal utilitas terminal untuk I/O.
   * @param graph    graf objek pengguna, event, dan tag.
   */
  public RecommendEventScreen(Terminal terminal, Graph<Object> graph) {
    this.terminal = terminal;
    this.graph = graph;
  }

  /**
   * Menjalankan alur rekomendasi: menerima deskripsi profil,
   * menghitung skor kedekatan dengan BFS personalisasi, mengurutkan,
   * dan menampilkan beberapa event teratas.
   *
   * @throws Exception jika proses penghitungan atau penampilan layar gagal.
   */
  @Override
  public void show() throws Exception {
    this.terminal.clear();
    this.terminal.printDivider();
    System.out.println(Text.bold("# Recommend Event"));
    this.terminal.printDivider();

    String[] tokens = this.terminal.getLine("Describe your profile: ").split("\\s+");

    Map<Object, Integer> scores = new HashMap<>();

    for (String token : tokens) {
      if (token.isBlank()) continue;
      Set<Object> matches = this.graph.search(token);
      if (matches == null || matches.isEmpty()) {
        continue;
      }

      for (Object vertex : new LinkedHashSet<>(matches)) {
        Personalization.bfsScoring(this.graph, vertex, 1, scores);
      }
    }

    List<Scored<Object>> scoredList = Personalization.toScoredList(scores);

    Scored<Object>[] scoredArray = scoredList.toArray(Scored[]::new);
    Sort.selectionSort(scoredArray);

    final int MAX_EVENTS = 5;
    List<Scored<Event>> events = new ArrayList<>();

    for (Scored<Object> scored : scoredArray) {
      Object value = scored.getValue();
      if (value instanceof Event event) {
        events.add(Scored.of(event, scored.getScore()));
        if (events.size() >= MAX_EVENTS) {
          break;
        }
      }
    }

    for (Scored<Event> scoredEvent : events) {
      Event event = scoredEvent.getValue();
      terminal.printDivider();
      System.out.println("ID          : " + event.getId());
      System.out.println("Organizer   : " + event.getOrganizer());
      System.out.println("Title       : " + event.getTitle());
      System.out.println("Desc        : " + event.getDescription());
      System.out.println("Relv. Score : " + scoredEvent.getScore());
    }

    if(events.isEmpty()) {
      System.out.println(Text.warning("Maaf, belum ada rekomendasi yang cocok untuk profil anda."));
    }
    terminal.waitForInput(Text.brightBlack("Press ENTER to continue..."));
    this.terminal.clear();
  }
}
