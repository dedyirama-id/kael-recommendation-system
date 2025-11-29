package org.kael.views;

import org.kael.GraphLoader;
import org.kael.Screen;
import org.kael.utils.Terminal;

/**
 * Layar untuk mencari event berdasarkan ID.
 */
public class FindEventScreen implements Screen {
  private final Terminal terminal;
  private final GraphLoader graphLoader;

  /**
   * Membuat find event screen.
   *
   * @param terminal    utilitas terminal untuk I/O.
   * @param graphLoader objek graph loader untuk mendapatkan vertex graph berdasarkan Id.
   */
  public FindEventScreen(Terminal terminal, GraphLoader graphLoader) {
    this.terminal = terminal;
    this.graphLoader = graphLoader;
  }

  /**
   * Menampilkan layar untuk mencari event berdasarkan id event.
   *
   * @throws Exception jika terjadi error atau fitur belum diimplementasi.
   */
  @Override
  public void show() throws Exception {
    while (true) {
      this.terminal.clear();
      this.terminal.printCenter("# Lorem Ipsum");
      this.terminal.printDivider();

      throw new Exception("Method not implemented yet!");
      // TODO:
      // - Ambil input id event
      // - Dapatkan dan simpan objek event dari graphLoader
      // - Jika event tidak ditemukan, print error
      // - Jika event ditemukan, print semua detail dari event tersebut
    }
  }
}
