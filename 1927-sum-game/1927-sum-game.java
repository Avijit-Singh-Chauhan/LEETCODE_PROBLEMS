class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n / 2;

        int diff = 0;
        int qDiff = 0;

        for (int i = 0; i < n; i++) {
            char c = num.charAt(i);

            if (c == '?') {
                if (i < half) {
                    qDiff++;
                } else {
                    qDiff--;
                }
            } else {
                if (i < half) {
                    diff += c - '0';
                } else {
                    diff -= c - '0';
                }
            }
        }

        // Alice can force inequality if the number of ? is odd
        if (Math.abs(qDiff) % 2 == 1) {
            return true;
        }

        // Bob can force equality only in this case
        return diff + (9 * qDiff) / 2 != 0;
    }
}