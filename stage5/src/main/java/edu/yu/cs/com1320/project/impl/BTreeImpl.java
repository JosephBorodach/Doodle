package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.BTree;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.IOException;

/**
 * Max: max children per B-tree node = MAX-1 (must be an even number and greater than 2)
 * @param <Key>
 * @param <Value>
 */
public class BTreeImpl<Key extends Comparable<Key>, Value> implements BTree<Key, Value> {
    private static final int MAX = 6;
    private Node root;
    private int height;
    private int size;
    private Node leftMostExternalNode;
    private PersistenceManager<Key, Value> manager;

    public BTreeImpl() {
        this.root = new Node(0);
        this.leftMostExternalNode = this.root;
    }

    private final class Node {
        private int entryCount;
        private Entry[] entries = new Entry[BTreeImpl.MAX]; //The array of children
        private Node next;
        private Node previous;

        /**
         * @param k - create a node with k entries
         */
        private Node(int k) {
            this.entryCount = k;
        }

        private void setNext(Node next) {
            this.next = next;
        }

        private Node getNext() {
            return this.next;
        }

        private void setPrevious(Node previous) {
            this.previous = previous;
        }

        @SuppressWarnings("unused")
        private Node getPrevious() {
            return this.previous;
        }

        private Entry[] getEntries() {
            return Arrays.copyOf(this.entries, this.entryCount);
        }
    }

    //internal nodes: only use key and child;
    //external nodes: only use key and value
    @SuppressWarnings("unchecked")
    private final class Entry<Key, Value> {
        private Comparable key;
        private Value val;
        private Node child;

        /**
         * @param key
         * @param val
         * @param child
         */
        private Entry(Comparable key, Value val, Node child) {
            this.key = key;
            this.val = val;
            this.child = child;
        }

        private Comparable getKey() {
            return this.key;
        }

        private void setValue(Value val) {
            this.val = val;
        }

        private Value getValue() {
            return this.val;
        }
    }

    /**
     * @param pm
     */
    @Override
    public void setPersistenceManager(PersistenceManager<Key,Value> pm) {
        this.manager = pm;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the
     *         symbol table and {@code null} if the key is not in the symbol
     *         table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    @Override
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        Entry entry = this.get(this.root, key, this.height);
        if (entry != null) {
            if (entry.getValue() == null && this.manager != null) {
                try {
                    Value v = this.manager.deserialize(key);
                    entry.setValue(v);
                    return v;
                } catch (IOException e) {
                    throw new IllegalStateException(); //e.printStackTrace();
                }
            }
            Value val = (Value) entry.getValue();
            return val;
        }
        return null;
    }

