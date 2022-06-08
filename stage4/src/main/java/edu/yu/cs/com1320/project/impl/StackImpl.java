package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.Stack;
public class StackImpl<T> implements Stack<T> {
    private Node<T> head;
    private int numberOfNodes;

    /**
     * StackImpl must have a constructor that takes no arguments.
     */
    public StackImpl() {
        this.head = null;
        this.numberOfNodes = 0;
    }

    /**
     * @throws IllegalArgumentException [If push is passed a null element throw an IllegalArgumentException
     * @param element object to add to the Stack
     */
    public void push(T element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        this.head = new Node<T>(element, this.head);
        this.numberOfNodes++;
    }

    /**
     * Decrement top after removing the element removes and returns element at the top of the stack
     * @return element at the top of the stack, null if the stack is empty
     */
    public T pop() {
        if (this.head == null) {
            return null;
        }
        T returnElement = this.head.element;
        this.head = this.head.next;
        this.numberOfNodes--;
        return returnElement;
    }

    /**
     * We should return a copy of the top
     * @return the element at the top of the stack without removing it [if the stack is empty, return null]
     */
    public T peek() {
        if (this.head == null) {
            return null;
        }
        T returnElement = this.head.element;
        return returnElement;
    }

    /**
     * @return how many elements are currently in the stack
     */
    public int size() {
        int size = this.numberOfNodes;
        return size;
    }

    private class Node<T> {
        private final T element;
        private Node<T> next;
        private Node (T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }
    }
}