class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int odd = 0;
        char mid = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                odd++;
                mid = (char) ('a' + i);
            }
        }

        if (odd > 1) return "";

        int halfLen = n / 2;
        int[] half = new int[26];

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
        }

        String prefix = target.substring(0, halfLen);

        int[] remaining = half.clone();
        boolean possible = true;

        for (int i = 0; i < halfLen; i++) {
            int c = prefix.charAt(i) - 'a';

            if (remaining[c] == 0) {
                possible = false;
                break;
            }

            remaining[c]--;
        }

        if (possible) {
            String candidate = makePalindrome(prefix, mid);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        for (int pivot = halfLen - 1; pivot >= 0; pivot--) {
            remaining = half.clone();

            boolean valid = true;

            for (int i = 0; i < pivot; i++) {
                int c = prefix.charAt(i) - 'a';

                if (remaining[c] == 0) {
                    valid = false;
                    break;
                }

                remaining[c]--;
            }

            if (!valid) continue;

            int current = prefix.charAt(pivot) - 'a';

            for (int c = current + 1; c < 26; c++) {
                if (remaining[c] == 0) continue;

                int[] temp = remaining.clone();
                temp[c]--;

                StringBuilder h = new StringBuilder();

                for (int i = 0; i < pivot; i++) {
                    h.append(prefix.charAt(i));
                }

                h.append((char) ('a' + c));

                for (int x = 0; x < 26; x++) {
                    while (temp[x] > 0) {
                        h.append((char) ('a' + x));
                        temp[x]--;
                    }
                }

                return makePalindrome(h.toString(), mid);
            }
        }

        return "";
    }

    private String makePalindrome(String half, char mid) {
        StringBuilder result = new StringBuilder();

        result.append(half);

        if (mid != 0) {
            result.append(mid);
        }

        for (int i = half.length() - 1; i >= 0; i--) {
            result.append(half.charAt(i));
        }

        return result.toString();
    }
}