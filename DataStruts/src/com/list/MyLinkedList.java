package com.list;


/**
	LinkList class implements a doubly-linked list.
*/
public class MyLinkedList<AnyType> implements Iterable<AnyType>
{
	private int theSize;
	private int modCount = 0;
	private Node<AnyType> beginMarker;
	private Node<AnyType> endMarker;
	
	/**
		Construct an empty LinkedList.
	*/
	public MyLinkedList() {
		doClear();
	}

	@SuppressWarnings("unused")
	private void clear() {
		doClear();
	}

	/**
		Change the size of this collection to zero.
	*/
	public void doClear() {
		beginMarker = new Node<>(null, null, null);
		endMarker = new Node<>(null, beginMarker, null);
		beginMarker.next = endMarker;

		theSize = 0;
		modCount++;
	}

	/**
		@return the number of items in this collection 
	*/
	public int size() {
		return theSize;
	}

	public boolean isEmpty() {
		return theSize == 0;
	}

	/**
		Adds an item to this collection, at the end.
		@param x any object
		@return true.
	*/
	public boolean add(AnyType x) {
		add(size(), x);
		return true;
	}

	/**
		Adds an item to this collection, at specific position
		Items at or after that position are slid one position higher.
		@param x anyobject.
		@param idx position to add it.
		throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
	*/
	public void add(int idx, AnyType x) {
		addBefore(getNode(idx, 0, size()), x);
	}

	/**
		Adds an item to this collection, at specific position p.
		Items at or after that position are slid one position higher.
		@param p Node to be add before.
		@param x any object.
		@throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
	*/
	public void addBefore(Node<AnyType> p, AnyType x) {
		Node<AnyType> newNode = new Node<>(x, p.prev, p);
		newNode.prev.next = newNode;
		p.prev = newNode;

		theSize++;
		modCount++;
	}


	/**
		Returns the item at position idx.
		@param idx the index to search in.
		@throws IndexOutOfBoundsException if idx is out of range.
	*/
	public AnyType get(int idx) {
		return getNode(idx).data;
	}

	/**
		Gets the Node at position idx, which must range from 0 to size - 1.
	*/
	private Node<AnyType> getNode(int idx) {
		return getNode(idx, 0 , size() - 1);
	}


	/**
		Gets the Node at position idx, which must range from lower to upper.
		@param idx index to search at.
		@param lower lowest valid index.
		@param upper uppest valid index.
		@return internal node corresponding to idx.
		@throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive. 
	*/
	private Node<AnyType> getNode(int idx, int lower, int upper) 
	{
		Node<AnyType> p;

		if(idx < lower || idx > upper)
			throw new IndexOutOfBoundsException("getNode index: " + idx + "; size: " + size());
		
		if(idx < size() / 2) 
		{
			p = beginMarker.next;
			for(int i = 0; i < idx; i++)
				p = p.next;
		}
		else 
		{
			p = endMarker;
			for(int i = size(); i > idx; i--)
				p = p.prev;
		}

		return p;
	}

	/**
		Changes the item at at position idx.
		@param idx the index to change.
		@param newVal the new value.
		@throws IndexOutOfBoundsException if idx is not between 0 and size() - 1.
	*/
	public AnyType set(int idx , AnyType newVal) 
	{
		Node<AnyType> p = getNode(idx);
		AnyType oldVal = p.data;

		p.data = newVal;
		return oldVal;
	}
	
	/**
		Removes an item from this collection at index
	*/
	public AnyType remove(int idx) 
	{
		return remove(getNode(idx));
	}

	/**
		Removes the object contained in Node p.
		@param p the Node containing the object.
		@return the item that was removed from the collection 
	*/
	private AnyType remove(Node<AnyType> p) 
	{
		p.prev.next = p.next;
		p.next.prev = p.prev;
		theSize--;
		modCount++;

		return p.data;
	}


	/**
		Change the adjacent data by adjust pointer.
		@param idx the index of data to be adjust.
		@return whether the data is changed.
	*/
	public boolean changeData(int idx)
	{
		if(idx < 0 || idx >= size() - 1)
			throw new IndexOutOfBoundsException("getNode index: " + idx + "; size: " +size());
		Node<AnyType> node = getNode(idx);
		
		node.prev.next = node.next;
		node.next.next.prev = node;
		node.next.prev = node.prev;
		node.prev =  node.next;		//position has changed in here
		node.next = node.prev.next;
		node.prev.next = node;
		
		//AnyType data = remove(idx);
		//add(idx-1, data);
		//modCount++;
		return true;
	}

	
	/**
		return true if this elements is contained in this collection
		@param val values that may contains
		@return true if contained, else false
	*/
	public boolean contains(AnyType val) 
	{
		java.util.Iterator<AnyType> iterator = iterator();

		while(iterator.hasNext()) {
			if(val == iterator.next()) return true;
		}

		return false;
	}

