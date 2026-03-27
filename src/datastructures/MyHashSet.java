package datastructures;

public class MyHashSet {

    private static final int CAPACITY = 1000;
    private Node[] buckets;

    private static class Node {
        String key;
        Node next;

        Node(String key) {
            this.key = key;
        }
    }

    public MyHashSet() {
        buckets = new Node[CAPACITY];
    }

    private int getIndex(String key) {
        return Math.abs(key.hashCode()) % CAPACITY;
    }

    public void add(String key) {
        int index = getIndex(key);
        Node curr = buckets[index];

        while (curr != null) {
            if (curr.key.equals(key)) return;
            curr = curr.next;
        }

        Node newNode = new Node(key);
        newNode.next = buckets[index];
        buckets[index] = newNode;
    }

    public boolean contains(String key) {
        int index = getIndex(key);
        Node curr = buckets[index];

        while (curr != null) {
            if (curr.key.equals(key)) return true;
            curr = curr.next;
        }

        return false;
    }

    public void remove(String key) {
        int index = getIndex(key);
        Node curr = buckets[index];
        Node prev = null;

        while (curr != null) {
            if (curr.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                return;
            }
            prev = curr;
            curr = curr.next;
        }
    }
}