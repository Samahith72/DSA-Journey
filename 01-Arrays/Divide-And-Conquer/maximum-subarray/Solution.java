class Solution {
    public int maxSubArray(int[] nums) {

        int low = 0;
        int high = nums.length-1;
        int result = maxSubArraySum(nums, low, high);

        return result;
        
    }

    public int maxSubArraySum(int[] arr, int low, int high){
        //Best case if only one element is present
        if(low == high){
            return arr[low];
        }

        // find the middle of the array
        int mid = low + (high - low) / 2;

        // find the left array sum recursively
        int leftMax = maxSubArraySum(arr, low, mid);

        //find the right array sum recursively
        int rightMax = maxSubArraySum(arr, mid+1, high);

        // find the crossing sum starting from the left half and ending at right half
        int crossingMax = crossingSum(arr, low, mid, high);

        return Math.max(Math.max(leftMax, rightMax), crossingMax);
    }


    public int crossingSum(int[] arr, int low, int mid, int high){
        //find the left half sum including the mid
        int sum =0;
        int leftSum = Integer.MIN_VALUE;
        for(int i = mid; i>= low;i--){
            sum += arr[i];
            if(sum > leftSum){
                leftSum = sum;
            }
        }


        // find the right half sum from mid+1 to high
        int sum2 =0;
        int rightSum = Integer.MIN_VALUE;
        for(int i = mid+1; i<= high; i++){
            sum2+= arr[i];
            if(sum2 > rightSum){
                rightSum = sum2;
            }
        }

        // now add the both leftSum and rightSum
        return leftSum + rightSum;
    }
}