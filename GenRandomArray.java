import java.util.Random;

/**
 * Generate an array of unique randomly-distributed integers
 */
public class GenRandomArray {
	
	/**
	 * Generates an array of unique randomly-distributed integers.
	 * @param n the size of the array
	 * @return an array of randomly-distributed unique integers
	 */
	public static int[] generate(int n) {
		Random rand = new Random();
		int[] arr = new int[n];
		for (int i = 0; i < n; ++i) arr[i] = i;

		while (n > 0) {
			int j = rand.nextInt(n--);
			int temp = arr[n];
			arr[n] = arr[j];
			arr[j] = temp;
		}

		return arr;
	}

	/**
	 * Test program
	 */
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		int[] arr = generate(25);
		long execTime = System.nanoTime() - startTime;
		System.out.printf("Execution time: %dns\n", execTime);
		for (int i = 0; i < arr.length; ++i) {
			System.out.printf("%d ", arr[i]);
		}
	}
}
