
 public class ListNode {
     int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
 
class Solution {
    public ListNode[] splitListToParts(ListNode head, int k) {

        

        int n = 0;
        ListNode current = head;

        while(current != null){
            n++;
            current = current.next;
        }
        

        int baseSize = n/k;
        int extra = n%k;
        current = head;

        ListNode[] parts = new ListNode[k];


        for(int i=0;i < k;i++){
            parts[i] = current;
            int partSize = baseSize;

            if(i < extra){
                partSize++;
            }

            for(int j = 1; j< partSize;j++){
                current = current.next;
            }

            if(current != null){
                ListNode nextNode = current.next;
                current.next = null;
                current = nextNode;
            }
        } 

        return parts;
    }
}