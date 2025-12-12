package org.kael.entities;

import java.util.Objects;

/**
 * Entity yang mewakili pengguna sistem.
 */
public class User {
  private final String id;
  private final String name;
  private final String profile;
  private final String email;

  /**
   * Membuat User baru.
   *
   * @param id      identifier unik.
   * @param name    nama pengguna.
   * @param profile deskripsi profil pengguna.
   */
  public User(String id, String name, String profile, String email) {
    this.id = id;
    this.name = name;
    this.profile = profile;
    this.email = email;
  }

  /**
   * Mengambil ID user.
   *
   * @return id user.
   */
  public String getId() {
    return id;
  }

  /**
   * Mengambil nama user.
   *
   * @return nama.
   */
  public String getName() {
    return name;
  }

  /**
   * Mengambil deskripsi profil user.
   *
   * @return profil.
   */
  public String getProfile() {
    return profile;
  }

  /**
   * Mengambil email user.
   *
   * @return email.
   */
  public String getEmail() {
    return email;
  }
  /**
   * Kesetaraan berdasarkan ID user.
   *
   * @param o objek pembanding.
   * @return true jika ID sama.
   */
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

  /**
   * Representasi string berupa ID user.
   *
   * @return id dalam bentuk string.
   */
  @Override
  public String toString() {
    return this.getId();
  }
}
