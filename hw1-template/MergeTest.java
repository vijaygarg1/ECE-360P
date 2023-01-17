public class MergeTest {
    public static void main(String[] args) {
        int[] A1 = { 1, 3, 5, 7 };
        int[] B1 = { 2, 4, 6, 8 };
        verifyParallelMerge(A1, B1, 10);

        int[] A2 = { 27, 30, 1000, 2000 };
        int[] B2 = { 1, 2, 3, 5, 10 };
        verifyParallelMerge(A2, B2, 10);

        int[] A3 = { 0, 1, 2 };
        int[] B3 = { 0, 1, 2 };
        verifyParallelMerge(A3, B3, 10);
    }

    static void verifyParallelMerge(int[] A, int[] B, int numThreads) {
        int[] C1 = new int[A.length + B.length];
        int[] C2 = new int[A.length + B.length];

        System.out.println("Verify Parallel Merge for arrays: ");
        printArray(A);
        printArray(B);

        merge(A, B, C1);
        PMerge.parallelMerge(A, B, C2, numThreads);

        boolean isSuccess = true;
        for (int i = 0; i < C1.length; i++) {
            if (C1[i] != C2[i]) {
                System.out.println("Your parallel merge algorithm is not correct");
                System.out.println("Expect:");
                printArray(C1);
                System.out.println("Your results:");
                printArray(C2);
                isSuccess = false;
                break;
            }
        }

        if (isSuccess) {
            System.out.println("Great, your merge algorithm works for this test case");
        }
        System.out.println("=========================================================");
    }

    public static void merge(int[] A, int[] B, int[] C) {
        int i = 0, j = 0, k = C.length - 1;
        while (i < A.length || j < B.length) {
            if (i == A.length) {
                C[k--] = B[j++];
            } else if (j == B.length) {
                C[k--] = A[i++];
            } else {
                if (A[i] < B[j])
                    C[k--] = A[i++];
                else
                    C[k--] = B[j++];
            }
        }
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