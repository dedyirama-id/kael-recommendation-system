package org.kael.algorithms;

import java.util.Objects;

/**
 * Wrapper untuk menyimpan sebuah nilai (value) beserta skor (score),
 * misalnya hasil perhitungan Personalized PageRank atau algoritma lain.
 *
 * Urutan "alami" (Comparable) class ini adalah berdasarkan score secara
 * menurun (descending): skor lebih besar dianggap "lebih tinggi" (datang duluan).
 */
public final class Scored<T> implements Comparable<Scored<T>> {

  private final T value;
  private final double score;

  /**
   * Membuat wrapper nilai dengan skor terkait.
   *
   * @param value nilai asli yang ingin diberi skor.
   * @param score skor numerik yang terasosiasi dengan nilai.
   */
  public Scored(T value, double score) {
    this.value = value;
    this.score = score;
  }

  /**
   * Mengambil nilai asli yang dibungkus.
   *
   * @return nilai generik yang disimpan.
   */
  public T getValue() {
    return value;
  }

  /**
   * Mengambil skor yang terasosiasi dengan nilai.
   *
   * @return skor dalam bentuk double.
   */
  public double getScore() {
    return score;
  }

  /**
   * Urutkan descending berdasarkan score.
   * Score lebih besar -> muncul lebih dulu.
   *
   * @param other entri Scored lain untuk dibandingkan.
   * @return nilai negatif jika this memiliki skor lebih besar (datang lebih dulu),
   *         nilai positif jika skor lebih kecil, atau 0 jika sama.
   */
  @Override
  public int compareTo(Scored<T> other) {
    return Double.compare(other.score, this.score);
  }

  /**
   * Membandingkan kesetaraan berdasarkan nilai dan skor.
   *
   * @param o objek lain yang dibandingkan.
   * @return true jika objek adalah Scored dengan value dan score yang sama.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Scored<?> scored)) return false;
    return Double.compare(scored.score, score) == 0 &&
        Objects.equals(value, scored.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, score);
  }

  /**
   * Representasi string sederhana berupa pasangan value dan score.
   *
   * @return String terformat untuk debugging/logging.
   */
  @Override
  public String toString() {
    return "Scored{" +
        "value=" + value +
        ", score=" + score +
        '}';
  }

  /**
   * Static factory untuk sedikit mempersingkat pemanggilan.
   *
   * @param value nilai generik yang ingin dibungkus.
   * @param score skor terkait.
   * @param <T>   tipe nilai.
   * @return instans Scored baru.
   */
  public static <T> Scored<T> of(T value, double score) {
    return new Scored<>(value, score);
  }
}

