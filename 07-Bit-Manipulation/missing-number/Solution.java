class Solution {
    public int missingNumber(int[] nums) {
        /*
        int result = 0;

        for(int i=0; i <= nums.length ; i++){
            result = result ^ i;
        }

        for(int num:  nums){
            result = result ^ num;
        }
        return result;
        */

        int n = nums.length;
        int sum=0;
        for(int num: nums){
            sum+=num;
        }

        int s = (n * (n+1))/2;
        return s - sum;
    }
}