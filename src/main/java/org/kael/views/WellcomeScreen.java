package org.kael.views;

import org.kael.Screen;
import org.kael.utils.Terminal;

public class WellcomeScreen implements Screen {
  private final Terminal terminal;
  private final Screen recommendEventScreen;

  public WellcomeScreen(Terminal terminal, Screen recommendEventScreen) {
    this.terminal = terminal;
    this.recommendEventScreen = recommendEventScreen;
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
            this.recommendEventScreen.show();
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
