
package com.hash;

import java.util.LinkedList;
import java.util.List;

public class SeparateChainingHashTable<AnyType> {
	
	private static final int DEFAULT_TABLE_SIZE = 101;
	/**the array of lists**/
	private List<AnyType>[] theLists;
	private int currentSize;
	
	public SeparateChainingHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}
	
	public SeparateChainingHashTable(int size) {
		theLists = new LinkedList[nextPrime(size)];
		for(int i = 0; i < theLists.length; i++)
			theLists[i] = new LinkedList<>();
	}
	
	/**
	 * Insert into hash table. if x is already present, then do nothing
	 * @param x item to be insert
	 */
	public void insert(AnyType x) {
		List<AnyType> whichList = theLists[myHash(x)];
		if(!whichList.contains(x)) {
			whichList.add(x);
			
			if(++currentSize > theLists.length)
				rehash();
		}
	}
	
	/**
	 * Remove item from hash table
	 * @param x item to remove
	 */
	public void remove(AnyType x) {
		List<AnyType> whichList = theLists[myHash(x)];
		if(whichList.contains(x)) {
			whichList.remove(x);
			currentSize--;
		}
	}
	
	
	/**
	 * Find a item from hash table
	 * @param x item to search for
	 * @return true if x is found
	 */
	public boolean contains(AnyType x) {
		List<AnyType> whichList = theLists[myHash(x)];
		return whichList.contains(x);
	}
	
	/**
	 * Make the hash table logically empty
	 */
	public void makeEmpty() {
		for(int i = 0; i < theLists.length; i++)
			theLists[i].clear();
		currentSize = 0;
	}
	
	private void rehash() {
		List<AnyType>[] oldList = theLists;
		
		//Create new double-size empty table
		theLists = new List[nextPrime(2 * theLists.length)];
		for(int i = 0; i < theLists.length; i++)
			theLists[i] = new LinkedList<>();
		
		//copy table over
		currentSize = 0;
		for(List<AnyType> list : oldList)
			for(AnyType x : list)
				insert(x);
	}
	
	/**
	 * A hash rountine for String objects
	 * @param key the string to hash
	 * @param tableSize size of the hash table
	 * @return the hash value
	 */
	public static int hash(String key, int tableSize) {
		int hashVal = 0;
		
		for(int i = 0; i < key.length(); i++)
			hashVal += 37 * hashVal + key.charAt(i);
		
		hashVal %= tableSize;
		if(hashVal < 0)
			hashVal += tableSize;
		
		return hashVal;
	}
	
	private int myHash(AnyType x) {
		int hashVal = x.hashCode();
		
		hashVal %= theLists.length;
		if(hashVal < 0)		//x.hashCode maybe nagetive
			hashVal += theLists.length;
		
		return hashVal;
	}

	/**
	 * Internal method to find a prime number at least as large as n
	 * @param n
	 * @return a prime number larger than n or equals to n
	 */
	private static int nextPrime(int n) {
		if(n % 2 == 0)
			n++;
		
		for(; !isPrime(n); n+=2)
			;
		
		return n;
	}
	
	/**
	 * Interanl method to test if number is prime
	 * Not an efficient algorithm
	 * @param n the number to test
	 * @return the result of the test
	 */
	private static boolean isPrime(int n) {
		if(n == 2 || n == 3)
			return true;
		
		if(n == 1 || n % 2 == 0)
			return false;
		
		for(int i = 3; i * i < n; i+=2)
			if(n % i == 0)
				return false;
		
		return true;
	}
	
	
	//Simple Main
	public static void main(String[] args) {
		SeparateChainingHashTable<Integer> H = new SeparateChainingHashTable<>();
		
		long startTime = System.currentTimeMillis();
		final int NUMS = 2000000;
		final int GAP = 37;
		
		System.out.println("Checking..., (no more output means success)");
		
		for(int i = GAP; i != 0; i = (i +  GAP) % NUMS)
			H.insert(i);
		for(int i = 0; i < NUMS; i += 2)
			H.remove(i);
		
		for(int i = 0; i < NUMS; i += 2) 
			if(!H.contains(i))
				System.out.println("Find fails " + i);
		
		for(int i = 0; i < NUMS; i += 2)
			if(H.contains(i))
				System.out.println("OOPS!!!! "  + i);
		
		long endTime = System.currentTimeMillis();
		System.out.println("Spend time : " + (endTime - startTime));
		
	}
	
	
}
