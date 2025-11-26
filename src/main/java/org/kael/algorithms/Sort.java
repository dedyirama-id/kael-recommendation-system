package org.kael.algorithms;

public class Sort {

    /**
     * Sort array of data using selection sort algorithm.
     *
     * @param data Random array of data to be sorted. Element should implement Comparable interface.
     * @throws Exception Throw any exception if process failed
     */
    public static <T extends Comparable<T>> void selectionSortDesc(T[] data) throws Exception {
        if (data == null) {
            throw new Exception("Data cannot be null");
        }

        int n = data.length;

        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (data[j].compareTo(data[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }

            if (maxIndex != i) {
                T temp = data[i];
                data[i] = data[maxIndex];
                data[maxIndex] = temp;
            }
        }
    }
}