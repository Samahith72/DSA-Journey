import java.util.HashMap;

class Solution {
    public int maxPoints(int[][] points) {
        if(points.length <= 2){
            return points.length;
        }

        int answer =0;

        for(int i=0;i< points.length;i++){
            HashMap<String, Integer> map = new HashMap<>();
            int maxSlope = 0;

            for(int j=i+1;j<points.length;j++){
                int dy = points[j][1] - points[i][1];
                int dx = points[j][0] - points[i][0];

                int gcd = gcd(dy,dx);

                dy /= gcd;
                dx /= gcd;

                if(dx < 0){
                    dy = -dy;
                    dx =-dx;
                }

                if(dy == 0){
                    dx =1;
                }
                if(dx == 0){
                    dy = 1;
                }

                String slope = dy + "/" + dx;
                int frequency = map.getOrDefault(slope,0)+1;
                map.put(slope, frequency);
                maxSlope = Math.max(maxSlope, frequency);
            }

            answer = Math.max(answer, maxSlope+1);

        }
        return answer;
    }
    private int gcd(int a, int b) {

        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }
}