    /**
     * if statement in the else: if (we are at the last key in this node OR the key we are looking for is less than the next key, i.e. the desired key must be in the subtree below the current entry), then recurse into the current entry’s child
     * @param currentNode
     * @param key
     * @param height
     * @return
     */
    private Entry get(Node currentNode, Key key, int height) {
        Entry[] entries = currentNode.entries;
        if (height == 0) { //current node is external
            for (int i = 0; i < currentNode.entryCount; i++) {
                if(this.isEqual(key, entries[i].getKey())) {
                    return entries[i]; //found desired key - return its value
                }
            }
            return null; //didn't find the key
        } else { //current node is internal
            for (int i = 0; i < currentNode.entryCount; i++) {
                if (i + 1 == currentNode.entryCount || this.less(key, entries[i + 1].key)) {
                    return this.get(entries[i].child, key, height - 1);
                }
            }
            return null;
        }
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old
     * value with the new value if the key is already in the symbol table. If
     * the value is {@code null}, this effectively deletes the key from the symbol table.
     * @param key the key
     * @param val the value
     * @return the previous value or null if there was no previous value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("argument key to put() is null");
        }
        Entry alreadyThere = this.get(this.root, key, this.height);
        if (alreadyThere != null) { //if the key already exists in the b-tree, simply replace the value
            Value returnVal  = (Value) alreadyThere.getValue();
            if (returnVal == null && this.manager != null) {
                try {
                    returnVal = this.manager.deserialize(key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (val == null && this.manager != null) {
                try {
                    this.manager.delete(key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            alreadyThere.setValue(val);
            return returnVal;
        }
        Node newNode = this.put(this.root, key, val, this.height);
        this.size++;
        if (newNode != null) {
            //split the root: Create a new node to be the root. Set the old root to be new root's first entry.
            //Set the node returned from the call to put to be new root's second entry
            Node newRoot = new Node(2);
            newRoot.entries[0] = new Entry(this.root.entries[0].key, null, this.root);
            newRoot.entries[1] = new Entry(newNode.entries[0].key, null, newNode);
            this.root = newRoot;
            this.height++; //a split at the root always increases the tree height by 1
        }
        return null;
    }

    /**
     * int j - will be set to the the index in currentNode.entries[], where the new entry goes
     * 1st for loop: find index in currentNode’s entry[] to insert new entry we look for key < entry.key
     * since we want to leave j pointing to the slot to insert the new entry, hence we want
     * to find the first entry in the current node that key is LESS THAN
     * if statement in the else: if (we are at the last key in this node OR the key we are looking for is less than the next key,
     * i.e. the desired key must be added to the subtree below the current entry), then do a recursive call to put on the current entry’s child
     * @param currentNode
     * @param key
     * @param val
     * @param height
     * @return null if no new node was created (i.e. just added a new Entry into an existing node). If a new node was created due to the need to split, returns the new node
     */
    private Node put(Node currentNode, Key key, Value val, int height) {
        int j;
        Entry newEntry = new Entry(key, val, null);
        if (height == 0) { //external node
            for (j = 0; j < currentNode.entryCount; j++) {
                if (this.less(key, currentNode.entries[j].key)) {
                    break;
                }
            }
        } else { //internal node
            for (j = 0; j < currentNode.entryCount; j++) { //find index in node entry array to insert the new entry
                if ((j + 1 == currentNode.entryCount) || this.less(key, currentNode.entries[j + 1].key)) {
                    //increment j (j++) after the call so that a new entry created by a split will be inserted in the next slot
                    Node newNode = this.put(currentNode.entries[j++].child, key, val, height - 1);
                    if (newNode == null) {
                        return null;
                    }
                    //if the call to put returned a node, it means I need to add a new entry to the current node
                    newEntry.key = newNode.entries[0].getKey();
                    newEntry.val = null;
                    newEntry.child = newNode;
                    break;
                }
            }
        }
        //shift entries over one place to make room for new entry
        for (int i = currentNode.entryCount; i > j; i--) {
            currentNode.entries[i] = currentNode.entries[i - 1];
        }
        currentNode.entries[j] = newEntry; //add new entry
        currentNode.entryCount++;
        if (currentNode.entryCount < BTreeImpl.MAX) {
            //no structural changes needed in the tree so just return null
            return null;
        } else {
            //will have to create new entry in the parent due to the split, so return the new node,
            //which is the node for which the new entry will be created
            return this.split(currentNode, height);
        }
    }

    /**
     * //by changing currentNode.entryCount, we will treat any value at index higher than the new currentNode.entryCount as if it doesn't exist
     * split node in half
     * @param currentNode
     * @return new node
     */
    private Node split(Node currentNode, int height) {
        Node newNode = new Node(BTreeImpl.MAX / 2);
        currentNode.entryCount = BTreeImpl.MAX / 2;
        for (int j = 0; j < BTreeImpl.MAX / 2; j++) { //copy top half of h into t
            newNode.entries[j] = currentNode.entries[BTreeImpl.MAX / 2 + j];
        }
        if (height == 0) { //external node
            newNode.setNext(currentNode.getNext());
            newNode.setPrevious(currentNode);
            currentNode.setNext(newNode);
        }
        return newNode;
    }

    /**
     * When a document has to be kicked out of memory,
     * instead of it being deleted completely it will be written to disk via a call to BTree.moveToDisk.
     * @param k
     * @throws Exception
     * @throws IllegalArgumentException
     */
    @Override
    public void moveToDisk(Key k) throws Exception {
        if (this.manager == null) {
            throw new IllegalStateException();
        }
        if (k == null) {
            throw new IllegalArgumentException();
        }
        Entry<Key, Value> entry = this.get(this.root, k, this.height);
        Value value = entry.getValue();
        if (entry == null || value == null) {
            throw new IllegalArgumentException();
        }
        this.manager.serialize(k, value);
        entry.setValue(null);
    }

    /**
     * Comparison functions - make Comparable instead of Key to avoid casts
     */
    private static boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private static boolean isEqual(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }

    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty;
     * {@code false} otherwise
     */
    @SuppressWarnings("unused")
    private boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * @return the number of key-value pairs in this symbol table
     */
    @SuppressWarnings("unused")
    private int getSize() {
        return this.size;
    }

    /**
     * @return the height of this B-tree
     */
    @SuppressWarnings("unused")
    private int height() {
        return this.height;
    }

    /**
     * returns a list of all the entries in the Btree, ordered by key
     * @return
     */
    private ArrayList<Entry> getOrderedEntries() {
        Node current = this.leftMostExternalNode;
        ArrayList<Entry> entries = new ArrayList<>();
        while(current != null) {
            for(Entry e : current.getEntries()) {
                if(e.getValue() != null) {
                    entries.add(e);
                }
            }
            current = current.getNext();
        }
        return entries;
    }

    @SuppressWarnings("unused")
    private Entry getMinEntry() {
        Node current = this.leftMostExternalNode;
        while(current != null) {
            for(Entry e : current.getEntries()) {
                if(e.getValue() != null) {
                    return e;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    private Entry getMaxEntry() {
        ArrayList<Entry> entries = this.getOrderedEntries();
        return entries.get(entries.size() - 1);
    }
}
