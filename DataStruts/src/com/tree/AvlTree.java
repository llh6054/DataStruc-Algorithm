package com.tree;

public class AvlTree<AnyType extends Comparable<? super AnyType>> {
	
	private AvlNode<AnyType> root;	//The tree root.
	
	public AvlTree() {
		root = null;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public void makeEmpty() {
		root = null;
	}
	
	public void insert(AnyType x) {
		root = insert(x, root);
	}
	
	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots subtree.
	 * @return new root of the subtree.
	 */
	private AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> t) {
		if(t == null)
			return new AvlNode<>(x, null, null);
		
		int compareResult = x.compareTo(t.element);
		
		if(compareResult < 0)
			t.left = insert(x, t.left);
		else if(compareResult > 0)
			t.right = insert(x, t.right);
		else
			;	//Duplicate, do nothing
		return balance(t);
	}
	
	public void remove(AnyType x) {
		root  = remove(x, root);
	}
	
	/**
	 * Internal method to remove from a subtree.
	 * @param x the item to remove.
	 * @param t	node that roots the subtree.
	 * @return the new root of subtree.
	 */
	private AvlNode<AnyType> remove(AnyType x, AvlNode<AnyType> t) {
		
	}
	
	public AnyType findMin() {
		if(isEmpty())
			throw new UnderFlowException();
		return findMin(root).element;
	}
	
	
	/**
	 * Internal method to find a smallest item in a subtree.
	 * @param t node that roots the subtree.
	 * @return node that contains the smallest item.
	 */
	private AvlNode<AnyType> findMin(AvlNode<AnyType> t) {
		if(t == null)
			return t;
		
		while(t.left != null)
			t = t.left;
		return t;
	}
	
	public AnyType findMax() {
		if(isEmpty())
			throw new UnderFlowException();
		return findMax(root).element;
	}
	
	/**
	 * Internal method to find a largest item in a subtree.
	 * @param t node that roots the subtree.
	 * @return node that contains the largest item.
	 */
	private AvlNode<AnyType> findMax(AvlNode<AnyType> t) {
		if(t == null)
			return t;
		
		while(t.right != null)
			t = t.right;
		return t;
	}
	
	
	private static class AvlNode<AnyType> {
		
		AnyType element;
		AvlNode<AnyType> left;
		AvlNode<AnyType> right;
		int height;
		
		AvlNode(AnyType theElement) {
			this(theElement, null, null);
		}
		
		AvlNode(AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt) {
			element = theElement;
			left = lt;
			right = rt;
			height = 0;
		}
		
	}
}
