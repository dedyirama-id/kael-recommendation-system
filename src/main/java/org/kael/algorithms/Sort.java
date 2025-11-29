package org.kael.algorithms;

/**
 * Kumpulan algoritma pengurutan sederhana yang digunakan di aplikasi.
 */
public class Sort {

    /**
     * Sort array of data using selection sort algorithm (ascending).
     * Array akan diubah di tempat dan tidak mengembalikan nilai.
     *
     * @param data Random array of data to be sorted. Element should implement Comparable interface.
     * @param <T>  tipe elemen yang dapat dibandingkan.
     * @throws Exception jika array bernilai null atau proses gagal.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] data) throws Exception {
        if (data == null) {
            throw new Exception("Data cannot be null");
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
