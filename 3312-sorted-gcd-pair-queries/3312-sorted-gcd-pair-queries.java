class Solution 
{
    public int[] gcdValues(int[] nums, long[] queries) 
    {
        int max = 0;
        for (int x : nums) 
        {
            max = Math.max(max, x);
        }
        int[] freq = new int[max + 1];
        for (int x : nums) 
        {
            freq[x]++;
        }
        int[] cnt = new int[max + 1];
        for (int d = 1; d <= max; d++) 
        {
            for (int multiple = d; multiple <= max; multiple += d) 
            {
                cnt[d] += freq[multiple];
            }
        }
        long[] exactPairs = new long[max + 1];
        for (int d = max; d >= 1; d--) 
        {
            long pairs = (long) cnt[d] * (cnt[d] - 1) / 2;
            for (int multiple = d + d; multiple <= max; multiple += d) 
            {
                pairs -= exactPairs[multiple];
            }
            exactPairs[d] = pairs;
        }
        long[] prefix = new long[max + 1];
        for (int i = 1; i <= max; i++) 
        {
            prefix[i] = prefix[i - 1] + exactPairs[i];
        }
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++)
        {
            long target = queries[i] + 1; 
            int lo = 1, hi = max;
            while (lo < hi) 
            {
                int mid = (lo + hi) / 2;
                if (prefix[mid] >= target)
                    hi = mid;
                else
                    lo = mid + 1;
            }
            ans[i] = lo;
        }
        return ans;
    }
}