import java.util.HashMap;

class LFUCache {

    class Node{
        int key;
        int val;
        int freq;
        Node next;
        Node prev;

        public Node(int key, int val){
            this.key = key;
            this.val = val;
            this.freq = 1;
        }
    }

    class DoublyLinkedList{
        Node head ;
        Node tail ;
        int size;

        public DoublyLinkedList(){
            head = new Node(0,0);
            tail = new Node(0,0);

            head.next = tail;
            tail.prev = head;
            size =0;
        }

        public void addFirst(Node node){
            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;

            size++;
        }
        
        public Node removeLast(){
            if(size == 0){
                return null;
            }

            Node node = tail.prev;
            remove(node);
            return node;
        }

        public void remove(Node node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

    }

    private int capacity;
    private HashMap<Integer, Node> nodeMap;
    private HashMap<Integer, DoublyLinkedList> freqMap;
    private int minFreq;

    public LFUCache(int capacity) {

        this.capacity = capacity;
        this.minFreq = 0;

        nodeMap = new HashMap<>();
        freqMap = new HashMap<>();
    }

    
    public int get(int key) {

        Node node = nodeMap.get(key);
        if(node == null)
            return -1;
        
        int value = node.val;
        updateFrequency(node);
        return value;
        
    }

    private void updateFrequency(Node node){
        int oldFreq = node.freq;
        DoublyLinkedList oldList = freqMap.get(oldFreq);
        oldList.remove(node);

        if(oldList.size == 0 && minFreq == oldFreq){
            minFreq++;
        }
        node.freq++;

        freqMap.putIfAbsent(node.freq, new DoublyLinkedList());
        freqMap.get(node.freq).addFirst(node);
    }
    
    public void put(int key, int value) {
        //case1:- if key exists
        if(nodeMap.containsKey(key)){
            Node node = nodeMap.get(key);
            node.val = value;
            updateFrequency(node);
            return;
        }

        
        Node node = new Node(key, value);
        //case2:- new key, space available
        if(nodeMap.size() < capacity){
            newKey(node);
        } //case3:- new key, no space
        else{
            Node removed = freqMap.get(minFreq).removeLast();
            int removedKey = removed.key;
            nodeMap.remove(removedKey);
            newKey(node);
        }
    }

    private void newKey(Node node){
        nodeMap.put(node.key, node);
        freqMap.putIfAbsent(1, new DoublyLinkedList());
        freqMap.get(1).addFirst(node);
        minFreq = 1;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */