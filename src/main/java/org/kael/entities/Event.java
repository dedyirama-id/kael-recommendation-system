package org.kael.entities;

import java.util.Objects;

/**
 * Entity yang mewakili sebuah event.
 */
public class Event {
  private final String id;
  private final String title;
  private final String slug;
  private final String description;
  private final String organizer;
  private final String startDate;
  private final String endDate;
  private final String url;

  /**
   * Membuat Event baru.
   *
   * @param id          identifier unik.
   * @param title       judul event.
   * @param slug        slug ramah URL.
   * @param description deskripsi singkat event.
   * @param organizer   penyelenggara.
   * @param startDate   tanggal mulai.
   * @param endDate     tanggal selesai.
   * @param url         tautan detail atau pendaftaran.
   */
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

  /**
   * Mengambil ID event.
   *
   * @return id event.
   */
  public String getId() {
    return id;
  }

  /**
   * Mengambil judul event.
   *
   * @return judul.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Mengambil slug event.
   *
   * @return slug.
   */
  public String getSlug() {
    return slug;
  }

  /**
   * Mengambil deskripsi event.
   *
   * @return deskripsi.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Mengambil penyelenggara event.
   *
   * @return nama organizer.
   */
  public String getOrganizer() {
    return organizer;
  }

  /**
   * Mengambil tanggal mulai event.
   *
   * @return tanggal mulai.
   */
  public String getStartDate() {
    return startDate;
  }

  /**
   * Mengambil tanggal selesai event.
   *
   * @return tanggal selesai.
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * Mengambil tautan informasi event.
   *
   * @return URL event.
   */
  public String getUrl() {
    return url;
  }

  /**
   * Kesetaraan berdasarkan ID event.
   *
   * @param o objek pembanding.
   * @return true jika ID sama.
   */
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

  /**
   * Representasi string berupa ID event.
   *
   * @return id dalam bentuk string.
   */
  @Override
  public String toString() {
    return this.getId();
  }
}
