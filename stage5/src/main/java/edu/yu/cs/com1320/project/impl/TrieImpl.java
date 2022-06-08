package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.Trie;
import java.util.*;
/**
 * @param <Value>
 */
public class TrieImpl<Value> implements Trie<Value> {
    private static final int alphabetSize = 36;
    private Node<Value> root;
    private Value returnVal;

    public TrieImpl() {
        this.root = new Node<>();
        this.returnVal = null;
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
     * if put in trieimpl is called with a null value, it should call deleteAll (check TooSimpleTrie)
     * Add the given value at the given key
     * @param key
     * @param value
     * @throws IllegalArgumentException if key or value is null
     */
    @Override
    public void put(String key, Value value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (key != "") {
            this.root = put(this.root, key, value, 0);
        }
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
        if (d == key.length()) { //we've reached the last node in the key, set the value for the key and return the node
            node.values.add(val);
        } else { //haven't arrived, proceed to the next node in the chain of nodes that forms the desired key
            int n = this.converter(key, d);
            node.links[n] = this.put(node.links[n], key, val, d + 1);
        }
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
        //Value returnValue = null;
        this.returnVal = null;
        if (key != "") { //If the key is empty, then return an empty value
            //this.delete(this.root, key, value, 0, returnValue);
            this.delete(this.root, key, value, 0);
        }
        //return returnValue;
        return this.returnVal;
    }

    /**
     * @param node
     * @param key
     * @param value
     * @param d
     * @return
     */
    private Node<Value> delete(Node<Value> node, String key, Value value, int d) {
        if (node == null) {
            return null;
        }
        if (d < key.length()) {
            int n = this.converter(key, d);
            node.links[n] = this.delete(node.links[n], key, value, d + 1); //proceed to the next node in the chain of nodes that forms the desired key
        }
        if (d == key.length() && node.values.contains(value)) { //we've reached the last node in the key, set the value for the key & return the node
            this.returnVal = value;
            node.values.remove(value);
        }
        return this.deleteValues(node);
    }

    /**
     * @param node
     * @param key
     * @param value
     * @param d
     * @return
     */
    private Node<Value> delete(Node<Value> node, String key, Value value, int d, Value returnValue) {
        if (node == null) {
            return null;
        }
        if (d < key.length()) {
            int n = this.converter(key, d);
            node.links[n] = this.delete(node.links[n], key, value, d + 1, returnValue); //proceed to the next node in the chain of nodes that forms the desired key
        }
        if (d == key.length() && node.values.contains(value)) { //we've reached the last node in the key, set the value for the key & return the node
            returnValue = value;
            node.values.remove(value);
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
     * get all exact matches for the given key, sorted in descending order.
     * Search is CASE INSENSITIVE.
     * @param key
     * @param comparator used to sort values
     * @return a List of matching Values, in descending order
     * Any search method in TrieImpl or DocumentStoreImpl that returns a collection must return an empty collection, not null, if there are no matches.
     * @throws IllegalArgumentException if key is null or if comparator is null
     */
    @Override
    public List<Value> getAllSorted(String key, Comparator<Value> comparator) {
        if (key == null || comparator == null) {
            throw new IllegalArgumentException();
        }
        List<Value> returnList = new ArrayList<>();
        if (key != "") {
            returnList = getAllSorted(this.root, key, 0);
            Collections.sort(returnList, comparator);
        }
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
        if (node.links[n] == null) { //If the node is null that means we've arrived at the end of the word
            return returnList;
        }
        return getAllSorted(node.links[n], key, d + 1); //proceed to the next node in the chain of nodes that forms the desired key
    }

    /**
     * get all matches which contain a String with the given prefix, sorted in descending order.
     * For example, if the key is "Too", you would return any value that contains "Tool", "Too", "Tooth", "Toodle", etc.
     * Search is CASE INSENSITIVE.
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
        if (prefix != "") {
            Set<Value> set = getAllWithPrefixSorted(this.root, prefix, 0, new HashSet<>());
            returnList = new ArrayList<>(set);
            Collections.sort(returnList, comparator);
        }
        return returnList;
    }

    /**
     * @param node
     * @param prefix
     * @param d
     * @return
     */
    private Set<Value> getAllWithPrefixSorted(Node<Value> node, String prefix, int d, Set<Value> returnSet) {
        if (d < prefix.length()) {
            int n = this.converter(prefix, d);
            if (node.links[n] != null) { //proceed to the next node in the chain of nodes that forms the desired key
                this.getAllWithPrefixSorted(node.links[n], prefix, d + 1, returnSet);
            }
        }
        if (d == prefix.length()) {
            returnSet.addAll(node.values);
            for (int i = 0; i < node.links.length; i++) {
                if (node.links[i] != null) {
                    this.getAllWithPrefixSorted(node.links[i], prefix, d, returnSet);
                }
            }
        }
        return returnSet;
    }

    /**
     * Delete the subtree rooted at the last character of the prefix.
     * Search is CASE INSENSITIVE.
     * @param prefix
     * @return a Set of all Values that were deleted.
     * @throws IllegalArgumentException if prefix is null
     */
    @Override
    public Set<Value> deleteAllWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
        Set<Value> returnSet = new HashSet<>();
        if (prefix != "") {
            deleteAllWithPrefix(this.root, prefix, 0, returnSet);
        }
        return returnSet;
    }

    private Node<Value> deleteAllWithPrefix(Node<Value> node, String prefix, int d, Set<Value> returnSet) {
        if (node == null) {
            return null;
        }
        if (d < prefix.length()) { //proceed to the next node in the chain of nodes that forms the desired key
            int n = this.converter(prefix, d);
            node.links[n] = this.deleteAllWithPrefix(node.links[n], prefix, d + 1, returnSet);
        }
        if (d == prefix.length()) {
            this.deletePrefix(node, prefix, d, returnSet);
        }
        return deleteValues(node);
    }

    private void deletePrefix(Node<Value> node, String prefix, int d, Set<Value> returnSet) {
        if (node.values != null & !node.values.isEmpty()) {
            returnSet.addAll(node.values);
            node.values = new HashSet<>();
        }
        for (int i = 0; i < this.alphabetSize; i++) {
            if (node.links[i] != null) {
                node.links[i] = this.deleteAllWithPrefix(node.links[i], prefix, d, returnSet);
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
        Set<Value> returnSet = new HashSet<>();
        if (key != "") {
            deleteAll(this.root, key, 0, returnSet);
        }
        return returnSet;
    }

    /**
     * @param node
     * @param key
     * @param d
     * @return
     */
    private Node<Value> deleteAll(Node<Value> node, String key, int d, Set<Value> returnSet) {
        if (node == null) {
            return null;
        }
        if (d < key.length()) { //proceed to the next node in the chain of nodes that forms the desired key
            int n = this.converter(key, d);
            node.links[n] = this.deleteAll(node.links[n], key, d + 1, returnSet); //return node;
        }
        if (d == key.length()) { //we've reached the last node in the key, set the value for the key & return the node
            returnSet.addAll(node.values);
            node.values = new HashSet<>(); //we're at the node to del - set the val to null
        }
        return this.deleteValues(node); //key, returnSet, d
    }

    /**
     * @param k
     * @param d
     * @return
     */
    private int converter(String k, int d) {
        return Character.getNumericValue(k.charAt(d));
    }
}