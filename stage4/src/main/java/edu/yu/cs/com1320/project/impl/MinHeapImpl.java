package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.MinHeap;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MinHeapImpl <E extends Comparable<E>> extends MinHeap<E> { //protected E[] elements;
    private int count;
    /**
     * MinHeapImpl must have a constructor that takes no arguments.
     */
    public MinHeapImpl() {
        this.count = 5;
        this.elements = (E[]) new Comparable[this.count];
    }

    /**
     * After a Document is used and its lastUsedTime is updated, that Document may now be in the wrong place in the Heap, therefore you must call MinHeapImpl.reHeapify.
     * The job of reHeapify is to determine whether the Document whose time was updated should stay where it is, move up in the heap, or move down in the heap, and then carry out any move that should occur.
     * @throws IllegalArgumentException
     * @param element
     */
    @Override
    public void reHeapify(E element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        if (this.isEmpty()) {
            return;
        }
        int index = this.getArrayIndex(element);
        this.downHeap(index);
        index = this.getArrayIndex(element);
        this.upHeap(index);
    }

    /**
     * @param element
     * @return
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
     */
    @Override
    protected int getArrayIndex(E element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        int i = 1;
        while (i <= this.count) {
            if (elements[i] == null) {
                throw new NoSuchElementException();
            }
            if (elements[i].equals(element)) {
                return i;
            }
            i++;
        }
        throw new NoSuchElementException();
    }

    @Override
    protected void doubleArraySize() {
        this.count *= 2;
        this.elements = Arrays.copyOf(this.elements, this.count);
    }
}