import java.util.PriorityQueue;

public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

        for(ListNode node : lists){
            if(node != null){
                minHeap.offer(node);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while(!minHeap.isEmpty()){
            ListNode current = minHeap.poll();
            tail.next = current;
            tail = current;
            if(current.next != null){
                minHeap.offer(current.next);
            }
        }

        return dummy.next;
    }
}