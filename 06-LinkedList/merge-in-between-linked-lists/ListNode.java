
  public class ListNode {
     int val;
     ListNode next;
     ListNode() {}
    ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
 
class Solution {
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {

        ListNode currentl1 = list1;
        ListNode taill2 = list2;

        for(int i=0;i < a-1;i++){
            currentl1 = currentl1.next;
        }

        while(taill2.next != null){
            taill2 = taill2.next;
        }

        ListNode l1 = getNode(b, list1);

        currentl1.next = list2;
        taill2.next = l1.next;
        return list1;

        
    }

    private ListNode getNode(int k, ListNode head){
        for(int i=0;i <k;i++){
            head = head.next;
        }
        return head;
    }
}