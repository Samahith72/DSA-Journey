class Solution {
    public int heightChecker(int[] heights) {

        int[] frequency = new int[101];
        
        for(int height: heights){
            frequency[height]++;
        }

        int idx =0;
        int count=0;

        for(int num=1;num <=100;num++){
            if(frequency[num] > 0){
                while(frequency[num] > 0){
                    if(heights[idx] != num){
                        count++;
                    }
                    idx++;
                    frequency[num]--;
                }
            }
        }

        return count;
        
    }
}