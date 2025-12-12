package org.kael.views;

import org.kael.GraphLoader;
import org.kael.Screen;
import org.kael.entities.Event;
import org.kael.entities.Tag;
import org.kael.entities.User;
import org.kael.models.Graph;
import org.kael.utils.Terminal;

import java.util.Scanner;

/**
 * Layar untuk mencari user berdasarkan ID.
 */
public class FindUserScreen implements Screen {
  private final Terminal terminal;
  private final GraphLoader graphLoader;
  private final Graph<Object> graph;

  /**
   * Membuat find user screen.
   *
   * @param terminal    utilitas terminal untuk I/O.
   * @param graphLoader objek graph loader untuk mendapatkan vertex graph berdasarkan Id.
   */
  public FindUserScreen(Terminal terminal, GraphLoader graphLoader) {
    this.terminal = terminal;
    this.graphLoader = graphLoader;

    try {
      this.graph = graphLoader.loadGraph();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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
      this.terminal.printCenter("# Find User");
      this.terminal.printDivider();

      // Gunakan Scanner untuk membaca input karena Terminal tidak menyediakan getInput
      System.out.print("Masukkan ID User: ");
      Scanner sc = new Scanner(System.in);
      String id = sc.nextLine().trim();

      // Dapatkan objek user dari graphLoader
      User user = this.graphLoader.getUsers().get(id);

      // Jika user tidak ditemukan
      if (user == null) {
        System.out.println("User dengan ID tersebut tidak ditemukan!");
        this.terminal.waitForInput("Tekan ENTER untuk kembali...");
        return;
      }

      // Dapatkan node user dari graph
      Graph.Node<Object> node = this.graph.getNode(user);

      if (node == null) {
        System.out.println("User tidak ditemukan dalam graph!");
        this.terminal.waitForInput("Tekan ENTER untuk kembali...");
        return;
      }

      // Print detail user (menggunakan System.out agar tidak tergantung method Terminal yang tidak ada)
      System.out.println();
      this.terminal.printCenter("User ditemukan!");
      this.terminal.printDivider();
      System.out.println("ID      : " + user.getId());
      System.out.println("Nama    : " + user.getName());
      System.out.println("Profil  : " + user.getProfile());

      // Print event terkait
      this.terminal.printDivider();
      System.out.println("EVENT TERKAIT:");
      boolean hasEvent = false;

      for (Graph.Node<Object> neighbor : node.getNeighbors()) {
        if (neighbor.getValue() instanceof Event e) {
          hasEvent = true;
          System.out.println("- " + e.getTitle() + " (" + e.getId() + ")");
        }
      }
      if (!hasEvent) System.out.println("(Tidak ada event)");

      // Print tag terkait
      this.terminal.printDivider();
      System.out.println("TAG TERKAIT:");
      boolean hasTag = false;

      for (Graph.Node<Object> neighbor : node.getNeighbors()) {
        if (neighbor.getValue() instanceof Tag t) {
          hasTag = true;
          System.out.println("- " + t.getName() + " (" + t.getId() + ")");
        }
      }
      if (!hasTag) System.out.println("(Tidak ada tag)");

      this.terminal.printDivider();
      this.terminal.waitForInput("Tekan ENTER untuk kembali...");
      return;
    }
  }
}
