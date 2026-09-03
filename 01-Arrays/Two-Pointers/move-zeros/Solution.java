class Solution {
    public void moveZeroes(int[] nums) {

        if(nums.length == 1 && nums[0] == 0){
            return;
        }

        int zero = 0;
        int i = 0;
        int j = 0;

         while(j < nums.length){
            if(nums[j] != 0){
                nums[i] = nums[j];
                j++;
                i++;
            }else{
                j++;
            }
         }

         while(i < nums.length){
            nums[i] = 0;
            i++;
         }
        
    }
}