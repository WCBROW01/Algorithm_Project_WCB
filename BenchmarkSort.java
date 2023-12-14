/**
 * Benchmarking tool for sorting algorithms
 * @author Will Brown
 * @version 1.0
 * Fall 2023
 */
public class BenchmarkSort {
	
	public static final int BENCHMARK_SIZES[] = { 4, 16, 64, 256, 1024, 4096, 16384, 65536 };
	public static final int EXECUTION_COUNT = 10;
	
	/**
	 * Benchmark a sorting algorithm. Test the sorting algorithm 10 times for each size of list in BENCHMARK_SIZES, and print out a report.
	 * @params sort the sorting algorithm to test
	 */
	public static void benchmark(SortInterface sort) {
		for (int listSize : BENCHMARK_SIZES) {
			System.out.printf("List size: %d\n", listSize);
			long averageOps = 0;
			long averageRuntime = 0;
			for (int i = 0 ; i < EXECUTION_COUNT; ++i) {
				int[] list = GenRandomArray.generate(listSize);
				sort.sort(list);
				averageOps += sort.getNumOps() / 10;
				averageRuntime += sort.getRuntime() / 10;
				System.out.printf("Execution #%d:\nOperations: %d\nRuntime: %d.%09ds\n", i, sort.getNumOps(), sort.getRuntime() / 1000000000, sort.getRuntime() % 1000000000);
			}
			System.out.printf("Average operations: %d\nAverage runtime: %d.%09ds\n\n", averageOps, averageRuntime / 1000000000, averageRuntime % 1000000000);
		}
	}
	
	/**
	 * Entrypoint for the benchmarking tool. Tests bubble sort and quick sort.
	 */
	public static void main(String[] args) {
		System.out.println("Bubble Sort");
		benchmark(new BubbleSort());
		System.out.println("Quick Sort");
		benchmark(new QuickSort());
	}
}