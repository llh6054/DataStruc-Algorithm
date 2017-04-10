package com.list;

import java.util.Iterator;

public class MyArrayList<AnyType> implements Iterable<AnyType>
{
	private static final int DEFAULT_CAPACITY = 10;

	private int theSize;
	private AnyType[] theItems;
	private int topOfStack = -1;
	/**
		Construct a new arrayList
	*/
	public MyArrayList() {
		doClear();
	}

	/**
		Returns the number of items in this collection
	*/
	public int size() {
		return theSize;
	}

	/**
		Return true if this collection is empty
	*/
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
		Return the item at idx position
	*/
	public AnyType get(int idx) {
		if( idx < 0 || idx > size() )
			throw new ArrayIndexOutOfBoundsException( "Index " + idx + "; Size " + size() );
		return theItems[idx];
	}

	/**
		Changes the item at position idx
		@param idx the index to change
		@param newValue the new value
		@return the old value
		@throws ArrayIndexOutOfBoundsException if index is out of range
	*/
	public AnyType set(int idx, AnyType newValue) {
		if( idx < 0 || idx > size())
			throw new ArrayIndexOutOfBoundsException( "Index " + idx + "; Size " + size());
		AnyType old = theItems[idx];
		theItems[idx] = newValue;
		return old;
	}

	@SuppressWarnings("unchecked")
	public void endureCapacity(int newCapacity) {
		if(newCapacity < theSize)
			return;

		AnyType[] old = theItems;
		theItems = (AnyType[])new Object[newCapacity];
		for(int i = 0; i < size(); i++) {
			theItems[i] = old[i];
		}
	}

	/**
		Adds an item to this collection, at the end
	*/
	public boolean add(AnyType x) {
		add(size(), x);
		return true;
	} 

	/**
		Adds an item to this collection, at the specific position
	*/
	public void add(int idx, AnyType x) {
		if(theItems.length == size())
			endureCapacity(size() * 2 + 1);

		for(int i = theSize; i > idx; i--)
			theItems[i] = theItems[i-1];		
		
		theItems[idx] = x;
		theSize++;
	}

	public AnyType remove(int idx) {
		AnyType removeItem = theItems[idx];

		for(int i = idx; i < size()-1; i++) 
			theItems[i] = theItems[i + 1];
		theSize--;
		return removeItem;
	}

	/**
		Removes object at the top of this stack and returns 
		that object as the value of the function.
	*/
	public AnyType pop() {
		AnyType x = peek();
		topOfStack--;
		return x;
		
	}
	
	/**
	 * 
	 * @return true if stack is empty
	 */
	public boolean stackIsEmpty() {
		return topOfStack < 0;
	}

	/**
		Pushes an item onto the top of the stack.
	*/
	public AnyType push(AnyType x) {
		if(theItems.length == size())
			endureCapacity(size() * 2 + 1);
		
		topOfStack++;
		theSize++;
		set(topOfStack, x);
		return x;
		
	}

	/**
		Looks at the object at the top of this stack without removing it from the stack.
	*/
	public AnyType peek() {
		if(topOfStack == -1)
			return null;
		return get(topOfStack);
	}
	
	/**
	 * add all items' element to the arrayList.
	 * 
	 * @param items collection to be added
	 */
	public void addAll(Iterable<? extends AnyType> items) {
		Iterator<? extends AnyType> iter = items.iterator();
		
		while(iter.hasNext()) {
			add(iter.next());
		}
	}

	/**
		Change the size of the this collection to zero
	*/
	public void clear() {
		doClear();
	}

	private void doClear() {
		theSize = 0;
		endureCapacity(DEFAULT_CAPACITY);
	}

	public java.util.ListIterator<AnyType> iterator() {
		return new ArrayListIterator();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("[ ");

		for(AnyType x: this) 
			sb.append(x + " ");
		sb.append(" ]");

		return new String(sb);
	}


	/**
	 * 	 A listIterator.
	 * */
	private class ArrayListIterator implements java.util.ListIterator<AnyType>
	{
		private int current = 0;
		private boolean okToRemove = false;

		@Override
		public boolean hasNext() {
			return current < size();
		}

		@Override
		public AnyType next() {
			if( !hasNext() )
				throw new java.util.NoSuchElementException();
			
			okToRemove = true;
			return theItems[ current++ ];
		}

		@Override
		public void remove() {
			if( !okToRemove )
				throw new IllegalStateException();
			
			MyArrayList.this.remove(--current);
			okToRemove = false;
		}

		@Override
		public void add(AnyType val) {
			MyArrayList.this.add(current, val);	
			current++;
		}

		@Override
		public boolean hasPrevious() {
			return current != 0;
		}

		@Override
		public int nextIndex() {
			return current;
		}

		@Override
		public AnyType previous() {
			if(!hasPrevious())
				throw new java.util.NoSuchElementException();
			okToRemove = true;
			return MyArrayList.this.get(current-1);
		}

		@Override
		public int previousIndex() {
			return current - 1;
		}

		@Override
		public void set(AnyType val) {
			if(hasPrevious())
				MyArrayList.this.set(current - 1, val);
		}
	}
}


class TestArrayList
{
	public static void main(String[] args)
	{
		testArrayList();
		testStack();
	}

	public static void testArrayList() {
		MyArrayList<Integer> lst =  new MyArrayList<>();

		for(int i = 0; i < 10; i++) {
			lst.add(i);
		}

		for(int i = 20; i < 30; i++) {
			lst.add(0, i);
		}

		lst.remove(0);
		lst.remove(lst.size() - 1);
		
		java.util.ListIterator<Integer> iterator = lst.iterator();
		iterator.add(100);
		while(iterator.hasNext()) {
			System.out.println(iterator.hasPrevious());
			System.out.println(iterator.next());
		}
		System.out.println(lst);
	}
	
	public static void testStack() {
		
		MyArrayList<Integer> lst =  new MyArrayList<>();
		
		System.out.println(lst.push(3));
		System.out.println(lst.push(123));
		System.out.println(lst.push(1234));
		System.out.println(lst.push(12345));
		System.out.println(lst.push(123456));
		System.out.println(lst.push(1234567));
		System.out.println(lst);
		
		System.out.println(lst.pop());
		System.out.println(lst);
		System.out.println(lst.pop());
		System.out.println(lst);
		System.out.println(lst.push(12));
		System.out.println(lst);
		System.out.println(lst.push(1));
		System.out.println(lst);
		System.out.println(lst.pop());
		System.out.println(lst);
		
		System.out.println(lst.push(13));
		System.out.println(lst.push(13));
		System.out.println(lst.push(13));
		System.out.println(lst);
		
		System.out.println(lst.size());
	}
	
}