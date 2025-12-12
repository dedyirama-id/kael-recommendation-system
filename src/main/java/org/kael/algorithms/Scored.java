package org.kael.algorithms;

import java.util.Objects;

/**
 * Kelas pembungkus yang menyimpan sebuah nilai beserta skor numeriknya.
 * <p>
 * Kelas ini dapat digunakan, misalnya, untuk menyimpan hasil perhitungan
 * algoritma pemeringkatan seperti Personalized PageRank atau algoritma
 * penilaian lainnya.
 * </p>
 *
 * <p>
 * Urutan alami ({@link Comparable}) untuk kelas ini didasarkan pada skor
 * secara menurun (descending): skor yang lebih besar dianggap memiliki
 * prioritas lebih tinggi.
 * </p>
 *
 * @param <T> tipe nilai yang dibungkus
 */
public final class Scored<T> implements Comparable<Scored<T>> {

  private final T value;
  private final double score;

  /**
   * Membuat instance {@code Scored} dengan nilai dan skor terkait.
   *
   * @param value nilai yang akan dibungkus; boleh {@code null}
   * @param score skor numerik yang berasosiasi dengan nilai
   */
  public Scored(T value, double score) {
    this.value = value;
    this.score = score;
  }

  /**
   * Mengembalikan nilai yang dibungkus oleh objek ini.
   *
   * @return nilai yang disimpan, dapat berupa {@code null}
   */
  public T getValue() {
    return value;
  }

  /**
   * Mengembalikan skor yang berasosiasi dengan nilai.
   *
   * @return skor dalam bentuk {@code double}
   */
  public double getScore() {
    return score;
  }

  /**
   * Membandingkan objek ini dengan objek {@code Scored} lain berdasarkan skor.
   * <p>
   * Perbandingan dilakukan secara menurun (descending), sehingga objek
   * dengan skor lebih besar akan dianggap "lebih kecil" dalam konteks
   * pengurutan dan muncul lebih dahulu.
   * </p>
   *
   * @param other objek {@code Scored} lain yang akan dibandingkan
   * @return nilai negatif jika objek ini memiliki skor lebih besar dari {@code other},
   *         nilai positif jika skor lebih kecil, atau {@code 0} jika skornya sama
   */
  @Override
  public int compareTo(Scored<T> other) {
    return Double.compare(other.score, this.score);
  }

  /**
   * Menentukan kesetaraan antara objek ini dan objek lain.
   * <p>
   * Dua objek {@code Scored} dianggap setara jika keduanya memiliki nilai
   * ({@code value}) dan skor ({@code score}) yang sama.
   * </p>
   *
   * @param o objek lain yang akan dibandingkan
   * @return {@code true} jika objek lain merupakan {@code Scored} dengan
   *         nilai dan skor yang sama; {@code false} jika tidak
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Scored<?> scored)) return false;
    return Double.compare(scored.score, score) == 0 &&
        Objects.equals(value, scored.value);
  }

  /**
   * Mengembalikan nilai hash yang konsisten dengan implementasi {@link #equals(Object)}.
   *
   * @return nilai hash untuk objek ini
   */
  @Override
  public int hashCode() {
    return Objects.hash(value, score);
  }

  /**
   * Mengembalikan representasi string dari objek ini untuk keperluan
   * debugging atau logging.
   *
   * @return representasi string yang memuat nilai dan skor
   */
  @Override
  public String toString() {
    return "Scored{" +
        "value=" + value +
        ", score=" + score +
        '}';
  }

  /**
   * Membuat instance {@code Scored} baru menggunakan metode static factory.
   *
   * @param value nilai yang akan dibungkus
   * @param score skor numerik yang berasosiasi dengan nilai
   * @param <T>   tipe nilai
   * @return instance {@code Scored} baru yang berisi nilai dan skor tersebut
   */
  public static <T> Scored<T> of(T value, double score) {
    return new Scored<>(value, score);
  }
}
