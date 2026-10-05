class MyLinkedList {

    class Node{
        int val;
        Node next;

        Node(int val){
            this.val = val;
            this.next = null;
        }
    }

    Node head;

    public MyLinkedList() {
        head = null;
    }
    
    public int get(int index) {

        Node current = head;
        int count = 0;
        while(current != null){
            if(count == index){
                return current.val;
            }
            current = current.next;
            count++;
        }

        return -1;
    }
    
    public void addAtHead(int val) {

        Node dummy = new Node(val);
        dummy.next = head;
        head = dummy;
        
    }
    
    public void addAtTail(int val) {
         Node newNode = new Node(val);
         if(head == null){
            head = newNode;
            return;
         }

        Node current = head;
        while(current.next != null){
            current = current.next;
        }
        current.next = newNode;
        
    }
    
    public void addAtIndex(int index, int val) {
        
        if(index == 0){
            addAtHead(val);
            return;
        }

        int count = 0;
        Node current = head;

        while(current != null && count < index-1){
            current = current.next;
            count++;
        }
        if(current == null)
            return;

        Node node = new Node(val);
        node.next = current.next;
        current.next = node;
        
    }
    
    public void deleteAtIndex(int index) {

        if(index == 0){
            head= head.next;
            return;
        }

        int count= 0;
        Node current = head;
        while(current != null && count < index-1){
            current = current.next;
            count++;
        }

        if(current == null || current.next == null){
            return;
        }
        current.next = current.next.next;
        
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */