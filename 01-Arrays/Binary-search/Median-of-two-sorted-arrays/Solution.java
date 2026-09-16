class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        if(nums1.length > nums2.length){
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;

        int low = 0;
        int high = m;

        while(low <= high){
            int cut1 = (low + high)/2;
            int cut2 = (m+n+1) / 2 - cut1;

            double left1 = (cut1 == 0)? Integer.MIN_VALUE : nums1[(cut1 -1)];
            double left2 = (cut2 == 0) ? Integer.MIN_VALUE : nums2[(cut2-1)];
            double right1 = (cut1 == m) ? Integer.MAX_VALUE : nums1[cut1];
            double right2 = (cut2 == n) ? Integer.MAX_VALUE : nums2[cut2];

            if(left1 <= right2 && left2 <= right1){
                if((m+n)%2 == 1){
                    return Math.max(left1, left2);
                }

                double leftMax = Math.max(left1, left2);
                double rightMin = Math.min(right1, right2);

                return(leftMax + rightMin) / 2.0;
            }

            if(left1 > right2){
                high = cut1 -1;
            }else{
                low = cut1 +1;
            }


        }

        return 0.0;
        
    }
}