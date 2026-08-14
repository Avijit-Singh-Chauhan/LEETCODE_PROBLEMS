class Solution {
    public int maximumLengthSubstring(String s) {
        int[] count = new int[26];
        int left = 0;
        int ans = 0;

        for (int right = 0; right < s.length(); right++) {
            int index = s.charAt(right) - 'a';
            count[index]++;

            // If any character occurs more than twice,
            // shrink the window from the left
            while (count[index] > 2) {
                count[s.charAt(left) - 'a']--;
                left++;
            }

            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }
}