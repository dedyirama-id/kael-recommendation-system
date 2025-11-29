package org.kael.entities;

import java.util.Objects;

/**
 * Representasi tag yang dapat melekat pada event atau pengguna.
 */
public class Tag {
  private final String id;
  private final String name;
  private final String slug;

  /**
   * Membuat Tag baru.
   *
   * @param id   identifier unik.
   * @param name nama tag.
   * @param slug slug ramah URL.
   */
  public Tag(String id, String name, String slug) {
    this.id = id;
    this.name = name;
    this.slug = slug;
  }

  /**
   * Mengambil ID tag.
   *
   * @return id tag.
   */
  public String getId() {
    return id;
  }

  /**
   * Mengambil nama tag.
   *
   * @return nama.
   */
  public String getName() {
    return name;
  }

  /**
   * Mengambil slug tag.
   *
   * @return slug.
   */
  public String getSlug() {
    return slug;
  }

  /**
   * Kesetaraan berdasarkan ID tag.
   *
   * @param o objek pembanding.
   * @return true jika ID sama.
   */
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

  /**
   * Representasi string berupa ID tag.
   *
   * @return id dalam bentuk string.
   */
  @Override
  public String toString() {
    return this.getId();
  }
}
