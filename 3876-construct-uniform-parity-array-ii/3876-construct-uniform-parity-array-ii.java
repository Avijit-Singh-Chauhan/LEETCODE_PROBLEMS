class Solution {
    public boolean uniformArray(int[] nums1) {
        int min = nums1[0];
        boolean hasOdd = false;

        for (int x : nums1) {
            min = Math.min(min, x);
            if ((x & 1) == 1) {
                hasOdd = true;
            }
        }

        return !hasOdd || (min & 1) == 1;
    }
}