class Solution {
    public int totalNumbers(int[] digits) {
        boolean[][][] used = new boolean[10][10][10];
        int count = 0;
        for (int i = 0; i < digits.length; i++) {
            for (int j = 0; j < digits.length; j++) {
                for (int k = 0; k < digits.length; k++) {
                    if (i == j || j == k || i == k) continue;
                    if (digits[i] == 0) continue;
                    if (digits[k] % 2 != 0) continue;
                    if (!used[digits[i]][digits[j]][digits[k]]) {
                        used[digits[i]][digits[j]][digits[k]] = true;
                        count++;
                    }
                }
            }
        }
        return count;
    }
}