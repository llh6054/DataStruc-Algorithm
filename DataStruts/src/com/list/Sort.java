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
			AnyType temp = a[ p ];
			
			for(j = p; j > 0 && temp.compareTo(a[ j - 1 ]) < 0; j--)
				a[ j ] = a[j - 1];
			a[ j ] = temp;
		}
	}
	
	/**
	 * Shell sort.
	 * @param  a an array of comparable items
	 */
	public static <AnyType extends Comparable<? super AnyType>> 
	void shellSort(AnyType a[]) {
		int j;
		for(int gap = a.length / 2; gap > 0; gap /= 2) {
			for(int i = gap; i < a.length; i++) {
				AnyType temp = a[ i ];
				for(j = i; j >= gap && temp.compareTo( a[ j - gap ] ) < 0; j -= gap)
					a[ j ] = a[ j - gap ];
				a[ j ] = temp;
			}
		}
	}
	
	 /**
     * Internal method for heapsort.
     * @param i the index of an item in the heap.
     * @return the index of the left child.
     */
    private static int leftChild( int i ){
        return 2 * i + 1;
    }
    
    /**
     * Internal method for heapsort that is used in deleteMax and buildHeap.
     * @param a an array of Comparable items.
     * @index i the position from which to percolate down.
     * @int n the logical size of the binary heap.
     */
    private static <AnyType extends Comparable<? super AnyType>>
    void percDown( AnyType [ ] a, int i, int n ){
        int child;
        AnyType tmp;

        for( tmp = a[ i ]; leftChild( i ) < n; i = child ){
            child = leftChild( i );
            if( child != n - 1 && a[ child ].compareTo( a[ child + 1 ] ) < 0 )
                child++;
            if( tmp.compareTo( a[ child ] ) < 0 )
                a[ i ] = a[ child ];
            else
                break;
        }
        a[ i ] = tmp;
    }
    
  
	
}
