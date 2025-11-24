package org.kael.entities;

import java.util.Objects;

public class Tag {
  private final String id;
  private final String name;
  private final String slug;

  public Tag(String id, String name, String slug) {
    this.id = id;
    this.name = name;
    this.slug = slug;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSlug() {
    return slug;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Tag tag)) return false;
    return Objects.equals(id, tag.id);
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
