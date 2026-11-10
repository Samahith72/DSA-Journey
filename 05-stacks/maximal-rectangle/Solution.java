import java.util.Arrays;
import java.util.Stack;

class Solution {
    public int maximalRectangle(char[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;

        int[] height = new int[n];
        Arrays.fill(height, 0);
        int maxArea = 0;

        for(int i=0;i <m;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j] == '1'){
                    height[j]++;
                }else{
                    height[j] = 0;
                }
            }
            int currentArea = largestHistogram(height);
            maxArea = Math.max(maxArea, currentArea);
        }
        return maxArea;
        
    }

    private int largestHistogram(int[] heights){
        Stack<Integer> stack = new Stack<>();
        int answer = 0;
        int n = heights.length;

        for(int i=0;i<=n;i++){
            int currentHeight = (i == n)? 0: heights[i];

            while(!stack.isEmpty()&& heights[stack.peek()] > currentHeight){
                int height = heights[stack.pop()];

                int width;
                if(stack.isEmpty()){
                    width = i;
                }
                else{
                    width = i - stack.peek()-1;
                }

                int area = height * width;
                answer = Math.max(answer, area);
            }

            stack.push(i);
        }

        return answer;
    }
}