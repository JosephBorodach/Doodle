package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.Stack;
/**
 * @param <T>
 * The command stack must be an instance of StackImpl<Undoable>.
 *      what does this mean?
 * If a command involves a single document, you will create and push an instance of GenericCommand onto the command stack.
 * If, however, the command involves multiple documents / URIs, you will use an instance of CommandSet to capture the information
 * about the changes to each document.
 * The CommandSet should only be removed from the command stack once the CommandSet has no commands left in it due to undo(uri)
 * being called on the URIs of all the commands in the command set.
 */
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
     * Increment top before adding the new element
     * @throws IllegalArgumentException [If push is passed a null element throw an IllegalArgumentException
     * @param element object to add to the Stack
     * 
     * QU: If element is a generic than we don't need to worry about types right?  
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
/**
 * Add Undo Support via a Command Stack
 * 1. Every call to DocumentStore.putDocument and DocumentStore.deleteDocument must result in the adding of a new instance of edu.yu.cs.com1320.project.Command to a single Stack which serves as your command stack
 * a. No other class besides your document store may have any access/references to the command stack; it must be a private field encapsulated within DocumentStoreImpl
 * b. You must use the Command class given to you to model commands. You may not alter in any way, or subclass, Command.
 * c. You must write a class called edu.yu.cs.com1320.project.impl.StackImpl which is found in its own .java file and implements the interface provided to you called edu.yu.cs.com1320.project.Stack, and your command stack must be an instance of StackImpl.
 * d. StackImpl must have a constructor that takes no arguments.
 * 2. If a user calls DocumentStore.undo(), then your DocumentStore must undo the last command on the stack
 * 3. If a user calls DocumentStore.undo(URI),then your DocumentStore must undo the last command on the stack
 * that was done on the Document whose key is the given URI, without having any permanent effects on any commands
 * that are on top of it in the command stack.
 * 4. Undo must be achieved by DocumentStore calling the Command.undo method on the Command that represents the
 * action to be undone. DocumentStore may not implement the actual undo logic itself, although it must manage the command stack and determine which undo to call on which Command.
 */ 

