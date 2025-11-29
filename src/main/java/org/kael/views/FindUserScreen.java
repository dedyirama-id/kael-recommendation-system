package org.kael.views;

import org.kael.GraphLoader;
import org.kael.Screen;
import org.kael.utils.Terminal;

/**
 * Layar untuk mencari user berdasarkan ID.
 */
public class FindUserScreen implements Screen {
  private final Terminal terminal;
  private final GraphLoader graphLoader;

  /**
   * Membuat find user screen.
   *
   * @param terminal    utilitas terminal untuk I/O.
   * @param graphLoader objek graph loader untuk mendapatkan vertex graph berdasarkan Id.
   */
  public FindUserScreen(Terminal terminal, GraphLoader graphLoader) {
    this.terminal = terminal;
    this.graphLoader = graphLoader;
  }

  /**
   * Menampilkan layar untuk mencari user berdasarkan id user.
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
      // - Ambil input id user
      // - Dapatkan dan simpan objek user dari graphLoader
      // - Jika user tidak ditemukan, print error
      // - Jika user ditemukan, print semua detail dari user tersebut
    }
  }
}
