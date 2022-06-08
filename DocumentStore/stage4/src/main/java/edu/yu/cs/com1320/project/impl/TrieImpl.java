package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.Trie;
import java.util.*;
public class TrieImpl<Value> implements Trie<Value> {
    private static final int alphabetSize = 36;
    private Node<Value> root;
    private Set<Value> returnSet;
    private Value returnValue;
    public TrieImpl() {
        this.root = new Node<>();
        this.returnSet = new HashSet<>();
        this.returnValue = null;
    }

    private class Node<Value> {
        private Set<Value> values;
        private Node<Value>[] links;
        private Node() {
            this.values = new HashSet<>();
            this.links = new Node[alphabetSize];
        }
    }

    /**
     * @param key
     * @param value
     * @throws IllegalArgumentException if key or value is null
     */
    @Override
    public void put(String key, Value value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (key == "") { //length() == 0 key.toASCIIString().isBlank()
            return;
        }
        this.root = put(this.root, key, value, 0);
    }

    /**
     * @param node
     * @param key
     * @param val
     * @param d
     * @return
     */
    private Node<Value> put(Node<Value> node, String key, Value val, int d) {
        if (node == null) { //create a new node
            node = new Node<>();
        }
        if (d != key.length()) {
            int n = this.converter(key, d);
            node.links[n] = this.put(node.links[n], key, val, d + 1); //proceed to the next node in the chain of nodes that forms the desired key
            return node;
        }
        node.values.add(val); //we've reached the last node in the key, set the value for the key and return the node
        return node;
    }

    /**
     * Remove the given value from the node of the given key (do not remove the value from other nodes in the Trie)
     * @param key
     * @param value
     * @return the value which was deleted. If the key did not contain the given value, return null.
     * @throws IllegalArgumentException if key is null
     */
    @Override
    public Value delete(String key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        this.returnValue = null;
        if (key == "") {
            return null;
        }
        this.returnSet = new HashSet<>();
        this.delete(this.root, key, value, 0);
        return returnValue;
    }

    /**
     * @param node
     * @param key
     * @param value
     * @param d
     * @return
     */
    private Node<Value> delete(Node<Value> node, String key, Value value, int d) { //2x that you need the return set
        if (node == null) {
            return null;
        }
        if (d == key.length() && node.values.contains(value)) { //we've reached the last node in the key, set the value for the key & return the node
            this.returnValue = value; //this.returnSet.add(value);
            node.values.remove(value);
        }
        if (d < key.length()) {
            int n = this.converter(key, d);
            node.links[n] = this.delete(node.links[n], key, value, d + 1); //proceed to the next node in the chain of nodes that forms the desired key
        }
        return this.deleteValues(node);
    }

    private Node<Value> deleteValues(Node<Value> node) {
        if (node.values != null && !node.values.isEmpty()) { //this node has a val – do nothing, return the node
            return node;
        }
        for (int i = 0; i < this.alphabetSize; i++) { //otherwise, check if subtrie rooted at x is completely empty
            if (node.links[i] != null){
                return node; //not empty
            }
        }
        return null; //empty - set this link to null in the parent
    }

    /**
     * @param key
     * @param comparator used to sort values
     * @return a List of matching Values, in descending order
     * @throws IllegalArgumentException if key is null or if comparator is null
     */
    @Override
    public List<Value> getAllSorted(String key, Comparator<Value> comparator) {
        if (key == null || comparator == null) { //2x check - key.contains(" ")
            throw new IllegalArgumentException();
        }
        List<Value> returnList = new ArrayList<>();
        if (key == "") {
            return returnList; //key.length() == 0
        }
        returnList = getAllSorted(this.root, key, 0); //returnList.sort(comparator); Collections.reverse(returnList);
        Collections.sort(returnList, comparator);
        return returnList;
    }

    /**
     * @param node
     * @param key
     * @param d
     * @return
     */
    private List<Value> getAllSorted(Node<Value> node, String key, int d) {
        List<Value>returnList = new ArrayList<>();
        if (d == key.length()) {
            returnList.addAll(node.values);
            return returnList;
        }
        int n = this.converter(key, d);
        if (node.links[n] == null) {
            return returnList;
        }
        return getAllSorted(node.links[n], key, d + 1); //Haven't arrived at the right Node yet, keep moving foward
    }

