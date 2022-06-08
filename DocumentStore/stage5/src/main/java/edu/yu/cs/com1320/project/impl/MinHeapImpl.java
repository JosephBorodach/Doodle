package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.MinHeap;
//import edu.yu.cs.com1320.project.BTree;
//import edu.yu.cs.com1320.project.impl.BTreeImpl;

import java.util.*;

public class MinHeapImpl <E extends Comparable<E>> extends MinHeap<E> {
    protected int count;
    //private BTree bTree;

    /*
    public MinHeapImpl(BTreeImpl bTree) {
        if (bTree == null) {
            throw new IllegalArgumentException();
        }
        this.bTree = bTree;
        this.count = 5;
        elements = (E[]) new Comparable[this.count];
    }
    */

    public MinHeapImpl() {
        this.count = 5;
        elements = (E[]) new Comparable[this.count];
    }

    /**
     * After a Document is used and its lastUsedTime is updated, that Document may now be in the wrong place in the Heap, therefore you must call MinHeapImpl.reHeapify.
     * The job of reHeapify is to determine whether the Document whose time was updated should stay where it is, move up in the heap, or move down in the heap, and then carry out any move that should occur.
     * @param element
     */
    @Override
    public void reHeapify(E element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        if (!this.isEmpty()) {
            int index = this.getArrayIndex(element);
            this.downHeap(index);
            this.upHeap(index);
        }
    }

    /**
     * @param element
     * @return
     */
    @Override
    protected int getArrayIndex(E element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < this.count; i++) {
            if (this.elements[i] == null) {
                throw new NoSuchElementException();
            }
            if (this.elements[i].equals(element)) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    protected void doubleArraySize() {
        this.count *= 2;
        this.elements = Arrays.copyOf(this.elements, this.elements.length * 2);
    }
}