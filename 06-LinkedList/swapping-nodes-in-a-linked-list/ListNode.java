
  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }
 
class Solution {
    public ListNode swapNodes(ListNode head, int k) {

        ListNode fast = head;
        ListNode second = head;
        ListNode first = head;

        for(int i=0;i < k-1;i++){
            fast= fast.next;
            first = first.next;
        }

        while(fast.next != null){
            fast = fast.next;
            second = second.next;
        }


        int temp = second.val;
        second.val = first.val;
        first.val = temp;


        return head;
    }
}