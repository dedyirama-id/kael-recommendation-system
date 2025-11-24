package org.kael.utils;

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

public class CsvGraphLoader {
  private final Map<String, User> users = new LinkedHashMap<>();
  private final Map<String, Event> events = new LinkedHashMap<>();
  private final Map<String, Tag> tags = new LinkedHashMap<>();

  public Map<String, User> getUsers() {
    return Collections.unmodifiableMap(users);
  }

  public Map<String, Event> getEvents() {
    return Collections.unmodifiableMap(events);
  }

  public Map<String, Tag> getTags() {
    return Collections.unmodifiableMap(tags);
  }

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

  private void loadUsers(Graph<Object> graph) throws IOException {
    for (String[] row : readCsv("users.csv")) {
      if (row.length < 3) {
        throw new IOException("Invalid row in users.csv: " + String.join(",", row));
      }
      String id = row[0].trim();
      User user = new User(id, row[1].trim(), row[2].trim());
      users.put(id, user);
      graph.addVertex(user);
    }
  }

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
        rows.add(line.split(",", -1));
      }
    }
    return rows;
  }

  private InputStream getResource(String resourceName) throws IOException {
    InputStream in = CsvGraphLoader.class.getClassLoader().getResourceAsStream(resourceName);
    if (in == null) {
      throw new IOException("Resource not found: " + resourceName);
    }
    return in;
  }

  private User requireUser(String id) {
    User user = users.get(id);
    if (user == null) {
      throw new IllegalStateException("User not found for id: " + id);
    }
    return user;
  }

  private Event requireEvent(String id) {
    Event event = events.get(id);
    if (event == null) {
      throw new IllegalStateException("Event not found for id: " + id);
    }
    return event;
  }

  private Tag requireTag(String id) {
    Tag tag = tags.get(id);
    if (tag == null) {
      throw new IllegalStateException("Tag not found for id: " + id);
    }
    return tag;
  }
}
