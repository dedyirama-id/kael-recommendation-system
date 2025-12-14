package org.kael;

import org.kael.entities.Event;
import org.kael.entities.Tag;
import org.kael.entities.User;
import org.kael.models.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Loader utilitas untuk membangun graf objek (User, Event, Tag)
 * berdasarkan data CSV yang tersimpan di resources.
 */
public class GraphLoader {
  private final Map<String, User> users = new LinkedHashMap<>();
  private final Map<String, Event> events = new LinkedHashMap<>();
  private final Map<String, Tag> tags = new LinkedHashMap<>();

  /**
   * Mengambil data user yang sudah dimuat.
   *
   * @return Map tidak dapat dimodifikasi berisi user dengan key ID.
   */
  public Map<String, User> getUsers() {
    return Collections.unmodifiableMap(users);
  }

  /**
   * Mengambil data event yang sudah dimuat.
   *
   * @return Map tidak dapat dimodifikasi berisi event dengan key ID.
   */
  public Map<String, Event> getEvents() {
    return Collections.unmodifiableMap(events);
  }

  /**
   * Mengambil data tag yang sudah dimuat.
   *
   * @return Map tidak dapat dimodifikasi berisi tag dengan key ID.
   */
  public Map<String, Tag> getTags() {
    return Collections.unmodifiableMap(tags);
  }

  /**
   * Memuat seluruh data CSV dan membangun graf tak berarah.
   *
   * @return graf terisi lengkap dengan vertex dan edge yang dibaca.
   * @throws IOException jika file resource tidak ditemukan atau format baris tidak valid.
   */
  public Graph<Object> loadGraph() throws IOException {
    Graph<Object> graph = new Graph<>(false);

    loadUsers(graph);
    loadEvents(graph);
    loadTags(graph);
    linkUserEvents(graph);
    linkUserTags(graph);
    linkEventTags(graph);

    return graph;
  }

  /**
   * Membaca file users.csv dan menambahkan vertex User ke graf.
   *
   * @param graph graf target.
   * @throws IOException jika baris CSV kurang kolom atau tidak bisa dibaca.
   */
  private void loadUsers(Graph<Object> graph) throws IOException {
    for (String[] row : readCsv("users.csv")) {
      if (row.length < 3) {
        throw new IOException("Invalid row in users.csv: " + String.join(",", row));
      }
      String id = row[0].trim();
      User user = new User(id, row[1].trim(), row[2].trim(), row[3].trim());
      users.put(id, user);
      graph.addVertex(user);
    }
  }

  /**
   * Membaca file events.csv dan menambahkan vertex Event ke graf.
   *
   * @param graph graf target.
   * @throws IOException jika baris CSV kurang kolom atau tidak bisa dibaca.
   */
  private void loadEvents(Graph<Object> graph) throws IOException {
    for (String[] row : readCsv("events.csv")) {
      if (row.length < 8) {
        throw new IOException("Invalid row in events.csv: " + String.join(",", row));
      }
      String id = row[0].trim();
      Event event = new Event(
          id,
          row[1].trim(),
          row[2].trim(),
          row[3].trim(),
          row[4].trim(),
          row[5].trim(),
          row[6].trim(),
          row[7].trim()
      );
      events.put(id, event);
      graph.addVertex(event);
    }
  }

  /**
   * Membaca file tags.csv dan menambahkan vertex Tag ke graf.
   *
   * @param graph graf target.
   * @throws IOException jika baris CSV kurang kolom atau tidak bisa dibaca.
   */
  private void loadTags(Graph<Object> graph) throws IOException {
    for (String[] row : readCsv("tags.csv")) {
      if (row.length < 3) {
        throw new IOException("Invalid row in tags.csv: " + String.join(",", row));
      }
      String id = row[0].trim();
      Tag tag = new Tag(id, row[1].trim(), row[2].trim());
      tags.put(id, tag);
      graph.addVertex(tag);
    }
  }

  /**
   * Membuat edge antara user dan event berdasarkan user_event.csv.
   *
   * @param graph graf target.
   * @throws IOException jika baris tidak valid.
   */
  private void linkUserEvents(Graph<Object> graph) throws IOException {
    for (String[] row : readCsv("user_event.csv")) {
      if (row.length < 2) {
        throw new IOException("Invalid row in user_event.csv: " + String.join(",", row));
      }
      User user = requireUser(row[0].trim());
      Event event = requireEvent(row[1].trim());
      graph.addEdge(user, event);
    }
  }

