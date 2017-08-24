/**
 * 
 */
package com.tree;

/**
 * Implements of unbalanced binary search tree.
 * 2017/08/24
 * @author chubby
 *
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
	
	//the tree root
	private BinaryNode<AnyType> root;
	
	
	public BinarySearchTree() {
		root = null;
	}
	
	/**
	 * Make the tree logical empty.
	 */
	public void makeEmpty() {
		root = null;
	}
	
	/**
	 * Test the tree is logical empty.
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * Find an item in the tree
	 * @param x
	 * @return
	 */
	public boolean contains(AnyType x) {
		return contains(x, root);
	}
	
	/**
	 * Internal method to find an item in a subtree.
	 * @param x
	 * @param root
	 * @return
	 */
	private boolean contains(AnyType x, BinaryNode<AnyType> t) {
		if(t == null)
			return false;
		
		int compareResult = x.compareTo(t.element);
		
		if(compareResult < 0) 
			return contains(x, t.left);
		if(compareResult > 1)
			return contains(x, t.right);
		else
			return true;	//match
		
	}
	
	/**
	 * Find the smallest item in the tree.
	 * @return smallest item.
	 */
	public AnyType findMin() {
		if(isEmpty())
			throw new UnderFlowException();
		
		return findMin(root).element;
	}
	
	/**
	 * Internal method find the smallest item in the tree.
	 * @return smallest item null if tree is empty.
	 */
	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
		if(t == null)
			return null;
		else if(t.left == null)
			return t;
		return findMin(t.left);
	}
	
	/**
	 * Find the largest item in the tree.
	 * @return the largest item in the tree, null if empty.
	 */
	public AnyType findMax() {
		if(isEmpty())
			throw new UnderFlowException();
		
		return findMax(root).element;
	}
	
	/**
	 * Internal method find the largest item in the tree.
	 * @param t root of the tree
	 * @return largest item in the tree.
	 */
	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
		if(t == null)
			return null;
		else if(t.right == null)
			return t;
		return findMax(t.right);
	}
	
	/**
	 * Insert an item to the tree.
	 * @param x
	 */
	public BinaryNode<AnyType> insert(AnyType x) {
		return insert(x, root);
	}
	
	/**
	 * Internal method to insert an item into a tree.
	 * @param x item to insert to the subtree.
	 * @param t the node that roots the subtree.
	 * @return new root of the subtree.
	 */
	private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
		if(t == null)
			return new BinaryNode<>(x, null, null);
		
		int compareResult = x.compareTo(t.element);
		
		if(compareResult < 0) 
			t.left = insert(x, t.left);
		if(compareResult > 0)
			t.right = insert(x, t.right);
		else
			;		//Duplicate, do nothing
		return t;
	}
	
	private static class BinaryNode<AnyType> {
		
		//data in the node
		AnyType element;
		
		//left child
		BinaryNode<AnyType> left;
		
		//right child
		BinaryNode<AnyType> right;
		
		/**
		 * constructor
		 * @param theElement
		 */
		BinaryNode(AnyType theElement) {
			this(theElement, null, null);
		}
		
		/**
		 * constructor
		 */
		BinaryNode(AnyType theElement,  BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
			element = theElement;
			left = lt;
			right = rt;
		}
	}
	
}
