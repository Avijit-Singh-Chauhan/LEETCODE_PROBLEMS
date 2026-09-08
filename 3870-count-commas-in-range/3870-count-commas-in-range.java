class Solution {
    public int countCommas(int n) {
        if (n < 1000) return 0;

        int count = 0;
        for (int i = 1000; i <= n; i++) {
            count += String.valueOf(i).length() > 3 ? (String.valueOf(i).length() - 1) / 3 : 0;
        }
        return count;
    }
}