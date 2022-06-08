package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.HashTable;
/**
 * Implement array doubling on the array used in your HashTableImpl to support unlimited entries. Don’t forget to re-hash all your entries after doubling the array!
 * Instances of HashTable should be constructed with two type parameters, one for the type of the keys in the table and one for the type of the values
 * @param <Key>
 * @param <Value>
 */
public class HashTableImpl<Key, Value> implements HashTable<Key, Value> {
    private Node<Key,Value>[] table;
    private final double DEFAULT_LOAD_FACTOR = 0.75;
    private int size = 5;
    private int numberOfNodes = 0;

    public HashTableImpl() {
        this.table = new Node[this.size];
    }

    /**
     * @param k the key whose value should be returned
     * @return the value that is stored in the HashTable for k,
     * or null if there is no such key in the table
     */
    public Value get(Key k) {
        if (k == null || this.table[hashFunction(k)] == null) {
            return null;
        }
        Node<Key,Value> evaluate = this.table[hashFunction (k)];
        while (evaluate != null && !evaluate.key.equals(k) && evaluate.nextNode != null) {
            evaluate = evaluate.nextNode;
        }
        if (evaluate != null && evaluate.key.equals(k)) {
            return evaluate.value;
        }
        return null;
    }

    /**
     * Associates the specified value with the specified key in this map
     * @param k the key at which to store the value
     * @param v the value to store.
     * To delete an entry, put a null value.
     * @return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
     */
    public Value put(Key k, Value v) {
        if (k == null) {
            return null;
        }
        if ((this.numberOfNodes / this.size) >= this.DEFAULT_LOAD_FACTOR) {
            this.resize();
        }
        if (v == null) {
            return this.delete(k, this.get(k));
        }
        if (this.table[hashFunction(k)] == null) {
            return this.addIndex(k, v);
        }
        if (this.get(k) == null) {
            return this.add(k, v);
        }
        return this.replace(k, v);
    }

    /**
     *
     */
    private void resize () {
        Node<Key,Value>[] temporaryTable = this.table.clone();
        int oldSize = this.size;
        this.size *= 2;
        this.table = new Node[this.size];
        this.numberOfNodes = 0; //numberOfNodes must be set to 0 because each call to put will add to the count.
        for (int i = 0; i < oldSize; i++) {
            Node<Key,Value> current = temporaryTable[i];
            while (current != null) {
                Key k = current.key;
                Value v = current.value;
                this.put(k, v);
                current = current.nextNode;
            }
        }
    }

    /**
     * Decrement the number of nodes currently in the table
     * @param k the key at which to store the value
     * @return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
     */
    private Value delete(Key k, Value previousValue) {
        if (this.table[hashFunction(k)] == null || this.get(k) == null) {
            return null;
        }
        Node<Key,Value> evaluate = this.table[hashFunction(k)];
        Node<Key,Value> previousEntry = null;
        while (evaluate.nextNode != null && !evaluate.key.equals(k)) {
            previousEntry = evaluate;
            evaluate = evaluate.nextNode;
        }
        if (evaluate.key.equals(k)) { //I'm not sure if this check is even necessary; evaluate != null &&
            if (previousEntry == null) {
                this.table[hashFunction(k)] = evaluate.nextNode;
            } else {
                previousEntry.nextNode = evaluate.nextNode;
            }
        }
        this.numberOfNodes--;
        return previousValue;
    }

    /**
     * Increment the current number of nodes
     * @param k the key at which to store the value
     * @param v the value to store.
     * @return null because the key was not already in the table.
     */
    private Value addIndex(Key k, Value v) {
        this.table[hashFunction(k)] = new Node(k, v);
        this.numberOfNodes++;
        return null;
    }

    /**
     * Increment the current number of nodes
     * @param k the key at which to store the value
     * @param v the value to store.
     * @return null because the key was not already in the table.
     */
    private Value add(Key k, Value v) {
        Node<Key,Value> evaluate = this.table[hashFunction(k)];
        while (evaluate != null && evaluate.nextNode != null) {
            evaluate = evaluate.nextNode;
        }
        evaluate.nextNode = new Node(k, v);
        numberOfNodes++;
        return null;
    }

    /**
     * @param k the key at which to store the value
     * @param v the value to store.
     * @return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
     */
    private Value replace(Key k, Value v) {
        Node<Key,Value> evaluate = this.table[hashFunction(k)];
        while (evaluate != null && !evaluate.key.equals(k)) {
            evaluate = evaluate.nextNode;
        }
        return evaluate.changeValue(v);
    }

    private class Node<Key,Value> {
        final private Key key;
        private Value value;
        private Node<Key,Value> nextNode;
        private Node(Key k, Value v) {
            this.nextNode = null;
            this.key = k;
            this.value = v;
        }

        /**
         * @return
         * @param v
         */
        private Value changeValue(Value v) {
            Value returnValue = this.value;
            this.value = v;
            return returnValue;
        }
    }

    /**
     * @param key the key at which to store the value
     * @return
     * Once the hashCode() method returns an int, we must still
     * distribute it, the hash2 role, into one of the m slots, h,
     * The simplest way to do this is to take the absolute value of the modulus of the hash code divided by the table size, m: k = Math.abs( o.hashCode() % m );
     * Absolute value is necessary so that the number is not negative
     * hash function for keys - returns value between 0 and this.table.length-1
     * maximum value of signed 32 bit int is 2,147,483,647 (which = hexadecimal 0x7FFFFFFF), which
     * is actually 31 ones in a row in binary; the 32nd bit is used as a sign bit.
     * 1) mask off the sign bit, turning a signed 32-bit number into a 31-bit non-negative integer
     * 2) mod with this.table.length to get the index
     */
    private int hashFunction(Key key){
        return ((key.hashCode() & 0x7fffffff) % this.size);
    }
}