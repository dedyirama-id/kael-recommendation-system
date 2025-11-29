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

  public Scored(T value, double score) {
    this.value = value;
    this.score = score;
  }

  public T getValue() {
    return value;
  }

  public double getScore() {
    return score;
  }

  /**
   * Urutkan descending berdasarkan score.
   * Score lebih besar -> muncul lebih dulu.
   */
  @Override
  public int compareTo(Scored<T> other) {
    // descending: other.score - this.score
    return Double.compare(other.score, this.score);
  }

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

  @Override
  public String toString() {
    return "Scored{" +
        "value=" + value +
        ", score=" + score +
        '}';
  }

  /**
   * Static factory untuk sedikit mempersingkat pemanggilan.
   */
  public static <T> Scored<T> of(T value, double score) {
    return new Scored<>(value, score);
  }
}

