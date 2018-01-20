package com.juliapavlenko.sortintarray;

import java.util.Arrays;

/**
 * Created by julia on 20.01.18.
 */
public class AppSortIntArray {

    public static int[] TEST_INT_ARRAY = {5, 3, 7, 2, 1, 6, 2, 1, 6, 5, 3, 7, 2, 1, 6, 2, 1, 6, 5, 3, 7, 2, 1, 6, 5, 3, 7, 2, 1, 6, 2, 1, 6, 5, 3, 7, 2, 1, 6, 5};

    public static void main(String[] args) {
        AppSortIntArray appSortIntArray = new AppSortIntArray();
        appSortIntArray.testMergeSort(new MergeSort(), "Merge sort");
        appSortIntArray.testMergeSort(new QuickSort(), "Quick sort");
    }

    public <T extends Sort> void testMergeSort(T sort, String sortName) {
        System.out.println(String.format("%s\nGiven Array:", sortName));
        printArray(TEST_INT_ARRAY);
        int[] sortingArray = Arrays.copyOf(TEST_INT_ARRAY, TEST_INT_ARRAY.length);
        long startTime = System.currentTimeMillis();
        sort.sort(sortingArray);
        long endTime = System.currentTimeMillis();
        System.out.println("Sorted array:");
        printArray(sortingArray);
        System.out.println(sortName + " tooks " + (endTime - startTime) + " milliseconds\n");
    }

    private static void printArray(int arr[]) {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
}


