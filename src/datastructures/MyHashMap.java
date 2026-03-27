
package datastructures;

public class MyHashMap {

    private static final int CAPACITY = 1000;
    private Node[] buckets;

    // Node class to store key-value pair
    private static class Node {
        String key;
        int value;
        Node next;

        Node(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // Constructor
    public MyHashMap() {
        buckets = new Node[CAPACITY];
    }

    // Hash function
    private int getIndex(String key) {
        return Math.abs(key.hashCode()) % CAPACITY;
    }

    // Add word or update frequency
    public void add(String key) {
        key = key.toLowerCase();
        int index = getIndex(key);
        Node curr = buckets[index];

        while (curr != null) {
            if (curr.key.equals(key)) {
                curr.value++; // increase frequency
                return;
            }
            curr = curr.next;
        }

        // If not found, insert new node
        Node newNode = new Node(key, 1);
        newNode.next = buckets[index];
        buckets[index] = newNode;
    }

    // Get frequency of a word
    public int get(String key) {
        key = key.toLowerCase();
        int index = getIndex(key);
        Node curr = buckets[index];

        while (curr != null) {
            if (curr.key.equals(key)) {
                return curr.value;
            }
            curr = curr.next;
        }

        return 0;
    }

    // Display all key-value pairs
    public void display() {
        for (int i = 0; i < CAPACITY; i++) {
            Node curr = buckets[i];
            while (curr != null) {
                System.out.println(curr.key + " : " + curr.value);
                curr = curr.next;
            }
        }
    }
}
