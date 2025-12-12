package org.kael.views;

import org.kael.GraphLoader;
import org.kael.Screen;
import org.kael.entities.Event;
import org.kael.entities.Tag;
import org.kael.entities.User;
import org.kael.models.Graph;
import org.kael.utils.Terminal;

/**
 * Layar untuk mencari event berdasarkan ID.
 */
public class FindEventScreen implements Screen {
  private final Terminal terminal;
  private final GraphLoader graphLoader;
  private final Graph<Object> graph;

  public FindEventScreen(Terminal terminal, GraphLoader graphLoader) {
    this.terminal = terminal;
    this.graphLoader = graphLoader;

    // Sama seperti FindUserScreen: build graph dari GraphLoader
    try {
      this.graph = graphLoader.loadGraph();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void show() throws Exception {
    while (true) {
      terminal.clear();
      terminal.printCenter("# Find Event by ID");
      terminal.printDivider();

      // Input ID event
      String id = terminal.getLine("Masukkan ID Event (kosong untuk kembali): ");

      if (id == null || id.trim().isEmpty()) {
        return;
      }

      String trimmedId = id.trim();

      // Ambil event dari map events
      Event event = graphLoader.getEvents().get(trimmedId);

      if (event == null) {
        System.out.print("ERROR: Event dengan ID '" + trimmedId + "' tidak ditemukan!\n");
        terminal.waitForInput("Tekan ENTER untuk melanjutkan...");
        continue;
      }

      // Cari node event di graph
      Graph.Node<Object> node = this.graph.getNode(event);
      if (node == null) {
        System.out.println("Event tidak ditemukan dalam graph!");
        terminal.waitForInput("Tekan ENTER untuk kembali...");
        continue;
      }

      // Tampilkan detail event
      System.out.print("Event ditemukan:\n");
      terminal.printDivider();

      System.out.print("ID Event       : " + event.getId() + "\n");
      System.out.print("Judul          : " + event.getTitle() + "\n");
      System.out.print("Slug           : " + event.getSlug() + "\n");
      System.out.print("Deskripsi      : " + event.getDescription() + "\n");
      System.out.print("Organizer      : " + event.getOrganizer() + "\n");
      System.out.print("Tanggal Mulai  : " + event.getStartDate() + "\n");
      System.out.print("Tanggal Selesai: " + event.getEndDate() + "\n");
      System.out.print("URL            : " + event.getUrl() + "\n");

      // USER TERKAIT
      terminal.printDivider();
      System.out.println("USER TERKAIT:");
      boolean hasUser = false;
      for (Graph.Node<Object> neighbor : node.getNeighbors()) {
        if (neighbor.getValue() instanceof User u) {
          hasUser = true;
          System.out.println("- " + u.getName() + " (" + u.getId() + ")");
        }
      }
      if (!hasUser) {
        System.out.println("(Tidak ada user terkait)");
      }

      // TAG TERKAIT
      terminal.printDivider();
      System.out.println("TAG TERKAIT:");
      boolean hasTag = false;
      for (Graph.Node<Object> neighbor : node.getNeighbors()) {
        if (neighbor.getValue() instanceof Tag t) {
          hasTag = true;
          System.out.println("- " + t.getName() + " (" + t.getId() + ")");
        }
      }
      if (!hasTag) {
        System.out.println("(Tidak ada tag terkait)");
      }

      terminal.printDivider();
      terminal.waitForInput("Tekan ENTER untuk kembali...");
      this.terminal.clear();
    }
  }
}
