import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;
        Integer[] idx = new Integer[n];

        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }

        Arrays.sort(idx, (a, b) -> Integer.compare(nums[a], nums[b]));

        int start = 0;

        while (start < n) {
            int end = start;

            while (end + 1 < n &&
                   (long) nums[idx[end + 1]] - nums[idx[end]] <= limit) {
                end++;
            }

            int[] positions = new int[end - start + 1];
            int[] values = new int[end - start + 1];

            for (int i = start; i <= end; i++) {
                positions[i - start] = idx[i];
                values[i - start] = nums[idx[i]];
            }

            Arrays.sort(positions);

            for (int i = 0; i < positions.length; i++) {
                nums[positions[i]] = values[i];
            }

            start = end + 1;
        }

        return nums;
    }
}