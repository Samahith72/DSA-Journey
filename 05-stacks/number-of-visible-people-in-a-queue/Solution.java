class Solution {
    public int[] canSeePersonsCount(int[] heights) {

        int n = heights.length;
        int[] answer = new int[n];
        int[] stack = new int[n];

        int top = -1;

        for(int i=n-1;i >=0;i--){
            int count = 0;
            int currentHeight = heights[i];

            while(top >=0 && stack[top] < currentHeight){
                count++;
                top--;
            }

            if(top >=0){
                count++;
            }

            top++;
            answer[i] = count;
            stack[top] = currentHeight;
        }
        
        return answer;
    }
}