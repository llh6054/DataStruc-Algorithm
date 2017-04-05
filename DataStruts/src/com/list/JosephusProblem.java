package com.list;

/**
 * @author chubby
 * 2017/03/29
 * 
 * Լɪ������
 * n���˴�1��n��ÿ��m���˽��п�ͷ��ȫ������.
 */
public class JosephusProblem {

	public static void main(String[] args) {
		JosephusProblem jp = new JosephusProblem();
		jp.solveJosephusProlem(0, 5);
		System.out.println();
		jp.solveJosephusProlem(1, 5);
		System.out.println();
	}
	
	public void solveJosephusProlem(int m, int n) {
		
		MyLinkedList<Integer> list = new MyLinkedList<>();
		
		//initialize 
		for(int i = 1; i <= n; i++) {
			list.add(i);
		}
		
		int i = 0;
		int skip = m + 1;
		while(i < n) {
			System.out.print(list.get(m) + " ");
			list.set(m, 0);
			m += skip;
			if(m >= n)
				m = m - n;
			i++;
		}
	}

}
