package org.kael.views;

import org.kael.Screen;
import org.kael.models.Graph;
import org.kael.utils.Terminal;
import org.kael.utils.Text;

import java.util.LinkedHashSet;
import java.util.Set;

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

    for (String token : tokens) {
      if (token.isBlank()) continue;
      Set<Object> matches = this.graph.search(token);
      if (matches == null || matches.isEmpty()) {
        continue;
      }

      for (Object vertex : new LinkedHashSet<>(matches)) {
        this.graph.bfsScoring(vertex, 2);
      }
    }
  }
}
