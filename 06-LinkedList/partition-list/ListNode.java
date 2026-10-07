
  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
 
class Solution {
    public ListNode partition(ListNode head, int x) {
        ListNode small = new ListNode(-1) ;
        ListNode large = new ListNode(-1);
        ListNode smallTail = small;
        ListNode largeTail = large;
        ListNode current = head;
        ListNode smallHead = small;
        ListNode largeHead = large;

        while(current != null){
            int number = current.val;
            if( number < x){
                ListNode node = new ListNode(number);
                smallTail.next = node;
                smallTail = smallTail.next;
            }else{
                ListNode node = new ListNode(number);
                
                largeTail.next = node;
                largeTail = largeTail.next;
            }

            current = current.next;
        }
        
        smallTail.next = largeHead.next;
        largeTail.next = null;
        largeHead.next = null;
        smallHead = smallHead.next;

        return smallHead;
    }
}