    /**
     * @param prefix
     * @param comparator used to sort values
     * @return a List of all matching Values containing the given prefix, in descending order
     * @throws IllegalArgumentException if prefix is null or if comparator is null
     */
    @Override
    public List<Value> getAllWithPrefixSorted(String prefix, Comparator<Value> comparator) {
        if (prefix == null || comparator == null) {
            throw new IllegalArgumentException();
        }
        List<Value> returnList = new ArrayList<>();
        if (prefix == "") {
            return returnList;
        }
        this.returnSet = new HashSet<>();
        returnList = new ArrayList<>(getAllWithPrefixSorted(this.root, prefix, 0)); //Collections.reverse(returnList);
        Collections.sort(returnList, comparator);
        return returnList;
    }

    /**
     * @param node
     * @param prefix
     * @param d
     * @return
     */
    private Set<Value> getAllWithPrefixSorted(Node<Value> node, String prefix, int d) {
        if (d == prefix.length()) {
            this.returnSet.addAll(node.values);
            for (int i = 0; i < node.links.length; i++) {
                if (node.links[i] != null) {
                    this.getAllWithPrefixSorted(node.links[i], prefix, d);
                }
            }
        }
        if (d < prefix.length()) {
            int n = this.converter(prefix, d);
            if (node.links[n] != null) { //proceed to the next node in the chain of nodes that forms the desired key
                this.getAllWithPrefixSorted(node.links[n], prefix, d + 1);
            }
        }
        return this.returnSet;
    }

    /**
     * Delete the subtree rooted at the last character of the prefix.
     * @param prefix
     * @return a Set of all Values that were deleted.
     * @throws IllegalArgumentException if prefix is null
     */
    @Override
    public Set<Value> deleteAllWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
        this.returnSet = new HashSet<>();
        if (prefix == "") {
            return this.returnSet;
        }
        deleteAllWithPrefix(this.root, prefix, 0);
        return this.returnSet;
    }

    private Node<Value> deleteAllWithPrefix(Node<Value> node, String prefix, int d) {
        if (node == null) {
            return null;
        }
        if (d == prefix.length()) {
            this.deletePrefix(node, prefix, d);
        }
        if (d < prefix.length()) { //proceed to the next node in the chain of nodes that forms the desired key
            int n = this.converter(prefix, d);
            node.links[n] = this.deleteAllWithPrefix(node.links[n], prefix, d + 1);
        }
        return deleteValues(node);
    }

    private void deletePrefix(Node<Value> node, String prefix, int d) {
        if (node.values != null & !node.values.isEmpty()) {
            returnSet.addAll(node.values);
            node.values = new HashSet<>();
        }
        for (int i = 0; i < this.alphabetSize; i++) {
            if (node.links[i] != null) {
                node.links[i] = this.deleteAllWithPrefix(node.links[i], prefix, d);
            }
        }
    }

    /**
     * Delete all values from the node of the given key (do not remove the values from other nodes in the Trie)
     * @param key
     * @return a Set of all Values that were deleted.
     * @throws IllegalArgumentException if key is null
     */
    @Override
    public Set<Value> deleteAll(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        this.returnSet = new HashSet<>();
        if (key == "") {
            return this.returnSet;
        }
        deleteAll(this.root, key, 0);
        return this.returnSet;
    }

    /**
     * @param node
     * @param key
     * @param d
     * @return
     */
    private Node<Value> deleteAll(Node<Value> node, String key, int d) {
        if (node == null) {
            return null;
        }
        if (d == key.length()) { //we've reached the last node in the key, set the value for the key & return the node
            this.returnSet.addAll(node.values);
            node.values = new HashSet<>(); //we're at the node to del - set the val to null
        }
        if (d < key.length()) { //proceed to the next node in the chain of nodes that forms the desired key
            int n = this.converter(key, d);
            node.links[n] = this.deleteAll(node.links[n], key, d + 1); //return node;
        }
        return this.deleteValues(node); //key, returnSet, d
    }

    private int converter(String key, int d) {
        return Character.getNumericValue(key.charAt(d));
    }
}