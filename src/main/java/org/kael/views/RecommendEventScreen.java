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
   * menghitung skor kedekatan dengan algoritma yang dipilih pengguna,
   * mengurutkan, dan menampilkan beberapa event teratas.
   *
   * @throws Exception jika proses penghitungan atau penampilan layar gagal.
   */
  @Override
  public void show() throws Exception {
    int MAX_RECOMMENDATION_COUNT = 5;

    terminal.clear();
    System.out.println(Text.bold("# Recommend Event"));
    terminal.printDivider();
    int choice = terminal.getOption(
        new String[]{
            "Cancel [x]",
            "Recommend using BFS",
            "Recommend using Personalized PageRank"
        },
        "Choose recommendation algorithm (0-2): "
    );

    if (choice == 0) {
      System.out.println(Text.brightBlack("Cancelled. No recommendation generated."));
      terminal.waitForInput("Press ENTER to continue...");
      return;
    }

    terminal.clear();
    String[] tokens = terminal.getLine("Describe your profile: ").split("\\s+");
    terminal.printDivider();

    Set<Object> seeds = new LinkedHashSet<>();
    for (String token : tokens) {
      if (token.isBlank()) continue;

      Set<Object> matches = graph.search(token);
      if (matches != null && !matches.isEmpty()) {
        seeds.addAll(matches);
      }
    }

    if (seeds.isEmpty()) {
      System.out.println(Text.warning("Sorry, nothing matched your profile!"));
      terminal.printDivider();
      this.terminal.waitForInput("Press ENTER to continue...");
      return;
    }

    Map<Object, Double> scores = switch (choice) {
      case 1 -> {
        yield Personalization.bfsRecommendation(
            this.graph,
            seeds,
            1
        );
      }
      case 2 -> {
        Map<Object, Double> personalization = new HashMap<>();
        for (Object seed : seeds) {
          personalization.merge(seed, 1.0, Double::sum);
        }

        yield Personalization.personalizedPageRank(
            this.graph,
            personalization,
            0.15,
            50,
            1e-6
        );
      }

      default -> {
        System.out.println(
            Text.warning("Invalid choice!")
        );
        terminal.waitForInput("Press ENTER to continue...");
        terminal.clear();
        yield Map.of();
      }
    };

    if (scores.isEmpty()) {
      System.out.println(
          Text.warning("Sorry, there is no node matches your profile!")
      );
      terminal.printDivider();
      terminal.waitForInput("Press ENTER to continue...");
      terminal.clear();
      return;
    }

    List<Scored<Object>> scoredList = Personalization.toScoredList(scores);
    Scored<Object>[] scoredArray = scoredList.toArray(Scored[]::new);
    Sort.selectionSort(scoredArray);

    List<Scored<Event>> events = new ArrayList<>();

    for (Scored<Object> scored : scoredArray) {
      Object value = scored.getValue();
      if (value instanceof Event event) {
        events.add(Scored.of(event, scored.getScore()));
        if (events.size() >= MAX_RECOMMENDATION_COUNT) {
          break;
        }
      }
    }

    for (Scored<Event> scoredEvent : events) {
      Event event = scoredEvent.getValue();
      System.out.println("ID          : " + event.getId());
      System.out.println("Organizer   : " + event.getOrganizer());
      System.out.println("Title       : " + event.getTitle());
      System.out.println("Desc        : " + event.getDescription());
      System.out.println("Relv. Score : " + scoredEvent.getScore());
      terminal.printDivider();
    }

    if (events.isEmpty()) {
      System.out.println(
          Text.warning("Sorry, there is no recommendation for your profile yet!")
      );
    }

    terminal.waitForInput("Press ENTER to continue...");
    terminal.clear();
  }
}
