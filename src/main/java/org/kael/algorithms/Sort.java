package org.kael.algorithms;

public class Sort {

    /**
     * Sort array of data using selection sort algorithm.
     *
     * @param data Random array of data to be sorted. Element should implement Comparable interface.
     * @throws Exception Throw any exception if process failed
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