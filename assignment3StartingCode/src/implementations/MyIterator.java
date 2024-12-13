/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package implementations;
import java.util.NoSuchElementException;
import java.util.List;
import utilities.Iterator;

/**
 *
 * @author thedu
 * @param <E>
 */


public class MyIterator<E> implements Iterator<E> {
    private final List<E> list;
    private int currentIndex = 0; 

    public MyIterator(List<E> list) {
        this.list = list; 
    }

    @Override
    public boolean hasNext() {
        return currentIndex < list.size(); 
    }

    @Override
    public E next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the iterator");
        }
        return list.get(currentIndex++); 
    }
}

