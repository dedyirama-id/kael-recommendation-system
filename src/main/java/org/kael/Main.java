package org.kael;

import org.kael.models.Graph;
import org.kael.utils.Terminal;
import org.kael.utils.Text;
import org.kael.views.*;

/**
 * Entry point untuk aplikasi Ka'el Recommendation System berbasis terminal.
 * Kelas ini menyiapkan utilitas terminal, memuat graph dari sumber CSV,
 * lalu memulai tampilan awal aplikasi.
 */
public class Main {
  /**
   * Menjalankan aplikasi CLI rekomendasi.
   *
   * @param args argumen baris perintah, tidak digunakan.
   * @throws Exception ketika proses inisialisasi atau render screen gagal.
   */
  public static void main(String[] args) throws Exception {
    Terminal terminal = new Terminal(100);
    GraphLoader loader = new GraphLoader();
    Graph<Object> graph = loader.loadGraph();

    Screen res = new RecommendEventScreen(terminal, graph);
    Screen rus = new RecommendUserScreen(terminal, graph);
    Screen fes = new FindEventScreen(terminal, loader);
    Screen fus = new FindUserScreen(terminal, loader);
    Screen sgs = new ShowGraphScreen(terminal, graph);
    Screen ws = new WellcomeScreen(terminal, res, rus, fes, fus, sgs);

    terminal.setScreen(ws);
    terminal.showScreen();

    terminal.clear();
    System.out.println(Text.green("Goodbye!"));
  }
}

