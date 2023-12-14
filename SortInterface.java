/**
 * An interface for sorting algorithms that allows you to log the number of operations and runtime.
 * @author Will Brown
 * @version 1.0
 * Fall 2023
 */
public interface SortInterface {
	
	/**
	 * Performs an in-place sort of the provided array.
	 * @param a the array to sort
	 */
    public void sort(int[] a);
	
	/**
	 * @return the number of operations the last sort took
	 */
    public long getNumOps();
	
	/**
	 * @return the runtime of the last sort in nanoseconds
	 */
	public long getRuntime();
}