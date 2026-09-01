class Solution {
    public int minimumDeletions(int[] nums) {

        if(nums.length == 1){
            return 1;
        }
        if(nums.length == 2){
            return 2;
        }
        int minValue= Integer.MAX_VALUE;
        int maxValue =Integer.MIN_VALUE;

        int minIndex = 0;
        int maxIndex = 0;

        for(int i = 0;i< nums.length;i++){
            if(nums[i] < minValue){
                minIndex =i;
                minValue = nums[i];
            }
            if(nums[i] > maxValue){
                maxIndex = i;
                maxValue = nums[i];
            }
        }
        
        int scenario1 = minimumDelete1(nums, minIndex, maxIndex);
        int scenario2 = minimumDelete2(nums, minIndex, maxIndex);
        int scenario3 = minimumDelete3(nums, minIndex, maxIndex);

        return Math.min(Math.min(scenario1, scenario2), scenario3);
    }

    public int minimumDelete1(int[] arr, int min, int max){
        int j =0;
        int count = 0;
        while(j <= min || j <= max){
            count ++;
            j++;
        }
        return count;
    }

    public int minimumDelete2(int[] arr, int min, int max){
        int j = arr.length -1;
        int count = 0;
        while(j >= min || j >= max){
            count++;
            j--;
        }
        return count;
    }

    public int minimumDelete3(int[] arr, int min, int max){
        int count =0;
        int i = 0;
        int j = arr.length-1;

        if(min < max){
            while(i <= min){
                count++;
                i++;
            }
            while(j >= max){
                count++;
                j--;
            }
        }else{
            while(i <= max){
                count++;
                i++;
            }
            while(j >= min){
                count++;
                j--;
            }
        }

        return count;
    }
}