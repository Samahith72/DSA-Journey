
  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
 
class Solution {
    public ListNode deleteMiddle(ListNode head) {

        if(head == null || head.next == null){
            return null;
        }

        int size = 0;
        ListNode current = head;
        while(current != null){
            size++;
            current = current.next;
        }
        int mid = size/2;
        ListNode previous = getNode(mid -1, head);

        previous.next = previous.next.next;
        return head;
        
    }


    private ListNode getNode(int k, ListNode head){
        ListNode current = head;
        for(int i=0;i <k;i++){
            current = current.next;
        }

        return current;
    }
}