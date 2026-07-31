class Solution 
{
    public int minimumPushes(String word) 
    {
        int[] freq = new int[26];
        for (char c : word.toCharArray()) 
        {
            freq[c - 'a']++;
        }
        Arrays.sort(freq);
        int pushes = 1;
        int count = 0;
        long ans = 0;
        for (int i = 25; i >= 0; i--) 
        {
            if (freq[i] == 0) break;
            ans += (long) freq[i] * pushes;
            count++;
            if (count == 8) 
            {
                count = 0;
                pushes++;
            }
        }
        return (int) ans;
    }
}