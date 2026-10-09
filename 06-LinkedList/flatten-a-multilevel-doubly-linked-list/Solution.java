
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};


class Solution {
    public Node flatten(Node head) {
        if(head == null )
            return null;

        DFS(head);
        return head;
    }

    private Node DFS(Node current){
        Node tail =current;

        while(current != null){
            Node nextNode = current.next;

            //if no child
            if(current.child == null){
                tail = current;
            }
            // if there is child
            else{
                Node childHead = current.child;
                Node childTail = DFS(childHead);

                //connect the current to flatten child
                current.next = childHead;
                childHead.prev = current;
                current.child = null;

                //connect childtail to original list 
                if(nextNode != null){
                    childTail.next = nextNode;
                    nextNode.prev = childTail;
                }

                tail = childTail;
            }

            current = nextNode;
        }

        return tail;
    }
}