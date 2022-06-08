package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Stack;

/**
 * @param <T>
 * The command stack must be an instance of StackImpl<Undoable>.
 * If a command involves a single document, you will create and push an instance of GenericCommand onto the command stack.
 * If, however, the command involves multiple documents / URIs, you will use an instance of CommandSet to capture the information
 * about the changes to each document.
 * The CommandSet should only be removed from the command stack once the CommandSet has no commands left in it due to undo(uri)
 * being called on the URIs of all the commands in the command set.
 */
public class StackImpl<T> implements Stack<T> {
    private Node<T> head;
    private int numberOfNodes;

    public StackImpl() {
        this.head = null;
        this.numberOfNodes = 0;
    }

    /**
     * Increment top before adding the new element
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
     * Decrement top after removing the element
     * removes and returns element at the top of the stack
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