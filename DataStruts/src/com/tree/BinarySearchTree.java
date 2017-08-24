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
		else if(compareResult > 0)
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
	public void insert(AnyType x) {
		root = insert(x, root);
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
		else if(compareResult > 0)
			t.right = insert(x, t.right);
		else
			;		//Duplicate, do nothing
		return t;
	}
	
	/**
	 * Remove an item from tree, nothing is done if x not found.
	 * @param x the item to remove.
	 */
	public void remove(AnyType x) {
		root = remove(x, root);
	}
	
	/**
	 * Internal method to remove an item from a subtree.
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
		if(t == null)
			return t;	//item not found, do nothing
		
		int compareResult = x.compareTo(t.element);
		
		if(compareResult < 0) 
			t.left = remove(x, t.left);
		else if(compareResult > 0)
			t.right = remove(x, t.right);
		else if(t.left != null && t.right != null) { //two children
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		}
		else
			t = (t.left != null) ? t.left : t.right;
		
		return t;
	}
	
	
	/**
	 * Print tree.
	 */
	public void printTree() {
		if(isEmpty())
			System.out.println("Empty tree");
		else
			printTree(root);
	}
	
	/**
	 * Internal method print a subTree in sorted order.
	 * @param t the node that roots subtree.
	 */
	private void printTree(BinaryNode<AnyType> t) {
		if(t != null) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}
	
	/**
	 * Internal method to compute the height of a subtree.
	 * @param t the node that roots subtree.
	 * @return height of subtree
	 */
	private int height(BinaryNode<AnyType> t) {
		if(t == null)
			return -1;
		else
			return 1 + Math.max(height(t.left), height(t.right));
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
	
	
	//Test program.
	public static void main( String [ ] args ) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>( );
        final int NUMS = 4000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            t.insert( i );
        //t.printTree();
        for( int i = 1; i < NUMS; i+= 2 )
            t.remove( i );
        t.printTree();
        if( NUMS < 40 )
            t.printTree( );
       // t.printTree();
        if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
            System.out.println( "FindMin or FindMax error!" );

        for( int i = 2; i < NUMS; i+=2 )
             if( !t.contains( i ) )
                 System.out.println( "Find error1!" );

        for( int i = 1; i < NUMS; i+=2 ) {
            if( t.contains( i ) )
                System.out.println( "Find error2!" );
        }
    }
	
}
