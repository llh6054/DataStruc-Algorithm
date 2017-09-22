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
	
	/**
	 * Return the height of node t, -1 if null.
	 * @param t
	 * @return
	 */
	private int height(AvlNode<AnyType> t) {
		return t == null ? -1 : t.height;
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
		if(t == null)
			return t;
		
		int compareResult = x.compareTo(t.element);
		
		if(compareResult < 0)
			t.left =  remove(x, t.left);
		else if(compareResult > 0)
			t.right = remove(x, t.right);
		else if(t.left != null && t.right != null) {	//have to children.
			t.element = findMin(t.right).element;
			t.right =  remove(t.element, t.right);
		}else
			t = (t.left != null ? t.left : t.right);
		
		return balance(t);
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
	
	public boolean contains(AnyType x) {
		return contains(x, root);
	}
	
	/**
	 * Internal method to find an item in a subtree.
	 * @param x the item to find.
	 * @param t	node that roots the subtree.
	 * @return true if found, else false.
	 */
	private boolean contains(AnyType x, AvlNode<AnyType> t) {
		
		while(t != null) {
			int compareResult = x.compareTo(t.element);
			if(compareResult < 0)
				t = t.left;
			else if(compareResult > 0)
				t = t.right;
			else		//Match
				return true;
		}
		
		return false;
	}
	
	public void printTree() {
		if(isEmpty())
			System.out.println("empty tree");
		else
			printTree(root);
	}
	
	/**
	 * Internal method print subtree.
	 * @param t node that roots the subtree.
	 */
	private void printTree(AvlNode<AnyType> t) {
		if(t != null) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}
	
	private static final int ALLOWED_IMBALANCE = 1;
	
	private AvlNode<AnyType> balance(AvlNode<AnyType> t) {
		if(t == null)
			return t;
		
		if(height(t.left) - height(t.right) > ALLOWED_IMBALANCE)
			if(height(t.left.left) >= height(t.left.right))
				t = rotateWithLeftChild(t);
			else
				t = doubleWithLeftChild(t);
		else if(height(t.right) - height(t.left) > ALLOWED_IMBALANCE)
			if(height(t.right.left) >= height(t.right.right))
				t = rotateWithRightChild(t);
			else
				t = doubleWithRightChild(t);
	}
	
	/**
	 * Rotate binary tree node with left child.
	 * For AVL trees, this is a single rotation for case 1.
	 * Update heights, then return new root.
	 * @param k2
	 * @return
	 */
	private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> k2) {
		AvlNode<AnyType> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}
	
	/**
	 * Rotate binary tree node with right child.
	 * For AVL trees, this is a single rotation for case 4.
	 * Update heights, then return new root.
	 * @param k1
	 * @return
	 */
	private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> k1) {
		AvlNode<AnyType> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		k2.height = Math.max(k1.height, height(k2.right)) + 1;
		return k2;
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
