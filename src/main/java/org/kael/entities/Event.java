package org.kael.entities;

import java.util.Objects;

public class Event {
  private final String id;
  private final String title;
  private final String slug;
  private final String description;
  private final String organizer;
  private final String startDate;
  private final String endDate;
  private final String url;

  public Event(String id, String title, String slug, String description,
               String organizer, String startDate, String endDate, String url) {
    this.id = id;
    this.title = title;
    this.slug = slug;
    this.description = description;
    this.organizer = organizer;
    this.startDate = startDate;
    this.endDate = endDate;
    this.url = url;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getSlug() {
    return slug;
  }

  public String getDescription() {
    return description;
  }

  public String getOrganizer() {
    return organizer;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Event event)) return false;
    return Objects.equals(id, event.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return this.getId();
  }
}
