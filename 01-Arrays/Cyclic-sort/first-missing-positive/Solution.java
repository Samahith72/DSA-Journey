class Solution {
    public int firstMissingPositive(int[] nums) {

        int n = nums.length;
        int i = 0;

        while( i < n){
            int number = nums[i];
            //If number is valid
            if(nums[i] >= 1 && nums[i] <= n){
                //if number is not placed in proper position
                if(nums[i] != nums[nums[i] -1]){
                    int temp = nums[i];
                    nums[i] = nums[temp-1];
                    nums[temp - 1] = temp;
                }
                //if number is already placed in correct position then just move
                else{
                    i++;
                    }
            }
            else{
                i++;
            }
        }


        for(int j = 0; j< n;j++){
            if(nums[j] != j +1){
                return j+1;
            }
        }

        return n+1;
        
    }
}