/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementations;

/**
 *
 * @author thedu
 */
public class BSTreeNode<E> {
    E value;
    BSTreeNode<E> left;
    BSTreeNode<E> right;

    public E getElement() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public BSTreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(BSTreeNode<E> left) {
        this.left = left;
    }

    public BSTreeNode<E> getRight() {
        return right;
    }

    public void setRight(BSTreeNode<E> right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "\t" + value;
    }
    
    public boolean isLeaf() {
        return (left == null && right == null);
    }
    
    public boolean hasLeft() {
        return !(left==null);
    }
    
    public boolean hasRight(){
        return !(right==null);
    }
    
}
