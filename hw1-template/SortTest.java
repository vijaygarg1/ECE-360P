import java.util.Arrays;

public class SortTest {
    public static void main(String[] args) {
        int[] A1 = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
        verifyParallelSort(A1);

        int[] A2 = { 1, 3, 5, 7, 9 };
        verifyParallelSort(A2);

        int[] A3 = { 13, 59, 24, 18, 33, 20, 11, 11, 13, 50, 10999, 97 };
        verifyParallelSort(A3);
    }

    static void verifyParallelSort(int[] A) {
        int[] sorted = new int[A.length];
        int[] B = new int[A.length];

        System.arraycopy(A, 0, sorted, 0, A.length);
        Arrays.sort(sorted);
        System.arraycopy(A, 0, B, 0, A.length);

        System.out.println("Verify Parallel Sort for array: ");
        printArray(A);

        boolean isSuccess = true;

        RunnablePSort.parallelSort(B, 0, B.length, true);
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i] != B[i]) {
                System.out.println("Your parallel sorting algorithm (runnable, increasing) is not correct");
                System.out.println("Expect:");
                printArray(sorted);
                System.out.println("Your results:");
                printArray(B);
                isSuccess = false;
                break;
            }
        }

        System.arraycopy(A, 0, B, 0, A.length);
        ForkJoinPSort.parallelSort(B, 0, B.length, true);
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i] != B[i]) {
                System.out.println("Your parallel sorting algorithm (forkjoin, increasing) is not correct");
                System.out.println("Expect:");
                printArray(sorted);
                System.out.println("Your results:");
                printArray(B);
                isSuccess = false;
                break;
            }
        }

        // Reverse sorted array for decreasing tests
        for (int left = 0, right = sorted.length - 1; left < right; left++, right--) {
            int temp = sorted[left];
            sorted[left] = sorted[right];
            sorted[right] = temp;
        }

        System.arraycopy(A, 0, B, 0, A.length);
        RunnablePSort.parallelSort(B, 0, B.length, false);
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i] != B[i]) {
                System.out.println("Your parallel sorting algorithm (runnable, decreasing) is not correct");
                System.out.println("Expect:");
                printArray(sorted);
                System.out.println("Your results:");
                printArray(B);
                isSuccess = false;
                break;
            }
        }

        System.arraycopy(A, 0, B, 0, A.length);
        ForkJoinPSort.parallelSort(B, 0, B.length, false);
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i] != B[i]) {
                System.out.println("Your parallel sorting algorithm (forkjoin, decreasing) is not correct");
                System.out.println("Expect:");
                printArray(sorted);
                System.out.println("Your results:");
                printArray(B);
                isSuccess = false;
                break;
            }
        }

        if (isSuccess) {
            System.out.println("Great, your sorting algorithm works for this test case");
        }
        System.out.println("=========================================================");
    }

    public static void printArray(int[] A) {
        for (int i = 0; i < A.length; i++) {
            if (i != A.length - 1) {
                System.out.print(A[i] + " ");
            } else {
                System.out.print(A[i]);
            }
        }
        System.out.println();
    }
}
