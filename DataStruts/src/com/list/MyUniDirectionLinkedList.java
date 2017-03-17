package com.list;

public class MyUniDirectionLinkedList<AnyType>
{
	private Node<AnyType> first;	//a header node
	private int position = 0;	//position of the node
	private int theSize;

	/**
		Construct a header node.
	*/
	public MyUniDirectionLinkedList()
	{
		first = null;
	}


	/**
		size of list
	*/
	public int size()
	{
		return theSize;
	}

	/**
		Add a node at the first place.
		@param data the data of the node.
	*/
	public void addFirstNode(AnyType data)
	{
		Node<AnyType> node = new Node<>(data);
		node.next = first;
		first = node;
		theSize++;
	}
	
	/**
		Delete the first node.
		@return node has been deleted.
	*/
	public Node<AnyType> deleteFirstNode()
	{
		Node<AnyType> node = first;
		first = first.next;
		theSize--;
		return node;
	}
	
	/**
		Insert node at any position, insert before index.
		@param data node data to be inserted.
		@param idx index of the node 
	*/
	public void add(int idx, AnyType data)	
	{
		checkRange(idx);

		Node<AnyType> node = new Node<>(data);
		Node<AnyType> current = first;
		Node<AnyType> previous = null;
		
		if(idx == 0)	//first node
		{
			addFirstNode(data);
			return;
		}

		while((idx != 0) && (position != idx)) //common situation
		{
			previous = current;
			current = current.next;
			position++;
		}

		previous.next = node;
		node.next = current;
		position = 0;
		theSize++;
	}

	/**
		Delete node in specific postion.
		@param idx index of the postion.
		@return node has been deleted.
	*/
	public Node<AnyType> deleteByPos(int idx)	//encounter some problem.
	{
		checkRange(idx);
		
		Node<AnyType> previous;
		Node<AnyType> current = first;
		if(idx == 0)		//if it's first node
		{
			first = first.next;
		}
		else	//common situation
		{
			do
			{
				previous = current;
				current = current.next;
				position++;
			}
			while (position < idx);
			previous.next = current.next;
		}
		
		position = 0;
		theSize--;

		return current;
	}

	/**
		Delete node by data, only delete first node.
		@param data data to be matched.
		@return Node Node has been deleted, null if there's no match.
	*/
	public Node<AnyType> deleteByData(AnyType data)
	{
		Node<AnyType> tempNode = null;
		Node<AnyType> previous = null;
		Node<AnyType> current = first;
		if(findNodeByData(data) == null)
			return null;

		if(first.data == data)		//match first node
		{
			tempNode = deleteFirstNode();
			return tempNode;
		}	

		for(int i = 1; i < size(); i++) // common situation
		{
			previous = current;
			if(current.data == data) 
				tempNode = current;
			else
				current = current.next;
		}

		previous.next = current.next;
		theSize--;

		return tempNode;
	}

	/**
		Find node by position.
		@param idx index of the node.
		@return Node node at index postion.
	*/
	public AnyType findNodeByPos(int idx)
	{
		Node<AnyType> current = first;
		checkRange(idx);
		
		while(position < idx)
		{
			current = current.next;
			position++;
		}

		position = 0;
		return current.data;
	}

	/**
		Change adjacent data.
		@param idx index to be changed.
		@return true if changed success, otherwise false.
	*/
	public boolean changeData(int idx)
	{
		
		Node<AnyType> temp;
		Node<AnyType> current = first;
		Node<AnyType> prev = null;

		if(idx < 0 || idx+1 > size())
			throw new IndexOutOfBoundsException("findNodeByPos index: " + idx + "; size: " + size());	//can't be the lastest node
		
		if(idx == 0)	//first node.
		{
			temp = first.next;
			first.next = first.next.next;
			temp.next = first;
			first = temp;	
		}
		else	//common situation
		{
			while(position < idx)
			{
				prev = current;
				current = current.next;
				position++;
			}
			
			temp = current.next.next;
			prev.next = current.next;
			current.next.next = current;
			prev.next.next.next = temp; 

		}

		position = 0;
		return true;
	}

	/**
		Change Interval data of the list.
		@param idx specific index to be changed
		@return true is changed success
	*/
	public boolean changeIntervalData(int idx)
	{
		Node<AnyType> prev = null;
		Node<AnyType> current = first;
		Node<AnyType> temp;

		if(idx < 0 || idx+2 >= size())
			throw new IndexOutOfBoundsException("findNodeByPos index: " + idx + "; size: " + size());
		
		while(position < idx)
		{
			prev = current;
			current = current.next;
			position++;
		}

		temp = current.next.next.next;
		prev.next = current.next.next;
		prev.next.next = current.next;
		prev.next.next.next = current;
		prev.next.next.next.next = temp;

		position = 0;
		return true;
	}

	/**
		Reverse the list.
		@return first node or head.
	*/
	public Node<AnyType> reverse()
	{
		Node<AnyType> head = first;
		Node<AnyType> p = head.next;
		Node<AnyType> q = null;
		head.next = null;

		if(size() < 2)
			throw new UnsupportedOperationException("Operation is unspported for size: " + size());
		
		while(p != null)
		{
			q = p;
			p = p.next;
			q.next= head;
			head = q;
		}
		
		first = head;
		return head;
	}

	/**
		Find node by data, only find the first occurence data, null if no match.
		@param data data to be matched
		@return data of the first matched Node
	*/
	public AnyType findNodeByData(AnyType data)
	{
		Node<AnyType> current = first;
		boolean hasMatched = false;
		while(position < size())
		{
			if(current.data == data)
			{
				hasMatched = true;
				break;
			}
			current = current.next;
			position++;
		}
		position = 0;
		return hasMatched ? current.data : null;
	}


	/**
		Check index whether is between 0 and theSize.
	*/
	public void checkRange(int idx)
	{
		if(idx < 0 || idx >= size())
			throw new IndexOutOfBoundsException("findNodeByPos index: " + idx + "; size: " + size());
	}


	/**
		Display all nodes
	*/
	public void displayAllNodes() 
	{
		Node<AnyType> current = first;
		while(current != null)
		{
			current.display();
			current = current.next;
		}
		System.out.println(" ");
	}

	/**
		Node class.
	*/
	@SuppressWarnings("hiding")
	private class Node<AnyType> 
	{
		public AnyType data;
		public Node<AnyType> next;

		public Node(AnyType data)
		{
			this.data = data;
		}
		
		/**
			display data
		*/
		public void display() 
		{
			System.out.print(data + " ");
		}
	}
}

class TestUniDirectionLinkedList
{
	public static void main(String[] args)
	{
		MyUniDirectionLinkedList<Integer> lst = new MyUniDirectionLinkedList<>();
		lst.addFirstNode(23);
		lst.addFirstNode(25);
		lst.displayAllNodes();
		lst.add(1, 2);
		lst.displayAllNodes();
		lst.deleteByPos(1);
//		lst.deleteByData(23);
//		lst.displayAllNodes();
		for(int i = 0; i < 10; i++)
			lst.addFirstNode(i);
//		lst.changeData(1);
//		lst.changeIntervalData(1);
		lst.reverse();
		lst.displayAllNodes();
		if(lst.findNodeByPos(1) != null)
			System.out.println("find");
		
	}
}