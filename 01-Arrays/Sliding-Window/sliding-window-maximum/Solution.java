import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] answer = new int[nums.length-k+1];
        Deque<Integer> dq = new ArrayDeque<>();
        int index = 0;

        for(int right=0; right <nums.length;right++){
            while(!dq.isEmpty() && dq.peekFirst() <= right-k){
                dq.pollFirst();
            }

            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[right]) {
                dq.pollLast();
            }

            dq.offerLast(right);

            if(right >= k-1){
                answer[index] = nums[dq.peekFirst()];
                index++;
            }
        }

        return answer;
        
    }
}