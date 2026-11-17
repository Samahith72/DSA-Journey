class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] frequency = new int[1001];

        for(int arr: arr1){
           frequency[arr]++;
        }

        int[] result = new int[arr1.length];
        int i =0;

        for(int arr: arr2){
            if(frequency[arr] > 0){
                while(frequency[arr] > 0){
                    result[i++] = arr;
                    frequency[arr]--;
                }
            }
        }

        for(int j=0;j<frequency.length;j++){
            while(frequency[j] >0){
                result[i++] = j;
                frequency[j]--;
            }
        }

        return result;
    }
}