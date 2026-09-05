class Solution {
    public int[] productExceptSelf(int[] nums) {

        int[] ans = new int[nums.length];
        int leftProduct = 1;
        for(int i = 0; i < nums.length; i++){
            if(i == 0){
                ans[i] = leftProduct;
                leftProduct *= nums[0];
                continue;
            }

            ans[i] = leftProduct;
            leftProduct *= nums[i];
        }

        int rightProduct =1;
        for(int i=nums.length-1;i >=0; i--){
            if(i == nums.length-1){
                ans[i] = ans[i] * rightProduct;
                rightProduct *= nums[i];
                continue;
            }

            ans[i] = ans[i] * rightProduct;
            rightProduct *= nums[i];
        }

        return ans;
        
    }
}