package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.HashTable;
/**
 * Instances of HashTable should be constructed with two type parameters, one for the type of the keys in the table and one for the type of the values
 */
public class HashTableImpl<Key, Value> implements HashTable<Key, Value> { 
    private class entry {
        entry nextEntry;
        Key key;
        Value value;
        /**
         * @param k
         * @param v
         */
        private entry(Key k, Value v) {
            this.nextEntry = null;
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
        
        private void setNext(entry next) { 
            this.nextEntry = next;
        }

        /**
         * @return
         */         
        private Key getKey() {
            return this.key;
        }

        /**
         * @return
         */        
        private Value getValue() {
            return this.value; //(Value)e.value
        }    

        /**
         * @return
         */         
        private entry getNext() {
            return this.nextEntry;
        }
    }

    private final int size = 5; 
    private Object[] table = new Object[this.size];

    /**
     * @param k the key whose value should be returned
     * @return the value that is stored in the HashTable for k, 
     * or null if there is no such key in the table      
     */
    public Value get(Key k) {
        if (k == null || this.table[hashFunction(k)] == null) { //Should I be checking if the index isEmpty()?
            return null;
        }
        entry evaluate = (entry)this.table[hashFunction (k)]; 
        while (evaluate != null && !evaluate.getKey().equals(k) && evaluate.getNext() != null) { 
            evaluate = evaluate.getNext();
        } 
        if (evaluate != null && evaluate.getKey().equals(k)) { 
            return evaluate.getValue();
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
        if (v == null) {
            return this.delete(k, v, this.get(k)); 
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
     * @param k the key at which to store the value
     * @param v the value to store.
     * @return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
     */ 
    private Value delete(Key k, Value v, Value previousValue) { 
        if (this.table[hashFunction(k)] == null || this.get(k) == null) { 
            return null;
        }
        entry evaluate = (entry)this.table[hashFunction(k)];
        entry previousEntry = null; 
        while (evaluate.getNext() != null && !evaluate.getKey().equals(k)) { 
            previousEntry = evaluate;
            evaluate = evaluate.getNext();        
        }
        if (evaluate.getKey().equals(k)) { //I'm not sure if this check is even necessary; evaluate != null && 
             if (previousEntry == null) {
                this.table[hashFunction(k)] = evaluate.getNext();
            } else {
                previousEntry.setNext(evaluate.getNext()); 
            }
        }
        return previousValue; 
    }

    /**
     * @param k the key at which to store the value
     * @param v the value to store.
     * @return null because the key was not already in the table.
     */ 
    private Value addIndex(Key k, Value v) {
        this.table[hashFunction(k)] = new entry(k, v);
        return null;
    }

    /**
     * @param k the key at which to store the value
     * @param v the value to store.
     * @return null because the key was not already in the table.
     */     
    private Value add(Key k, Value v) {
        entry evaluate = (entry)this.table[hashFunction(k)]; 
        while (evaluate != null && evaluate.getNext() != null) { 
            evaluate = evaluate.getNext();        
        }
        evaluate.setNext(new entry(k, v)); 
        return null;
    }

    /**
     * @param k the key at which to store the value
     * @param v the value to store.
     * @return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
     */ 
    private Value replace(Key k, Value v) {
        entry evaluate = (entry)this.table[hashFunction(k)];
        while (evaluate != null && !evaluate.getKey().equals(k)) { 
            evaluate = evaluate.getNext();        
        }
        return evaluate.changeValue(v);
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
