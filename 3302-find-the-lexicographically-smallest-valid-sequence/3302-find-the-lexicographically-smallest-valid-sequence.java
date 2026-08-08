import java.util.*;

class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        ArrayList<Integer>[] pos = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            pos[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            pos[word1.charAt(i) - 'a'].add(i);
        }

        int[] suffix = new int[m + 1];
        Arrays.fill(suffix, -1);
        suffix[m] = n;

        int p = n;

        for (int i = m - 1; i >= 0; i--) {
            int c = word2.charAt(i) - 'a';
            int idx = upperBound(pos[c], p - 1);

            if (idx == -1) {
                break;
            }

            suffix[i] = idx;
            p = idx;
        }

        int[] prevDifferent = new int[n];
        Arrays.fill(prevDifferent, -1);

        for (int i = 1; i < n; i++) {
            if (word1.charAt(i) != word1.charAt(i - 1)) {
                prevDifferent[i] = i - 1;
            } else {
                prevDifferent[i] = prevDifferent[i - 1];
            }
        }

        int[] oneMismatch = new int[m + 1];
        Arrays.fill(oneMismatch, -1);
        oneMismatch[m] = n;

        for (int i = m - 1; i >= 0; i--) {
            int c = word2.charAt(i) - 'a';

            int match = upperBound(pos[c], oneMismatch[i + 1] - 1);

            int mismatch = -1;
            int bound = suffix[i + 1];

            if (bound > 0) {
                int j = bound - 1;

                if (word1.charAt(j) != word2.charAt(i)) {
                    mismatch = j;
                } else {
                    mismatch = prevDifferent[j];
                }
            }

            oneMismatch[i] = Math.max(match, mismatch);
        }

        int[] nextDifferent = new int[n];
        Arrays.fill(nextDifferent, n);

        for (int i = n - 2; i >= 0; i--) {
            if (word1.charAt(i) != word1.charAt(i + 1)) {
                nextDifferent[i] = i + 1;
            } else {
                nextDifferent[i] = nextDifferent[i + 1];
            }
        }

        int[] ans = new int[m];
        int prev = -1;
        boolean usedMismatch = false;

        for (int i = 0; i < m; i++) {
            int start = prev + 1;

            if (start >= n) {
                return new int[0];
            }

            int chosen;

            if (usedMismatch) {
                int c = word2.charAt(i) - 'a';
                int equal = lowerBound(pos[c], start);

                if (equal == pos[c].size() || equal >= suffix[i + 1]) {
                    return new int[0];
                }

                chosen = pos[c].get(equal);
            } else {
                int c = word2.charAt(i) - 'a';

                int equalIndex = lowerBound(pos[c], start);
                int equal = n;

                if (equalIndex < pos[c].size()) {
                    int idx = pos[c].get(equalIndex);
                    if (idx < oneMismatch[i + 1]) {
                        equal = idx;
                    }
                }

                int mismatch;

                if (word1.charAt(start) != word2.charAt(i)) {
                    mismatch = start;
                } else {
                    mismatch = nextDifferent[start];
                }

                if (mismatch >= suffix[i + 1]) {
                    mismatch = n;
                }

                chosen = Math.min(equal, mismatch);

                if (chosen == n) {
                    return new int[0];
                }
            }

            ans[i] = chosen;
            prev = chosen;

            if (word1.charAt(chosen) != word2.charAt(i)) {
                usedMismatch = true;
            }
        }

        return ans;
    }

    private int lowerBound(ArrayList<Integer> list, int target) {
        int l = 0;
        int r = list.size();

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (list.get(mid) < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }

    private int upperBound(ArrayList<Integer> list, int target) {
        int l = 0;
        int r = list.size();

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (list.get(mid) <= target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l == 0 ? -1 : list.get(l - 1);
    }
}