  /**
   * Membuat edge antara user dan tag berdasarkan user_tag.csv.
   *
   * @param graph graf target.
   * @throws IOException jika baris tidak valid.
   */
  private void linkUserTags(Graph<Object> graph) throws IOException {
    for (String[] row : readCsv("user_tag.csv")) {
      if (row.length < 2) {
        throw new IOException("Invalid row in user_tag.csv: " + String.join(",", row));
      }
      User user = requireUser(row[0].trim());
      Tag tag = requireTag(row[1].trim());
      graph.addEdge(user, tag);
    }
  }

  /**
   * Membuat edge antara event dan tag berdasarkan event_tag.csv.
   *
   * @param graph graf target.
   * @throws IOException jika baris tidak valid.
   */
  private void linkEventTags(Graph<Object> graph) throws IOException {
    for (String[] row : readCsv("event_tag.csv")) {
      if (row.length < 2) {
        throw new IOException("Invalid row in event_tag.csv: " + String.join(",", row));
      }
      Event event = requireEvent(row[0].trim());
      Tag tag = requireTag(row[1].trim());
      graph.addEdge(event, tag);
    }
  }

  /**
   * Membaca resource CSV menjadi list array string tanpa baris header.
   *
   * @param resourceName nama file resource di classpath.
   * @return list baris yang sudah dipisah berdasarkan koma.
   * @throws IOException jika resource tidak ditemukan atau gagal dibaca.
   */
  private List<String[]> readCsv(String resourceName) throws IOException {
    InputStream in = getResource(resourceName);
    List<String[]> rows = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
      String line;
      boolean skipHeader = true;
      while ((line = reader.readLine()) != null) {
        if (skipHeader) {
          skipHeader = false;
          continue;
        }
        if (line.isBlank()) continue;
        rows.add(parseCsvLine(line));
      }
    }
    return rows;
  }

  /**
   * Mem-parse satu baris CSV dengan dukungan tanda kutip sehingga koma di dalam kutipan tidak terpecah.
   * Format kutipan ganda di dalam field didukung dengan escape "".
   */
  private String[] parseCsvLine(String line) {
    List<String> cols = new ArrayList<>();
    StringBuilder current = new StringBuilder();
    boolean inQuotes = false;

    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      if (c == '"') {
        boolean isEscapedQuote = inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"';
        if (isEscapedQuote) {
          current.append('"');
          i++; // lewati kutipan kedua
        } else {
          inQuotes = !inQuotes;
        }
      } else if (c == ',' && !inQuotes) {
        cols.add(current.toString());
        current.setLength(0);
      } else {
        current.append(c);
      }
    }
    cols.add(current.toString());
    return cols.toArray(new String[0]);
  }

  /**
   * Mengambil stream resource dari classpath.
   *
   * @param resourceName nama file di resources.
   * @return InputStream siap dibaca.
   * @throws IOException jika resource tidak ditemukan.
   */
  private InputStream getResource(String resourceName) throws IOException {
    InputStream in = GraphLoader.class.getClassLoader().getResourceAsStream(resourceName);
    if (in == null) {
      throw new IOException("Resource not found: " + resourceName);
    }
    return in;
  }

  /**
   * Mengambil user dari cache dan melempar exception jika tidak ada.
   *
   * @param id id user.
   * @return User dengan id yang dimaksud.
   * @throws IllegalStateException jika user tidak ditemukan.
   */
  private User requireUser(String id) {
    User user = users.get(id);
    if (user == null) {
      throw new IllegalStateException("User not found for id: " + id);
    }
    return user;
  }

  /**
   * Mengambil event dari cache dan melempar exception jika tidak ada.
   *
   * @param id id event.
   * @return Event dengan id yang dimaksud.
   * @throws IllegalStateException jika event tidak ditemukan.
   */
  private Event requireEvent(String id) {
    Event event = events.get(id);
    if (event == null) {
      throw new IllegalStateException("Event not found for id: " + id);
    }
    return event;
  }

  /**
   * Mengambil tag dari cache dan melempar exception jika tidak ada.
   *
   * @param id id tag.
   * @return Tag dengan id yang dimaksud.
   * @throws IllegalStateException jika tag tidak ditemukan.
   */
  private Tag requireTag(String id) {
    Tag tag = tags.get(id);
    if (tag == null) {
      throw new IllegalStateException("Tag not found for id: " + id);
    }
    return tag;
  }
}
