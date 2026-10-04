
  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
 
class Solution {
    public ListNode mergeNodes(ListNode head) {

        ListNode current = head.next;
        ListNode result = null;
        ListNode tail = null;
        int sum =0;

        while(current != null){
            if(current.val == 0){
                ListNode newnode = new ListNode(sum);

                if(result == null){
                    result = newnode;
                    tail = newnode;
                }else{
                    tail.next = newnode;
                    tail = newnode;
                }
                sum =0;
            }else{
                sum += current.val;
            }

            current = current.next;
        }
        
        return result;
    }
}