import java.util.HashMap;
import java.util.HashSet;

class AllOne {

    class Node{
        int freq;
        HashSet<String> set;
        Node next;
        Node prev;
        
        public Node(int freq){
            this.freq = freq;
            this.set = new HashSet<>();
        }
        
    }

    private HashMap<String, Node> map;
    private Node head;
    private Node tail;

    public AllOne() {

        map = new HashMap<>();

        head = new Node(0);
        tail = new Node(0);

        head.next = tail;
        tail.prev = head;
        
    }

    private void addFirst(Node node){
        head.next.prev = node;
        node.next = head.next;

        head.next = node;
        node.prev = head;
        
    }
    
    public void inc(String key) {

        //case1:- New key
        if(!map.containsKey(key)){
            Node firstNode = head.next;
            Node node;
            if(firstNode  == tail  || firstNode.freq > 1){
                node = new Node(1);
                addFirst(node);
            }else{
                node = firstNode;
            }

            node.set.add(key);
            map.put(key, node);
            return;
        }//case2:- if key already exists and increase its count 
        Node current = map.get(key);
        int newFreq = current.freq + 1;
        Node target;
        if(current.next != tail && current.next.freq == newFreq){
           target = current.next;
        }else{
            target = new Node(newFreq);
            addAfter(current, target);
        }

        current.set.remove(key);
        target.set.add(key);
        map.put(key, target);

        if(current.set.isEmpty()){
            removeNode(current);
        }
        
    }

    private void addAfter(Node prevNode, Node node){
        prevNode.next.prev = node;
        node.next = prevNode.next;

        prevNode.next = node;
        node.prev = prevNode;
    }

    private void removeNode(Node node){
        node.prev.next= node.next;
        node.next.prev = node.prev;
    }
    
    public void dec(String key) {

        Node node = map.get(key);
        if(node == null)
            return;
        Node target;
        int newFreq = node.freq-1;
        if(newFreq < 1){
            node.set.remove(key);
            map.remove(key);
            if(node.set.isEmpty()){
                removeNode(node);
            }
            return;
        }
        if(node.prev.freq == newFreq){
            target = node.prev;
        }else{
            target = new Node(newFreq);
            addBefore(node, target);
        }
        node.set.remove(key);
        target.set.add(key);
        map.put(key, target);
        
        if(node.set.isEmpty()){
            removeNode(node);
        }
    }

    private void addBefore(Node current, Node target){
        current.prev.next = target;
        target.prev = current.prev;

        current.prev = target;
        target.next = current;
    }
    
    public String getMaxKey() {
        if(tail.prev != head){
            return tail.prev.set.iterator().next();
        }else{
            return "";
        }
        
    }
    
    public String getMinKey() {
        if(head.next == tail)
            return "";
        
        return head.next.set.iterator().next(); 
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