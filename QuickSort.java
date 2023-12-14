/**
 * Quicksort as implemented in the GNU C Library 2.38, ported to Java.
 * Rather than being recursive, it uses an internal stack to improve performance.
 * Copyright (C) 1991-2023 Free Software Foundation, Inc.
 * Licensed under the GNU Lesser General Public License v2.0 or later.
 *
 * @author Free Software Foundation, Will Brown
 * @version 1.0
 * Fall 2023
 */
public class QuickSort implements SortInterface {

    /* Discontinue quicksort algorithm when partition gets below this size.
     This particular magic number was chosen to work best on a Sun 4/260. */
    private final int MAX_THRESH = 4;

    private long numOps;
    private long runtime;

	/**
	 * A node for the QuickSort stack.
	 */
    private record QSStackNode(int lo, int hi) {};
	
	/**
	 * The QuickSort stack. Implemented as a basic array, with a fixed size of the number of bits of an integer.
	 * This is the maximum size it can ever grow to.
	 */
    private class QSStack {
        private int top = 0;
        private QSStackNode[] stack = new QSStackNode[Integer.SIZE];
        
        public void push(int low, int high) {
            stack[top++] = new QSStackNode(low, high);
        } 

        public QSStackNode pop() {
            return stack[--top];
        }

        public boolean notEmpty() {
            return top > 0;
        }
    }

    public void sort(int[] a) {
        numOps = 0;
		long startTime = System.nanoTime();

        if (a.length == 0) return;
        
        if (a.length > MAX_THRESH) {
            int lo = 0;
            int hi = a.length - 1;
            QSStack stack = new QSStack();

            stack.push(0, 0);
            while (stack.notEmpty()) {
                /* Select median value from among LO, MID, and HI. Rearrange
                LO and HI so the three values are sorted. This lowers the
                probability of picking a pathological pivot value and
                skips a comparison for both the LEFT_PTR and RIGHT_PTR in
                the while loops. */
                int mid = lo + ((hi - lo) >> 1);
                ++numOps;
                if (a[mid] < a[lo]) {
                    int temp = a[mid];
                    a[mid] = a[lo];
                    a[lo] = temp;
                }
                ++numOps;
                if (a[hi] < a[mid]) {
                    int temp = a[hi];
                    a[hi] = a[mid];
                    a[mid] = temp;
                    
                    // glibc doesn't nest this and skips it with a goto.
                    // we cannot do that.
                    ++numOps;
                    if (a[mid] < a[lo]) {
                        temp = a[mid];
                        a[mid] = a[lo];
                        a[lo] = temp;
                    }
                }

                int left_idx = lo + 1;
                int right_idx = hi - 1;

                /* Here's the famous ``collapse the walls'' section of quicksort.
                Gotta like those tight inner loops!  They are the main reason
                that this algorithm runs much faster than others. */
                do {
                    while (a[left_idx] < a[mid]) {
                        ++numOps;
                        ++left_idx;
                    }
                    while (a[mid] < a[right_idx]) {
                        ++numOps;
                        --right_idx;
                    }

                    if (left_idx < right_idx) {
                        int temp = a[left_idx];
                        a[left_idx] = a[right_idx];
                        a[right_idx] = temp;

                        if (mid == left_idx)
                            mid = right_idx;
                        else if (mid == right_idx)
                            mid = left_idx;
                        ++left_idx;
                        ++right_idx;
                    } else if (left_idx == right_idx) {
                        ++left_idx;
                        --right_idx;
                        break;
                    }
                } while (left_idx <= right_idx);

                /* Set up pointers for next iteration.  First determine whether
                left and right partitions are below the threshold size.  If so,
                ignore one or both.  Otherwise, push the larger partition's
                bounds on the stack and continue sorting the smaller one. */
                if (right_idx - lo <= MAX_THRESH) {
                    if (hi - left_idx <= MAX_THRESH) {
                        QSStackNode node = stack.pop();
                        lo = node.lo();
                        hi = node.hi();
                    } else {
                        lo = left_idx;
                    }
                } else if (hi - left_idx <= MAX_THRESH) {
                    hi = right_idx;
                } else if ((right_idx - lo) > (hi - left_idx)) {
                    stack.push(lo, right_idx);
                    lo = left_idx;
                } else {
                    stack.push(left_idx, hi);
                    hi = right_idx;
                }
            }
        }

        /* Once the BASE_PTR array is partially sorted by quicksort the rest
        is completely sorted using insertion sort, since this is efficient
        for partitions below MAX_THRESH size. BASE_PTR points to the beginning
        of the array to sort, and END_PTR points at the very last element in
        the array (*not* one beyond it!). */
        {
            int end_idx = a.length - 1;
            int tmp_idx = 0;
            int thresh = end_idx < MAX_THRESH ? end_idx : MAX_THRESH;
            int run_idx;

            /* Find smallest element in first threshold and place it at the
            array's beginning.  This is the smallest array element,
            and the operation speeds up insertion sort's inner loop. */
            for (run_idx = tmp_idx + 1; run_idx <= thresh; ++run_idx) {
                ++numOps;
                if (a[run_idx] < a[tmp_idx])
                    tmp_idx = run_idx;
            }
            
            if (tmp_idx != 0) {
                int temp = a[tmp_idx];
                a[tmp_idx] = a[0];
                a[0] = temp;
            }

            /* Insertion sort, running from left-hand-side up to right-hand-side.  */
            run_idx = 1;
            while (++run_idx <= end_idx) {
                tmp_idx = run_idx - 1;
                while (a[run_idx] < a[tmp_idx]) {
                    ++numOps;
                    --tmp_idx;
                }
                
                ++tmp_idx;
                if (tmp_idx != run_idx) {
                    int trav = run_idx + 1;
                    while (--trav >= run_idx) {
                        int i = a[trav];
                        int hi, lo;
                        for (hi = lo = trav; --lo >= tmp_idx; hi = lo)
                            a[hi] = a[lo];
                        a[hi] = i;
                    }
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