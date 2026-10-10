import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ListNode {
     int val;
     ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
 
class Solution {
    public int[] nextLargerNodes(ListNode head) {

        List<Integer> value = new ArrayList<>();

        while(head != null){
            value.add(head.val);
            head = head.next;
        }

        int n = value.size();
        int[] answer = new int[n];
        Stack<Integer> s = new Stack<>();

        for(int i =0;i < n;i++){

            while(!s.isEmpty() && value.get(i) > value.get(s.peek())){
                int idx = s.pop();
                answer[idx] = value.get(i);
            }

            s.push(i);

        }
        return answer;
    }
}