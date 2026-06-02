/*
 * William Ee
 * CS-284-E
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

public class CountingSortTest {
    public static void main(String[] args) {
        sortTest();
    }

    public static void sortTest() {
        int[] test1 = {2, 5, 3, 0, 2, 3, 0, 3};
        int[] test2 = {1, 9, 3, 5, 7, 2};
        int[] test3 = {9, 9, 9, 9, 8, 9, 9};
        int[] test4 = {};
        int[] test5 = {1};

        System.out.println("Test 1:");
        printTest(test1);

        System.out.println("Test 2:");
        printTest(test2);

        System.out.println("Test 3:");
        printTest(test3);

        System.out.println("Test 4:");
        printTest(test4);

        System.out.println("Test 5:");
        printTest(test5);
    }

    
    /** 
     * @param A
     */
    public static void printTest(int[] A) {
        System.out.println("Initial: " + java.util.Arrays.toString(A));

        try {
            CountingSort.sort(A);
            System.out.println("Sorted: " + java.util.Arrays.toString(A));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();
    }
}