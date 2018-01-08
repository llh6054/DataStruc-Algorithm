package com.hash;

/**
 * Probing table implementation of hash tables
 */
public class QuadraticProbingHashTable<AnyType> {
	
	private static final int DEFAULT_TABLE_SIZE = 101;
	private HashEntry<AnyType>[] array;
	private int currentSize;
	
	public QuadraticProbingHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}
	
	public QuadraticProbingHashTable(int size) {
		allocateArray(size);
		makeEmpty();
	}
	
	/**
	 * Internal method to allocate array
	 * @param arraySize the size of the array
	 */
	private void allocateArray(int arraySize) {
		array = new HashEntry[arraySize];
	}
	
	/**
	 * Make the hash table logical empty
	 */
	private void makeEmpty() {
		currentSize = 0;
		for(int i = 0; i < array.length; i++) 
			array[i] = null;
	}
	
	/**
	 * Find an item weather is contained in the hash table
	 * @param x item to search for
	 * @return true if found, false otherwise
	 */
	public boolean contains(AnyType x) {
		int curPos = findPos(x);
		return isActive(curPos);
	}
	
	/**
	 * Method that performs quadratic probing resolution
	 * @param x the item to search for
	 * @return the position where the search terminate
	 */
	private int findPos(AnyType x) {
		int offset = 1;
		int curPos = myHash(x);
		
		while(array[curPos] != null && !array[curPos].element.equals(x)) {
			curPos += offset;
			offset += 2;
			
			if(curPos > array.length)
				curPos -=array.length;
		}
		
		return curPos;
	}
	
	/**
	 * Return ture if currentPos exists and is active
	 * @param currentPos a result of the call to findPos
	 * @return true if current position is active
	 */
	private boolean isActive(int currentPos) {
		return array[currentPos] != null && array[currentPos].isActive;
	}
	
	
	private int myHash(AnyType x) {
		int hashVal = x.hashCode();
		
		hashVal %= array.length;
		if(hashVal < 0)
			hashVal += array.length; 
		
		return hashVal;
	}
	
	private static class HashEntry<AnyType> {
		public AnyType element; 	//the element;
		public boolean isActive;	// false if maked deleted
		
		public HashEntry(AnyType e) {
			this(e, true);
		}
		
		public HashEntry(AnyType e, boolean i) {
			element = e;
			isActive = i;
		}
	}
	
	
	
	/**
	 * Internal method to find a prime number at least as larger as n
	 * @param n the starting number(must be positive)
	 * @return a prime number larger than or equals to n
	 */
	private static int nextPrime(int n) {
		if(n % 2 == 0)
			n += 1;
		
		for(; !isPrime(n); n +=2)
			;
		
		return n;
	}
	
	/**
	 * Internal method to test if a number is prime
	 * Not an efficient algorithm
	 * @param n the number to test
	 * @return the result of the test
	 */
	private static boolean isPrime(int n) {
		if(n == 2 || n == 3)
			return true;
		
		if(n == 1 || n % 2 == 0)
			return false;
		
		for(int i = 3; i * i <=n; i += 2)
			if(n % i == 0)
				return false;
		
		return true;
	}
	

	public static void main(String[] args) {

	}

}
