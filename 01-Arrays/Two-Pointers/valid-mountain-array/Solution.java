class Solution {
    public boolean validMountainArray(int[] arr) {
        if(arr.length <3){
            return false;
        }

        int ptr1 = 0;
        int ptr2 = 1;
        int peak =0;

        while(ptr2 < arr.length){
            if(arr[ptr1] == arr[ptr2]){
                return false;
            }else if(arr[ptr1] < arr[ptr2]){
                ptr1++;
                ptr2++;
            }
            else
            {
                peak = ptr1;
                break;
            }
        }

        if(peak == 0){
            return false;
        }
        int j = peak;

        while(j  < arr.length-1){
            if(arr[j] > arr[j+1]){
                j++;
            }else{
                return false;
            }
        }

        return true;
    }
}