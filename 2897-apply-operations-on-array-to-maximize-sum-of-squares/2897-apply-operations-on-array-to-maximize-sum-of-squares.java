
class Solution {
    public int maxSum(java.util.List<Integer> nums, int k) {
        int MOD = 1000000007;
        int[] count = new int[31];

        for (int num : nums) {
            for (int i = 0; i < 31; i++) {
                if ((num & (1 << i)) != 0) {
                    count[i]++;
                }
            }
        }

        long ans = 0;

        for (int i = 0; i < k; i++) {
            long num = 0;

            for (int j = 0; j < 31; j++) {
                if (count[j] > 0) {
                    num |= (1L << j);
                    count[j]--;
                }
            }

            ans = (ans + (num * num) % MOD) % MOD;
        }

        return (int) ans;
    }
}
