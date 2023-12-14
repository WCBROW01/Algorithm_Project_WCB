/**
 * A test program that generates an array and sorts it with both bubble sort and quick sort.
 * @author Will Brown
 * @version 1.0
 * Fall 2023
 */
public class TestSort {
    public static void main(String[] args) {
        int[] a = GenRandomArray.generate(65536);
        SortInterface sort = new QuickSort();
        sort.sort(a);
        System.out.printf("Operations: %d\nRuntime: %d.%09ds\n", sort.getNumOps(), sort.getRuntime() / 1000000000, sort.getRuntime() % 1000000000);
        a = GenRandomArray.generate(65536);
        sort = new BubbleSort();
        sort.sort(a);
        System.out.printf("Operations: %d\nRuntime: %d.%09ds\n", sort.getNumOps(), sort.getRuntime() / 1000000000, sort.getRuntime() % 1000000000);
    }
}