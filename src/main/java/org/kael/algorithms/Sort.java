package org.kael.algorithms;

/**
 * Kelas utilitas yang menyediakan algoritma pengurutan sederhana.
 * <p>
 * Saat ini berisi implementasi algoritma selection sort dengan urutan menaik (ascending).
 * </p>
 */
public final class Sort {

    private Sort() {
        // Mencegah instansiasi
    }

    /**
     * Mengurutkan elemen dalam array menggunakan algoritma selection sort
     * dalam urutan menaik (ascending).
     * <p>
     * Pengurutan dilakukan secara in-place, sehingga array {@code data}
     * akan dimodifikasi dan tidak ada nilai yang dikembalikan.
     * </p>
     *
     * @param data array yang akan diurutkan; tidak boleh {@code null}
     * @param <T>  tipe elemen yang harus mengimplementasikan {@link Comparable}
     * @throws IllegalArgumentException jika {@code data} bernilai {@code null}
     */
    public static <T extends Comparable<T>> void selectionSort(T[] data) {
        if (data == null) {
            throw new IllegalArgumentException("data tidak boleh null");
        }

        int n = data.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (data[j].compareTo(data[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                T temp = data[i];
                data[i] = data[minIndex];
                data[minIndex] = temp;
            }
        }
    }
}
