class Solution {
    public int rangeBitwiseAnd(int left, int right) {
        /*int shift = 0;

        while(left != right){
            left = left >>1 ;
            right = right >> 1;

            shift++;
        }

        return left << shift;*/

        while(right > left){
            right = right & (right -1);
        }
        return right;
    }
}