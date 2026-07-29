import java.util.*;

class Solution 
{
    static final long LIMIT = 1_000_001L;
    public String smallestPalindrome(String s, int k) 
    {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) 
        {
            freq[c - 'a']++;
        }
        String mid = "";
        ArrayList<Integer> half = new ArrayList<>();
        for (int i = 0; i < 26; i++) 
        {
            if ((freq[i] & 1) == 1) 
            {
                mid = String.valueOf((char) ('a' + i));
            }
            half.add(freq[i] / 2);
        }
        int halfLen = s.length() / 2;
        long total = countWays(half, halfLen);
        if (total < k) return "";
        StringBuilder left = new StringBuilder();
        for (int pos = 0; pos < halfLen; pos++) 
        {
            for (int c = 0; c < 26; c++) 
            {
                if (half.get(c) == 0) continue;
                half.set(c, half.get(c) - 1);
                long ways = countWays(half, halfLen - pos - 1);
                if (ways >= k) 
                {
                    left.append((char) ('a' + c));
                    break;
                } 
                else 
                {
                    k -= ways;
                    half.set(c, half.get(c) + 1);
                }
            }
        }
        String right = new StringBuilder(left).reverse().toString();
        return left.toString() + mid + right;
    }
    private long countWays(ArrayList<Integer> cnt, int total) 
    {
        long res = 1;
        int rem = total;
        for (int x : cnt) 
        {
            if (x == 0) continue;
            res *= comb(rem, x);
            if (res > LIMIT) return LIMIT;
            rem -= x;
        }
        return Math.min(res, LIMIT);
    }
    private long comb(int n, int r) 
    {
        if (r > n) return 0;
        r = Math.min(r, n - r);
        long ans = 1;
        for (int i = 1; i <= r; i++) 
        {
            ans = ans * (n - r + i) / i;
            if (ans > LIMIT) return LIMIT;
        }
        return ans;
    }
}