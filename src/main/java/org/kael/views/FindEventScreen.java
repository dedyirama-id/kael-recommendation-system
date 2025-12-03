package org.kael.views;

import org.kael.GraphLoader;
import org.kael.Screen;
import org.kael.entities.Event;
import org.kael.utils.Terminal;

/**
 * Layar untuk mencari event berdasarkan ID.
 */
public class FindEventScreen implements Screen {
  private final Terminal terminal;
  private final GraphLoader graphLoader;

  public FindEventScreen(Terminal terminal, GraphLoader graphLoader) {
    this.terminal = terminal;
    this.graphLoader = graphLoader;
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
        return; // kembali ke menu sebelumnya
      }

      // Ambil event dari map events
      Event event = graphLoader.getEvents().get(id.trim());

      if (event == null) {
        System.out.print("ERROR: Event dengan ID '" + id + "' tidak ditemukan!\n");
        terminal.waitForInput("Tekan ENTER untuk melanjutkan...");
        continue;
      }

      // Tampilkan detail event
      System.out.print("Event ditemukan:\n");
      terminal.printDivider();

      System.out.print("ID Event     : " + event.getId() + "\n");
      System.out.print("Judul        : " + event.getTitle() + "\n");
      System.out.print("Slug         : " + event.getSlug() + "\n");
      System.out.print("Deskripsi    : " + event.getDescription() + "\n");
      System.out.print("Organizer    : " + event.getOrganizer() + "\n");
      System.out.print("Tanggal Mulai: " + event.getStartDate() + "\n");
      System.out.print("Tanggal Selesai: " + event.getEndDate() + "\n");
      System.out.print("URL          : " + event.getUrl() + "\n");

      terminal.printDivider();
      terminal.waitForInput("Tekan ENTER untuk kembali...");
      this.terminal.clear();
    }
  }
}
