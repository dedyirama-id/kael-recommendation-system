package org.kael.views;

import org.kael.Screen;
import org.kael.models.Graph;
import org.kael.utils.Terminal;
import org.kael.utils.Text;

/**
 * Layar untuk mencari event berdasarkan ID.
 */
public class ShowGraphScreen implements Screen {
  private final Terminal terminal;
  private final Graph graph;

  /**
   * Membuat find event screen.
   *
   * @param terminal    utilitas terminal untuk I/O.
   * @param graph objek graph loader untuk mendapatkan vertex graph berdasarkan Id.
   */
  public ShowGraphScreen(Terminal terminal, Graph graph) {
    this.terminal = terminal;
    this.graph = graph;
  }

  /**
   * Menampilkan layar untuk mencari event berdasarkan id event.
   *
   * @throws Exception jika terjadi error atau fitur belum diimplementasi.
   */
  @Override
  public void show() throws Exception {
    System.out.println(Text.bold("# Graph Details"));
    this.terminal.printDivider();
    System.out.println(this.graph);
    this.terminal.printDivider();
    this.terminal.waitForInput("Press ENTER to continue...");
  }
}
