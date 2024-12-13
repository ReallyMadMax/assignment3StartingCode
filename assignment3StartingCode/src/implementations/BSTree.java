/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementations;
import java.util.ArrayList;
import java.util.List;
import utilities.BSTreeADT;
import utilities.Iterator;

/**
 *
 * @author thedu
 * @param <E>
 */
public class BSTree<E extends Comparable<E>> implements BSTreeADT<E> {
    

    public BSTree() {
        size = 0;
        height = 0;
        root = null;
    }
    
    int size;
    int height;
    BSTreeNode<E> root;

    public BSTree(BSTreeNode<E> root) {
        this.root = root;
        size = 1;
        height = 1;
    }
    
    @Override
    public boolean add(E newEntry) {
        if (newEntry == null) {
            throw new NullPointerException("Cannot add a null element to the tree.");
        }

        BSTreeNode<E> newNode = new BSTreeNode<>();
        newNode.setValue(newEntry);

        if (isEmpty()) {
            root = newNode;
            size++;
            return true; 
        } else {
            return add(newNode, root);
        }
    }

    private boolean add(BSTreeNode<E> node, BSTreeNode<E> root) {
        if (node.getElement().compareTo(root.getElement()) < 0) {
            // traverse left
            if (!root.hasLeft()) {
                root.setLeft(node);
                size++;
                return true; // added node
            } else {
                return add(node, root.getLeft()); // recursion left
            }
        } else if (node.getElement().compareTo(root.getElement()) > 0) {
            // traverse right
            if (!root.hasRight()) {
                root.setRight(node);
                size++;
                return true; //added node
            } else {
                return add(node, root.getRight()); // recursion right
            }
        } else {
            return false; // Node not added
        }
    }
        
     @Override
    public boolean isEmpty() {
        return root==null;
    }
    
    
    @Override
    public BSTreeNode<E> getRoot() throws NullPointerException {
        if (isEmpty()) {
            throw new NullPointerException("The tree is empty.");
        }
        return root;
    }
    
    @Override
    public int getHeight() {
        return calculateHeight(root);
    }
    
    private int calculateHeight(BSTreeNode<E> node) {
    if (node==null) {
            return 0;
        }
        //we use recursion to calculate the heights of the left and right subtrees
        int leftHeight = calculateHeight(node.getLeft());
        int rightHeight = calculateHeight(node.getRight());
        
        return Math.max(leftHeight, rightHeight) +1;
    }
    
    @Override
    public int size()
    {
        return size;
    }
    
    @Override
    public void clear() {
        root = null;
        size = 0;
        height = 0;
    }
    
    @Override
    public BSTreeNode<E> search(E entry) throws NullPointerException {
     if (entry == null) {
         throw new NullPointerException("Can't search for a null element.");
     }   
     return recursiveSearch(entry, root);
    }
    
    
    private BSTreeNode<E> recursiveSearch(E entry, BSTreeNode<E> node) {
        if (node == null) {
            return null;
        }
    
        int compare = entry.compareTo(node.getElement());
        
        if (compare==0) {
            //if node is found compareTo will return 0
            return node;
        }
        //compareTo will return -1 if entry is less than node
        //then searches left
        else if (compare < 0) {
            return recursiveSearch(entry, node.getLeft());
        }
        //compareTo will return 1 if entry is larger than node
        //then searches right
        else {
            return recursiveSearch(entry, node.getRight());
        }
    }
    
    //contains and search are practically the same logic, search will return the value of the node
    //therefore we can use search as contains will return true or false is search finds the node
    @Override
    public boolean contains(E entry) throws NullPointerException {
        //
        return search(entry) != null;
    }
    
    @Override
    public BSTreeNode<E> removeMin() {
        if (isEmpty()) {
            return null;
        }
        
        BSTreeNode<E> current = root;
        BSTreeNode<E> parent = null;
        
        //traverse as far left as we can
        while (current.hasLeft()) {
            parent=current;
            current = current.getLeft();
        }
        
        BSTreeNode<E> minNode = current;
        //if parent is null minNode is the root
        if (parent==null) {
            root = root.getRight();
        }
        else {
            parent.setLeft(current.getRight());
        }
        size--;
        return minNode;
    }
    
    
    @Override
    public BSTreeNode<E> removeMax() {
        if (isEmpty()) {
            return null;
        }
        
        BSTreeNode<E> current = root;
        BSTreeNode<E> parent = null;
        
        //traverse as far right as we can
        while (current.hasRight()) {
            parent=current;
            current = current.getRight();
        }
        
        BSTreeNode<E> maxNode = current;
        //if parent is null maxNode is the root
        if (parent==null) {
            root = root.getLeft();
        }
        else {
            parent.setRight(current.getLeft());
        }
        size--;
        return maxNode;
    }

   
    
    
    private void preorderIterator(BSTreeNode<E> node, List<E> orderedList) {
        if (node==null) {
            return;
        }
        //Preorder is VLR
        orderedList.add(node.getElement()); // visit the node and store it
        if(node.hasLeft())
            preorderIterator(node.getLeft(), orderedList); //traverse left
        if(node.hasRight())
                preorderIterator(node.getRight(), orderedList); //traverse right
    }
    
    @Override
    public Iterator<E> preorderIterator()
    {
        List<E> orderedList = new ArrayList<>();
        preorderIterator(root, orderedList);
        return new MyIterator<> (orderedList); 

    }
    
    
    private void inorderIterator(BSTreeNode<E> node, List<E> orderedList) {
        if(node==null) {
            return;
        }
        //Inorder is LVR
        if(node.hasLeft())
            inorderIterator(node.getLeft(), orderedList);
        orderedList.add(node.getElement()); 
        if(node.hasRight())
            inorderIterator(node.getRight(), orderedList);
    }
    
    @Override
    public Iterator<E> inorderIterator()
    {
        List<E> orderedList = new ArrayList<>();
        inorderIterator(root, orderedList);
        return new MyIterator<> (orderedList);
        
    }
    
    private void postorderIterator(BSTreeNode<E> node, List<E> orderedList) {
        //Post order is LRV
        if(node.hasLeft())
            postorderIterator(node.getLeft(), orderedList);
        if(node.hasRight())
            postorderIterator(node.getRight(), orderedList);
        orderedList.add(node.getElement()); 
    }
    
    @Override
    public Iterator<E> postorderIterator()
    {
        List<E> orderedList = new ArrayList<>();
        postorderIterator(root, orderedList);
        return new MyIterator<> (orderedList);
    }
    
}
