import java.util.Arrays;

class Solution {
    public int minimumDifference(int[] nums, int k) {
        if(nums.length <= 1){
            return 0;
        }

        Arrays.sort(nums);
        int answer = Integer.MAX_VALUE;

        for(int i=0; i<= nums.length-k;i++){
            int difference = nums[i+k-1] - nums[i];

            answer = Math.min(difference, answer);
        }
        return answer;
    }
}