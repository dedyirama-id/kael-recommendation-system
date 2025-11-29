package org.kael.views;

import org.kael.Screen;
import org.kael.algorithms.Personalization;
import org.kael.algorithms.Scored;
import org.kael.algorithms.Sort;
import org.kael.entities.Event;
import org.kael.models.Graph;
import org.kael.utils.Terminal;
import org.kael.utils.Text;

import java.util.*;

public class RecommendEventScreen implements Screen {
  private final Terminal terminal;
  private final Graph<Object> graph;

  public RecommendEventScreen(Terminal terminal, Graph<Object> graph) {
    this.terminal = terminal;
    this.graph = graph;
  }

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

    Scored<Object>[] scoredArray = scoredList.toArray(new Scored[0]);
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

    if(events.size() == 0) {
      System.out.println(Text.warning("Maaf, belum ada rekomendasi yang cocok untuk profil anda."));
    }
    terminal.waitForInput(Text.brightBlack("Press ENTER to continue..."));
    this.terminal.clear();
  }
}
