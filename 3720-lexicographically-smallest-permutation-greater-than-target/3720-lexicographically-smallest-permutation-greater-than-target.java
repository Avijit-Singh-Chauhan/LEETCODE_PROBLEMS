import java.util.*;

class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        char[] t = target.toCharArray();
        char[] ans = new char[t.length];

        for (int i = 0; i < t.length; i++) {
            int x = t[i] - 'a';

            if (freq[x] > 0) {
                ans[i] = t[i];
                freq[x]--;
            } else {
                for (int c = x + 1; c < 26; c++) {
                    if (freq[c] > 0) {
                        ans[i] = (char)('a' + c);
                        freq[c]--;
                        fill(ans, i + 1, freq);
                        return new String(ans);
                    }
                }

                for (int j = i - 1; j >= 0; j--) {
                    int p = ans[j] - 'a';
                    freq[p]++;

                    for (int c = p + 1; c < 26; c++) {
                        if (freq[c] > 0) {
                            ans[j] = (char)('a' + c);
                            freq[c]--;
                            fill(ans, j + 1, freq);
                            return new String(ans);
                        }
                    }
                }

                return "";
            }
        }

        for (int j = t.length - 1; j >= 0; j--) {
            int p = ans[j] - 'a';
            freq[p]++;

            for (int c = p + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    ans[j] = (char)('a' + c);
                    freq[c]--;
                    fill(ans, j + 1, freq);
                    return new String(ans);
                }
            }
        }

        return "";
    }

    private void fill(char[] ans, int start, int[] freq) {
        int pos = start;

        for (int c = 0; c < 26; c++) {
            while (freq[c] > 0) {
                ans[pos++] = (char)('a' + c);
                freq[c]--;
            }
        }
    }
}