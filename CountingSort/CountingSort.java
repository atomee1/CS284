/*
 * William Ee
 * CS-284-E
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

public class CountingSort {
    /** 
     * @param A
     */
    public static void sort(int[] A) {
        if ( A.length == 0 || A == null) {
            throw new IllegalArgumentException("Array A cannot be empty");
        }
        
        int l = A.length;
        int max = A[0];

        for (int i = 1; i < l; i++) {
            if (A[i] > max) {
                max = A[i];
            }
        }
        
        int[] B = new int[l];
        int[] C = new int[max + 1];
        
        for (int i = 0; i <= max; i++) {
            C[i] = 0;
        }
        
        for (int j = 0; j < l; j++) {
            C[A[j]]++;
        }
        
        for (int i = 1; i <= max; i++) {
            C[i] += C[i - 1];
        }
        
        for (int j = l - 1; j >= 0; j--) {
            B[C[A[j]] - 1] = A[j];
            C[A[j]]--;
        }
        
        for (int i = 0; i < l; i++) {
            A[i] = B[i];
        }
    }
}