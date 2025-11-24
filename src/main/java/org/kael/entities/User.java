package org.kael.entities;

import java.util.Objects;

public class User {
  private final String id;
  private final String name;
  private final String profile;

  public User(String id, String name, String profile) {
    this.id = id;
    this.name = name;
    this.profile = profile;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getProfile() {
    return profile;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User user)) return false;
    return Objects.equals(id, user.id);
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
