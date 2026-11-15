class Solution {
    public int totalHammingDistance(int[] nums) {
        int total = 0;

        for(int bit=0; bit <= 31;bit++){
            int ones = 0;

            for(int num: nums){
               ones += (num >> bit) & 1;
            }
            int zeros = nums.length -ones;
            total += zeros * ones;
        }

        return total;
    }
}