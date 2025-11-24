package org.kael.views;

import org.kael.Screen;
import org.kael.utils.Terminal;

public class WellcomeScreen implements Screen {
  private final Terminal terminal;

  public WellcomeScreen(Terminal terminal) {
    this.terminal = terminal;
  }

  @Override
  public void show() throws Exception {
    while (true) {
      this.terminal.clear();
      this.terminal.printDivider();
      this.printLogo();
      this.terminal.printCenter("Welcome To Ka'el Recommendation System!");
      this.terminal.printDivider();
      int choice = this.terminal.getOption(new String[]{
          "Exit [x]",
          "Recommend Event",
          "Recommend User"
      }, "Choose (0-2): ");

      switch (choice) {
        case 0 -> {
            return;
            }
        case 1 -> {
            Screen res = new RecommendEventScreen(this.terminal);
            res.show();
            }
        case 2 -> throw new Exception("Not implemented yet!");
      }
    }
  }

  private void printLogo() {
    String logo =  """
        ██╗  ██╗ █████╗ ███████╗██╗    \s
        ██║ ██╔╝██╔══██╗██╔════╝██║    \s
        █████╔╝ ███████║█████╗  ██║    \s
        ██╔═██╗ ██╔══██║██╔══╝  ██║    \s
        ██║  ██╗██║  ██║███████╗███████╗
        ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
        """;

    String[] logoParts = logo.split("\n");
    for (String part: logoParts) {
      this.terminal.printCenter(part);
    }
  }
}
