import java.util.*;
class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        int[][] a = new int[n][4];

        for (int i = 0; i < n; i++) {
            a[i][0] = intervals.get(i).get(0);
            a[i][1] = intervals.get(i).get(1);
            a[i][2] = intervals.get(i).get(2);
            a[i][3] = i;
        }

        Arrays.sort(a, (x, y) -> {
            if (x[0] != y[0]) return Integer.compare(x[0], y[0]);
            if (x[1] != y[1]) return Integer.compare(x[1], y[1]);
            return Integer.compare(x[3], y[3]);
        });

        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            int lo = i + 1, hi = n;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (a[mid][0] > a[i][1]) hi = mid;
                else lo = mid + 1;
            }
            next[i] = lo;
        }

        long[][] dp = new long[5][n + 1];
        int[][] len = new int[5][n + 1];
        int[][] ids = new int[5][(n + 1) * 4];

        for (int k = 1; k <= 4; k++) {
            for (int i = n - 1; i >= 0; i--) {
                long skipScore = dp[k][i + 1];

                long takeScore = a[i][2] + dp[k - 1][next[i]];

                int takeLen = len[k - 1][next[i]] + 1;
                int[] take = new int[4];

                int p = 0;
                for (int j = 0; j < len[k - 1][next[i]]; j++) {
                    take[p++] = ids[k - 1][next[i] * 4 + j];
                }

                take[p++] = a[i][3];
                Arrays.sort(take, 0, takeLen);

                boolean chooseTake;

                if (takeScore > skipScore) {
                    chooseTake = true;
                } else if (takeScore < skipScore) {
                    chooseTake = false;
                } else {
                    chooseTake = lexSmaller(
                        take,
                        takeLen,
                        ids[k],
                        (i + 1) * 4,
                        len[k][i + 1]
                    );
                }

                if (chooseTake) {
                    dp[k][i] = takeScore;
                    len[k][i] = takeLen;
                    for (int j = 0; j < takeLen; j++) {
                        ids[k][i * 4 + j] = take[j];
                    }
                } else {
                    dp[k][i] = dp[k][i + 1];
                    len[k][i] = len[k][i + 1];
                    for (int j = 0; j < len[k][i]; j++) {
                        ids[k][i * 4 + j] = ids[k][(i + 1) * 4 + j];
                    }
                }
            }
        }

        int[] ans = new int[len[4][0]];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = ids[4][i];
        }

        return ans;
    }

    private boolean lexSmaller(int[] a, int aLen, int[] b, int start, int bLen) {
        int m = Math.min(aLen, bLen);

        for (int i = 0; i < m; i++) {
            if (a[i] != b[start + i]) {
                return a[i] < b[start + i];
            }
        }

        return aLen < bLen;
    }
}