//Kang Chau Yip 23268041
//Passes all large tests

/**
 * An implementation of the Fines problem from the 2022 CITS2200 Project
 */
public class FinesImpl implements Fines {
	public int count;

	public void reset() {
		count = 0;
	}

	/**
	 * 
	 * Apply Merge Sort to sort the array, and find how many ships are in the wrong
	 * position and get a fine.
	 * 
	 * @param priorities a list of the priority of each ship
	 * @return counts the total number of fines the ship may issue
	 */
	public int countFines(int[] priorities) {
		int start = 0;
		int end = priorities.length - 1;
		mergeSort(priorities, start, end);
		return count;
	}

	/**
	 * Set the range to be sorted.
	 * 
	 * @param priorities a list of the priority of each ship
	 * @param start      the starting point
	 * @param end        the ending point
	 */
	private void mergeSort(int[] priorities, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2;
			mergeSort(priorities, start, mid);
			mergeSort(priorities, mid + 1, end);
			merge(priorities, start, mid, end);
		}
	}

	/**
	 * Merge and sort the elements in the array between start and end in the array.
	 * 
	 * @param priorities a list of the priority of each ship
	 * @param start      the starting point
	 * @param mid        the middle point
	 * @param end        the ending point
	 */
	private void merge(int[] priorities, int start, int mid, int end) {
		// n1 is the index of end of the first array
		int n1 = mid - start + 1;
		// n2 is the index of end of the second array
		int n2 = end - mid;
		int L[] = new int[n1];
		int R[] = new int[n2];
		// Copy the original into 2 new array
		for (int i = 0; i < n1; i++) {
			L[i] = priorities[start + i];
		}
		for (int j = 0; j < n2; j++) {
			R[j] = priorities[mid + j + 1];
		}
		int i = 0;
		int j = 0;
		int k = start;
		/*
		 * If L[i] is bigger then R[j], copy L[i] into the original array A[k++], Else
		 * copy R[i], count+1, then i increment
		 */
		while (i < n1 && j < n2) {
			if (L[i] >= R[j]) {
				priorities[k] = L[i];
				i++;
			} else {
				priorities[k] = R[j];
				// count + 1 when the ship swap the position
				count = count + (n1 - i);
				j++;
			}
			k++;
		}
		/*
		 * When L[ ] or R[ ] are left behind, copy simply all the elements remaining in
		 * the array
		 */
		while (i < n1) {
			priorities[k] = L[i];
			i++;
			k++;
		}
		while (j < n2) {
			priorities[k] = R[j];
			j++;
			k++;
		}
	}

}