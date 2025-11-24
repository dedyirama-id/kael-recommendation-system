package org.kael.views;

import org.kael.Screen;
import org.kael.utils.Terminal;
import org.kael.utils.Text;

public class RecommendEventScreen implements Screen {
  private final Terminal terminal;

  public RecommendEventScreen(Terminal terminal) {
    this.terminal = terminal;
  }

  @Override
  public void show() throws Exception {
    while (true) {
      this.terminal.clear();
      this.terminal.printDivider();
      System.out.println(Text.bold("# Recommend Event"));
      this.terminal.printDivider();
      String profile = this.terminal.getLine("Describe your profile: ");

      throw new Exception("Method not implemented yet!");
    }
  }
}