	/**
		Removes all item that contains in items
		@param items 
	*/
	public void removesAll(Iterable <? extends AnyType> items)
	{
		AnyType item = null, listItem = null;
		java.util.Iterator<? extends AnyType> itemsIterator = items.iterator();
		java.util.Iterator<AnyType> listIterator;
	
		while(itemsIterator.hasNext()) {
			item = itemsIterator.next();
			listIterator = new LinkedListIterator();
			while(listIterator.hasNext())
			{
				listItem = listIterator.next();
				if((item == null && listItem == null) || (item != null && listItem.equals(item)))
					listIterator.remove();
			}
		}
	}
	
	
	public String toString() 
	{
		StringBuilder sb = new StringBuilder("[ ");
		
		for( AnyType x: this) 
		{
			sb.append(x + " ");
		}
		sb.append(" ]");
		
		return new String(sb);
	}

	public java.util.Iterator<AnyType> iterator() 
	{
		return new LinkedListIterator();
	}

	/**
		This is an implementation of LinkedListIterator
	*/
	private class LinkedListIterator implements java.util.ListIterator<AnyType>
	{
		private Node<AnyType> current = beginMarker.next;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;
		private int index;

		@Override
		public boolean hasNext() 
		{
			return current != endMarker;
		}

		@Override
		public AnyType next() 
		{
			if(expectedModCount != modCount)
				throw new java.util.ConcurrentModificationException();
			if(!hasNext())
				throw new java.util.NoSuchElementException();
			AnyType nextItem = current.data;	// focus on cursor
			current = current.next;	//cursor to remove
			okToRemove = true;
			index++;
			return nextItem;
		}
		
		@Override
		public void remove() 
		{
			if(expectedModCount != modCount)
				throw new java.util.ConcurrentModificationException();
			if(!okToRemove)
				throw new IllegalStateException();

			MyLinkedList.this.remove(current.prev);
			expectedModCount++;
			okToRemove = false;
		}

		@Override
		public void add(AnyType val) 
		{
			if(expectedModCount != modCount)
				throw new java.util.ConcurrentModificationException();
			MyLinkedList.this.add(index, val);
			expectedModCount++;
		}

		@Override
		public boolean hasPrevious() 
		{
			return current.prev != beginMarker;
		}

		@Override
		public int nextIndex() 
		{
			return index;
		}

		@Override
		public AnyType previous() 
		{
			if(!hasPrevious())
				throw new java.util.NoSuchElementException();
			current = current.prev;
			AnyType prevItem = current.data;
			index--;
			okToRemove = true;
			return prevItem;
		}

		@Override
		public int previousIndex() 
		{
			return index--;
		}

		@Override
		public void set(AnyType val) 
		{
			if(expectedModCount != modCount)
				throw new java.util.ConcurrentModificationException();
			current.data = val;
		}

	}

	/**
		This is the doubly-linked list node.
	*/
	private static class Node<AnyType>
	{
		public AnyType data;
		public Node<AnyType> prev;
		public Node<AnyType> next;

		public Node(AnyType d, Node<AnyType> p, Node<AnyType> n) {
			data = d; prev = p; next = n;
		} 

	}

}

class TestLinkedList
{
	public static void main(String[] args) 
	{
		MyLinkedList<Integer> lst = new MyLinkedList<>();
		MyLinkedList<Integer> removedLst = new MyLinkedList<>();
		
		for(int i = 0; i < 10; i++) 
			lst.add(i);
		for(int i = 20; i < 30; i++)
			lst.add(0, i);

		System.out.println("test");
		lst.remove(0);
		lst.remove(lst.size() - 1);
		
		lst.changeData(5);
		System.out.println(lst);

		for(int i = 0; i < 5; i++)
			removedLst.add(0, i);
		lst.removesAll(removedLst);
		System.out.println(lst);

		System.out.println(lst.contains(5));
	//	java.util.Iterator<Integer> itr = lst.iterator();
	/*	while(itr.hasNext())
		{
			itr.next();
			itr.remove();
			System.out.println(lst);
		}*/
	}
}

