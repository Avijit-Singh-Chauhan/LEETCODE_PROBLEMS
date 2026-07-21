class Solution 
{
    public int maxActiveSectionsAfterTrade(String s) 
    {
        int n = s.length();
        int ones = 0;
        for (char c : s.toCharArray()) 
        {
            if (c == '1') ones++;
        }
        String t = "1" + s + "1";
        int m = t.length();
        int i = 0;
        int bestGain = 0;
        while (i < m) 
        {
            char ch = t.charAt(i);
            int j = i;
            while (j < m && t.charAt(j) == ch) j++;
            if (ch == '1')
            {
                if (i > 0 && j < m && t.charAt(i - 1) == '0' && t.charAt(j) == '0') 
                {
                    int left = 0;
                    int p = i - 1;
                    while (p >= 0 && t.charAt(p) == '0') 
                    {
                        left++;
                        p--;
                    }
                    int right = 0;
                    p = j;
                    while (p < m && t.charAt(p) == '0') 
                    {
                        right++;
                        p++;
                    }
                    bestGain = Math.max(bestGain, left + right);
                }
            }
            i = j;
        }
        return ones + bestGain;
    }
}