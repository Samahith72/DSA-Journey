import java.util.HashSet;

class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }
  }
 
public class Solution {
    public ListNode detectCycle(ListNode head) {

        if(head == null || head.next == null){
            return null;
        }

        ListNode current = head;
        HashSet< ListNode > set = new HashSet<>();

        while(current != null && current.next != null){
            if(set.contains(current)){
                return current;
            }else{
                set.add(current);
                current = current.next;
            }
        }
        
        return null;
        
    }
}