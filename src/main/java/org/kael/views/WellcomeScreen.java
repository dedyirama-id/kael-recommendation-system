package org.kael.views;

import org.kael.Screen;
import org.kael.utils.Terminal;

/**
 * Layar selamat datang untuk navigasi awal aplikasi.
 */
public class WellcomeScreen implements Screen {
  private final Terminal terminal;
  private final Screen recommendEventScreen;
  private final Screen recommendUserScreen;
  private final Screen findEventScreen;
  private final Screen findUserScreen;
  private final Screen showGraphScreen;

  /**
   * Membuat layar selamat datang.
   *
   * @param terminal             utilitas terminal untuk I/O.
   * @param recommendEventScreen layar rekomendasi event yang akan dipanggil ketika dipilih.
   */
  public WellcomeScreen(Terminal terminal,
                        Screen recommendEventScreen,
                        Screen recommendUserScreen,
                        Screen findEventScreen,
                        Screen findUserScreen,
                        Screen showGraphScreen) {
    this.terminal = terminal;
    this.recommendEventScreen = recommendEventScreen;
    this.recommendUserScreen = recommendUserScreen;
    this.findEventScreen = findEventScreen;
    this.findUserScreen = findUserScreen;
    this.showGraphScreen = showGraphScreen;
  }

  /**
   * Menampilkan menu utama dengan pilihan navigasi atau keluar.
   *
   * @throws Exception jika navigasi layar gagal atau fitur belum diimplementasi.
   */
  @Override
  public void show() throws Exception {
    while (true) {
      this.terminal.clear();
      this.printLogo();
      this.terminal.printCenter("Welcome To Ka'el Recommendation System!");
      this.terminal.printDivider();
      int choice = this.terminal.getOption(new String[]{
          "Exit [x]",
          "Recommend Event",
          "Recommend User",
          "Find Event by Id",
          "Find User by Id",
          "Show Graph",
      }, "Choose (0-5): ");

      switch (choice) {
        case 0 -> {
          return;
        }
        case 1 -> {
          this.recommendEventScreen.show();
        }
        case 2 -> {
          this.recommendUserScreen.show();
        }
        case 3 -> {
          this.findEventScreen.show();
        }
        case 4 -> {
          this.findUserScreen.show();
        }
        case 5 -> {
          this.showGraphScreen.show();
        }
      }
    }
  }

  /**
   * Mencetak logo ASCII art aplikasi ke terminal.
   */
  private void printLogo() {
    String logo = """
        ██╗  ██╗ █████╗ ███████╗██╗    \s
        ██║ ██╔╝██╔══██╗██╔════╝██║    \s
        █████╔╝ ███████║█████╗  ██║    \s
        ██╔═██╗ ██╔══██║██╔══╝  ██║    \s
        ██║  ██╗██║  ██║███████╗███████╗
        ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
        """;

    String[] logoParts = logo.split("\n");
    for (String part : logoParts) {
      this.terminal.printCenter(part);
    }
  }
}
