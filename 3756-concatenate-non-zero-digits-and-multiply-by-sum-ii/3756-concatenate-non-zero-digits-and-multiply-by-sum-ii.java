import java.util.Arrays;

public class Solution {
    public int[] sumAndMultiply(String s, int[][] queries) {
        int m = s.length();
        long MOD = 1000000007L;
        
        // Prefix arrays
        long[] prefSum = new long[m + 1];
        long[] prefX = new long[m + 1];
        int[] nzCount = new int[m + 1];
        long[] pow10 = new long[m + 1];
        
        pow10[0] = 1;
        for (int i = 1; i <= m; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }
        
        for (int i = 0; i < m; i++) {
            int digit = s.charAt(i) - '0';
            
            // 1. Update digit sum prefix
            prefSum[i + 1] = prefSum[i] + digit;
            
            // 2. Update non-zero count and prefix X value
            if (digit != 0) {
                nzCount[i + 1] = nzCount[i] + 1;
                prefX[i + 1] = (prefX[i] * 10 + digit) % MOD;
            } else {
                nzCount[i + 1] = nzCount[i];
                prefX[i + 1] = prefX[i]; // Value remains unchanged
            }
        }
        
        int numQueries = queries.length;
        int[] answer = new int[numQueries];
        
        for (int i = 0; i < numQueries; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            
            // Calculate sum of digits in s[l..r]
            long sum = prefSum[r + 1] - prefSum[l];
            
            // Calculate total non-zero digits in s[l..r]
            int k = nzCount[r + 1] - nzCount[l];
            
            // Calculate concatenated integer x
            long x = (prefX[r + 1] - (prefX[l] * pow10[k]) % MOD + MOD) % MOD;
            
            // Calculate final answer for this query
            answer[i] = (int) ((x * (sum % MOD)) % MOD);
        }
        
        return answer;
    }
}