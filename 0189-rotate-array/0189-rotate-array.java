class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        int[] res = new int[n];
        for (int i = 0; i < n - k; i++) {
            res[i + k] = nums[i];
        }
        for (int i = n - k; i < n; i++) {
            res[i - (n - k)] = nums[i];
        }
        for (int i = 0; i < n; i++) {
            nums[i] = res[i];
        }
    }
}