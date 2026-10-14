import java.util.HashMap;

class LRUCache {

    class Node{
        int val;
        int key;
        Node next;
        Node prev;

        public Node(int key, int val){
            this.val = val;
            this.key = key;
        }
    }

    private Node head;
    private Node tail;
    private int capacity;
    private HashMap<Integer, Node> map;

    public LRUCache(int capacity) {

        this.capacity = capacity;
        map = new HashMap<>();

        head = new Node(0,0);
        tail = new Node(0,0);

        head.next = tail;
        tail.prev = head;
        
    }
    
    public int get(int key) {
        Node node  = map.get(key);
        if(node == null){
            return -1;
        }

        int value = node.val;
        moveNode(node);

        return value;
        
    }

    private void moveNode(Node node){
        node.next.prev = node.prev;
        node.prev.next = node.next;

        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;


    }
    
    public void put(int key, int value) {

        //case1 if key exists
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = value;
            moveNode(node);
            return;
        }
         Node node  = new Node(key, value);
        //case2 space exists and no key present
        if(map.size() < capacity){
            moveAhead(node);
            map.put(key, node);
        }//case3 no capacity so remove the least recently used
        else{
            int k = tail.prev.key;
            map.remove(k);
            removeTail(tail.prev);
            moveAhead(node);
            map.put(key, node);
        }
        
    }

    private void moveAhead(Node node){   
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeTail(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */