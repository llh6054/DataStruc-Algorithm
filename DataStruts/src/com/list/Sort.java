package com.list;

/**
 *A class that contains several sort routines. 
 * @author chubby
 *
 */
public final class Sort {

	/**
	 * Simple insertion sort.
	 * @param a an array of comparable items
	 */
	public static <AnyType extends Comparable<? super AnyType>>
	void insertionSort(AnyType a[]) {
		int j;
		for(int p = 1; p < a.length; p++) {
			AnyType temp = a[p];
			
			for(j = p; j > 0 && temp.compareTo(a[j-1]) < 0; j-- )
				a[j] = a[j - 1];
			a[j] = temp;
		}
	}
	
}
