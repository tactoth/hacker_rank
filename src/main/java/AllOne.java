import java.util.HashMap;
import java.util.Map;

public class AllOne {

    private Map<String, Node> map = new HashMap<>();

    private static class Node {
        String key;
        int v = 1;
        Node prev, next;

        public Node(String key, Node prev, Node next) {
            this.key = key;
            this.prev = prev;
            this.next = next;
        }
    }

    Node head, tail;
    private Map<String, Node> nodes = new HashMap<>();

    /**
     * Initialize your data structure here.
     */
    public AllOne() {

    }

    /**
     * Inserts a new key <Key> with value 1. Or increments an existing key by 1.
     */
    public void inc(String key) {
        Node node = map.get(key);
        if (node == null) {
            node = new Node(key, null, null);
            map.put(key, node);

            if (head == null)
                head = tail = node;
            else {
                // put to first
                head.prev = node;
                node.next = head;
                head = node;
            }
        } else {
            node.v++;
        }
    }

    /**
     * Decrements an existing key by 1. If Key's value is 1, remove it from the data structure.
     */
    public void dec(String key) {
        Integer v = map.get(key);
        if (v != null) {
            if (v == 1) {
                map.remove(key);
            } else {
                map.put(key, v - 1);
            }
        }
    }

    /**
     * Returns one of the keys with maximal value.
     */
    public String getMaxKey() {

    }

    /**
     * Returns one of the keys with Minimal value.
     */
    public String getMinKey() {

    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */