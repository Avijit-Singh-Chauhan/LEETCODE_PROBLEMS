class Solution {
    public String smallestNumber(String num, long t) {
        long temp = t;
        for (int p : new int[]{2, 3, 5, 7}) {
            while (temp % p == 0) {
                temp /= p;
            }
        }
        if (temp > 1) {
            return "-1";
        }

        int n = num.length();
        int[] req = getCounts(t);

        int[][] pref = new int[n + 1][4];
        int firstZero = -1;
        for (int j = 0; j < n; j++) {
            System.arraycopy(pref[j], 0, pref[j + 1], 0, 4);
            int d = num.charAt(j) - '0';
            if (d == 0) {
                firstZero = j;
                break;
            }
            addFactor(pref[j + 1], d, 1);
        }

        int maxI = (firstZero == -1) ? n : firstZero;

        for (int i = maxI; i >= 0; i--) {
            int[] prefixCounts = pref[i];

            int startD;
            if (i == n) {
                if (canSatisfy(req, prefixCounts, 0)) {
                    return num;
                }
                startD = num.charAt(n - 1) - '0' + 1;
            } else {
                startD = num.charAt(i) - '0' + 1;
            }

            for (int d = startD; d <= 9; d++) {
                int[] curCounts = prefixCounts.clone();
                addFactor(curCounts, d, 1);
                int remLen = n - 1 - i;
                if (canSatisfy(req, curCounts, remLen)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append(d);
                    appendSuffix(sb, req, curCounts, remLen);
                    return sb.toString();
                }
            }
        }

        int len = n + 1;
        int[] emptyCounts = new int[4];
        while (true) {
            for (int d = 1; d <= 9; d++) {
                int[] tmpCounts = emptyCounts.clone();
                addFactor(tmpCounts, d, 1);
                if (canSatisfy(req, tmpCounts, len - 1)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(d);
                    appendSuffix(sb, req, tmpCounts, len - 1);
                    return sb.toString();
                }
            }
            len++;
        }
    }

    private int[] getCounts(long t) {
        int[] c = new int[4];
        int[] primes = {2, 3, 5, 7};
        for (int i = 0; i < 4; i++) {
            while (t % primes[i] == 0) {
                c[i]++;
                t /= primes[i];
            }
        }
        return c;
    }

    private void addFactor(int[] c, int d, int sign) {
        if (d == 2) c[0] += sign;
        else if (d == 3) c[1] += sign;
        else if (d == 4) c[0] += 2 * sign;
        else if (d == 5) c[2] += sign;
        else if (d == 6) { c[0] += sign; c[1] += sign; }
        else if (d == 7) c[3] += sign;
        else if (d == 8) c[0] += 3 * sign;
        else if (d == 9) c[1] += 2 * sign;
    }

    private boolean canSatisfy(int[] req, int[] cur, int remLen) {
        int need2 = Math.max(0, req[0] - cur[0]);
        int need3 = Math.max(0, req[1] - cur[1]);
        int need5 = Math.max(0, req[2] - cur[2]);
        int need7 = Math.max(0, req[3] - cur[3]);

        int slotsFor23 = remLen - need5 - need7;
        if (slotsFor23 < 0) return false;

        int c8 = need2 / 3;
        need2 %= 3;
        int c9 = need3 / 2;
        need3 %= 2;

        int extraSlots = slotsFor23 - (c8 + c9);
        if (extraSlots < 0) return false;

        int reqSlots;
        if (need2 == 0 && need3 == 0) {
            reqSlots = 0;
        } else if (need2 == 2 && need3 == 1) {
            reqSlots = 2;
        } else {
            reqSlots = 1;
        }

        return extraSlots >= reqSlots;
    }

    private void appendSuffix(StringBuilder sb, int[] req, int[] cur, int remLen) {
        int need2 = Math.max(0, req[0] - cur[0]);
        int need3 = Math.max(0, req[1] - cur[1]);
        int need5 = Math.max(0, req[2] - cur[2]);
        int need7 = Math.max(0, req[3] - cur[3]);

        int c8 = need2 / 3;
        need2 %= 3;
        int c9 = need3 / 2;
        need3 %= 2;

        int extra = remLen - need5 - need7 - c8 - c9;

        int c2 = 0, c3 = 0, c4 = 0, c6 = 0;

        if (need2 == 2 && need3 == 1) {
            if (extra >= 2) {
                c2 = 1;
                c6 = 1;
            } else {
                c3 = 1;
                c4 = 1;
            }
        } else if (need2 == 2 && need3 == 0) {
            c4 = 1;
        } else if (need2 == 1 && need3 == 1) {
            c6 = 1;
        } else if (need2 == 1 && need3 == 0) {
            c2 = 1;
        } else if (need2 == 0 && need3 == 1) {
            c3 = 1;
        }

        int totalUsed = c2 + c3 + c4 + need5 + c6 + need7 + c8 + c9;
        int c1 = remLen - totalUsed;

        for (int i = 0; i < c1; i++) sb.append('1');
        for (int i = 0; i < c2; i++) sb.append('2');
        for (int i = 0; i < c3; i++) sb.append('3');
        for (int i = 0; i < c4; i++) sb.append('4');
        for (int i = 0; i < need5; i++) sb.append('5');
        for (int i = 0; i < c6; i++) sb.append('6');
        for (int i = 0; i < need7; i++) sb.append('7');
        for (int i = 0; i < c8; i++) sb.append('8');
        for (int i = 0; i < c9; i++) sb.append('9');
    }
}