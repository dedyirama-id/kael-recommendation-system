package org.kael.views;

import org.kael.Screen;
import org.kael.algorithms.Personalization;
import org.kael.algorithms.Scored;
import org.kael.algorithms.Sort;
import org.kael.entities.Event;
import org.kael.models.Graph;
import org.kael.utils.Terminal;
import org.kael.utils.Text;

import java.util.*;

/**
 * Layar untuk memberikan rekomendasi user berbasis input deskripsi event.
 */
public class RecommendUserScreen implements Screen {
  private final Terminal terminal;
  private final Graph<Object> graph;

  /**
   * Membuat layar rekomendasi user.
   *
   * @param terminal utilitas terminal untuk I/O.
   * @param graph    graf objek pengguna, event, dan tag.
   */
  public RecommendUserScreen(Terminal terminal, Graph<Object> graph) {
    this.terminal = terminal;
    this.graph = graph;
  }

  /**
   * Menjalankan alur rekomendasi: menerima deskripsi event,
   * menghitung skor kedekatan mengurutkan, dan menampilkan beberapa event teratas.
   *
   * @throws Exception jika proses penghitungan atau penampilan layar gagal.
   */
  @Override
  public void show() throws Exception {
    throw new Exception("Method not implemented yet!");

    // TODO:
    // - Copy kode show() dari class RecommendEventScreen
    // - Sesuaikan kode untuk menerima deskripsi event, kemudian merekomendasikan user yang relevan untuk event tersebut.
  }
}
