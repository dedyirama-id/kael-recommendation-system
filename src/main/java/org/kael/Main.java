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
    System.out.println("# Loaded Graph");
    terminal.printDivider();
    System.out.println(graph);
    terminal.printDivider();
    terminal.waitForInput("Press ENTER to continue...");
    terminal.clear();

    Screen res = new RecommendEventScreen(terminal, graph);
    Screen rus = new RecommendUserScreen(terminal, graph);
    Screen fes = new FindEventScreen(terminal, loader);
    Screen fus = new FindUserScreen(terminal, loader);
    Screen ws = new WellcomeScreen(terminal, res, rus, fes, fus);

    terminal.setScreen(ws);
    terminal.showScreen();

    System.out.println(Text.green("Goodbye!"));
  }
}

