
  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null){
            return head;
        }
        ListNode current = head.next;
        ListNode prev = head;
        while(current != null){
            if(current.val == prev.val){
                prev.next = prev.next.next;
                current = current.next;
                
            }else{
                prev = prev.next;
                current = current.next;
            }
        }
        return head;
    }
}