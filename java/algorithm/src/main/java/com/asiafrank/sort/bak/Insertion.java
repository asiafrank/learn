package com.asiafrank.sort.bak;

/**
 * 插入排序
 * Insertion Sort (A, n) // Sorts A[1..n]
 * for j<-2 to n
 *   do key<-A[j]
 *     i<-(j-1)
 *     while i>0 and A[i]>key
 *       do A[i+1]<-A[i]
 *          i<-(i-1)
 *          A[i+1]<-key
 *
 * Ex.
 * 8 2 4 9 3 6 (key is 2)
 * 2 8 4 9 3 6 (key is 4)
 * 2 4 8 9 3 6 (key is 9, nothing change)
 * 2 4 8 9 3 6 (key is 3)
 * 2 3 4 8 9 6 (key is 6)
 * 2 3 4 6 8 9 (done.)
 *
 * O(n^2)
 *
 * Created by Xiaofan Zhang on 1/3/2016.
 */
public class Insertion {
    public static void main(String[] args) {
        int[] input = {8, 2, 4, 9, 3, 6};
        printArray(input);
        sort(input);
    }

    /**
     * Insertion Sort (A, n) // Sorts A[1..n]
     * @param numbers array
     */
    private static void sort(int[] numbers) {
        int key;
        for (int i = 1; i < numbers.length; i++) {
            key = numbers[i];
            int j = i - 1;
            while (j >= 0 && numbers[j] > key) {
                numbers[j + 1] = numbers[j];
                j = j - 1;
            }
            numbers[j + 1] = key;
            printArray(numbers);
        }
    }

    private static void printArray(int[] input) {
        for (int i : input) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }
}
