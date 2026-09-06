class Solution {
    public int minSubArrayLen(int target, int[] nums) {

        int left = 0;
        int right = 0;
        int sum =0;
        int size = Integer.MAX_VALUE;
        boolean isFound = false;

        while(right < nums.length){
            sum += nums[right];

            while(sum >= target){
                int currentLength = right - left +1;
                size = Math.min(size, currentLength);
                sum = sum - nums[left];
                left++;
                isFound = true;
            }

            right++;
        }

        if(isFound){
            return size;
        }

        return 0;
        
    }
}