package datastructures;

public class TestHashSet {
    public static void main(String[] args) {
        MyHashSet set = new MyHashSet();

        set.add("apple");
        set.add("dog");
        set.add("apple"); // duplicate-

        System.out.println("Contains apple: " + set.contains("apple"));
        System.out.println("Contains cat: " + set.contains("cat"));

        set.remove("dog");
        System.out.println("Contains dog after removal: " + set.contains("dog"));
    }
}