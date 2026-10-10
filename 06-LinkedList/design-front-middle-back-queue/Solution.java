class FrontMiddleBackQueue {

    class Node{
        int val;
        Node next;
        Node prev;
        Node(int val){
            this.val = val;
        }
        Node(int val, Node next, Node prev){
            this.val = val;
            this.next = next;
            this.prev = prev;
        }
    }

    Node head;
    Node tail;
    int size;
    public FrontMiddleBackQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    public Node getNode(int idx){
        Node current = head;
        for(int i=0;i < idx;i++){
            current = current.next;
        }

        return current;
    }
    
    public void pushFront(int val) {
        Node node = new Node(val);

        if(head == null){
            head = tail = node;
        }else{

            node.next = head;
            head.prev = node;
            head = node;  
        }
        size++;
    }
    
    public void pushMiddle(int val) {
        Node node = new Node(val);

        int idx = size /2;
        if(idx == 0){
            pushFront(val);
            return;
        }

        Node current = getNode(idx);
        Node previous = current.prev;

        previous.next = node;
        node.prev = previous;
        node.next = current;
        current.prev = node;
        size++;
    }
    
    public void pushBack(int val) {

        Node node = new Node(val);

        if(head == null){
            head = tail = node;
        }else{

            tail.next = node;
            node.prev = tail;
            tail = node;
        }

        size++;
        
    }
    
    public int popFront() {

        if(head == null){
            return -1;
        }

        int value = head.val;
        head = head.next;
        size--;
        if(head == null){
            tail= null;
        }else{
            head.prev = null;
        }
        
        return value;
        
    }
    
    public int popMiddle() {

        if(head == null){
            return -1;
        }
        
        int idx = (size-1)/2;
        Node current = getNode(idx);
        int value = current.val;

        if(current.prev != null){
            current.prev.next = current.next;
        }else{
            head = current.next;
        }

        if(current.next != null){
            current.next.prev = current.prev;
        }else{
            tail = current.prev;
        }

        size--;
        return value;
    }
    
    public int popBack() {
        if(tail == null){
            return -1;
        }
        int value = tail.val;
        tail = tail.prev;
        if(tail == null){
            head = null;
        }else{
            tail.next = null;
        }
        size--;
        return value;
        
    }
}

/**
 * Your FrontMiddleBackQueue object will be instantiated and called as such:
 * FrontMiddleBackQueue obj = new FrontMiddleBackQueue();
 * obj.pushFront(val);
 * obj.pushMiddle(val);
 * obj.pushBack(val);
 * int param_4 = obj.popFront();
 * int param_5 = obj.popMiddle();
 * int param_6 = obj.popBack();
 */