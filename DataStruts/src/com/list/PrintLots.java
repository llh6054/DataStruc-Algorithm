package com.list;

public class PrintLots
{
	
	private MyArrayList<Integer> subList = new MyArrayList<>();

	public static void main(String[] args)
	{
		MyArrayList<Integer> l = new MyArrayList<>();
		MyArrayList<Integer> p = new MyArrayList<>();
		System.out.println("test");
		for(int i = 20; i < 50; i++)
			l.add(i);

		for(int i = 0; i < 5; i++)
			p.add((int)Math.floor(Math.random() * 10));
		
		System.out.println(l);
		System.out.println(p);
		PrintLots pl = new PrintLots();
		MyArrayList<Integer> subList = pl.printLots(l, p);
		System.out.println(subList);

	}
	
	/**
		Print elements in P at the specific position that is pointed by L.
		@param l the list to be print.
		@param p the list to point the position.
		@return the subList 
	*/
	public MyArrayList<Integer> printLots(MyArrayList<Integer> l, MyArrayList<Integer> p) 
	{
		int index;
		int tempVal;
		for(int i = 0; i < p.size(); i++) 
		{
			index = (Integer)p.get(i);
			tempVal = (Integer)l.get(index);
			subList.add(tempVal);
		}

		return subList;
	}
}