package com.list;
/**
	Given two sorted lists, L 1 and L 2 ,compute L 1 U L 2 and L 1 N L 2 
	using only the basic list operations.
*/
public class ListOperation
{
	public static void main(String[] args)
	{
		MyUniDirectionLinkedList<Integer> lst1 = new MyUniDirectionLinkedList<>();
		MyUniDirectionLinkedList<Integer> lst2 = new MyUniDirectionLinkedList<>();
		MyUniDirectionLinkedList<Integer> andResult = new MyUniDirectionLinkedList<>();
		MyUniDirectionLinkedList<Integer> uniResult = new MyUniDirectionLinkedList<>();

		for(int i = 1; i < 6; i++)
			lst1.addFirstNode(i);

		for(int i = 4; i < 9; i++)
			lst2.addFirstNode(i);
		
		ListOperation lao = new ListOperation();
		andResult = lao.interSectionOperation(lst1, lst2);
		andResult.displayAllNodes();

		uniResult = lao.unionOperation(lst1, lst2);
		uniResult.displayAllNodes();


	}

	/**
		The intersection of L1 and L2, L1 and L2 must be sorted descend.
		@param lst1.
		@param lst2.
		@return intersection of L1 and L2.
	*/
	public MyUniDirectionLinkedList<Integer> interSectionOperation(MyUniDirectionLinkedList<Integer> lst1, MyUniDirectionLinkedList<Integer> lst2) 
	{
		MyUniDirectionLinkedList<Integer> andResult = new MyUniDirectionLinkedList<>();
		for(int i = 0; i < lst1.size(); i++)
		{
			for(int j = 0; j < lst2.size(); j++)
			{

				if(lst1.findNodeByPos(i) > lst2.findNodeByPos(j))
				{
					break;
				}

				if (lst1.findNodeByPos(i) == lst2.findNodeByPos(j))
				{
					andResult.addFirstNode(lst1.findNodeByPos(i));
				}
			
			}
				
		}
		
		return andResult;
	}

	/**
		The union of L1 and L2, L1 and L2.
		@param lst1.
		@param lst2.
		@return union of L1 and L2.
	*/
	public MyUniDirectionLinkedList<Integer> unionOperation(MyUniDirectionLinkedList<Integer> lst1, MyUniDirectionLinkedList<Integer> lst2)
	{
		MyUniDirectionLinkedList<Integer> uniResult = new MyUniDirectionLinkedList<Integer>();
		
		if(lst1 == null)
			return lst2;

		if(lst2 == null)
			return lst1;
		
		uniResult = lst1; 

		for(int i = 0; i < lst2.size(); i++) 
		{
			if(uniResult.findNodeByData(lst2.findNodeByPos(i)) == null)
			{
				uniResult.addFirstNode(lst2.findNodeByPos(i));
			}
		}

		return uniResult;
	}

}