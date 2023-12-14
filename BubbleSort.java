/**
 * An implmentation of bubble sort.
 * @author Will Brown
 * @version 1.0
 * Fall 2023
 */
public class BubbleSort implements SortInterface {

    private long numOps;
    private long runtime;

    public void sort(int[] a) {
        numOps = 0;
        long startTime = System.nanoTime();

        boolean swapped = true;
        for (int i = a.length - 1; i > 0 && swapped; --i) {
            swapped = false;
            for (int j = 0; j < i; ++j) {
                ++numOps;
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    swapped = true;
                }
            }
        }
        
        runtime = System.nanoTime() - startTime;
    }

    public long getNumOps() {
        return numOps;
    }

    public long getRuntime() {
        return runtime;
    }
}