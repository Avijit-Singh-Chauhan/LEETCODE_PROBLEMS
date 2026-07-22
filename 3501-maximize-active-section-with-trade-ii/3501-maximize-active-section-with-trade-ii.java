import java.util.ArrayList;
import java.util.List;
class Solution {
    static class Group {
        int start;
        int length;
        Group(int start, int length) {
            this.start = start;
            this.length = length;
        }
    }
    static class SparseTable {
        private final int[][] st;
        public SparseTable(int[] nums) {
            int n = nums.length;
            if (n == 0) {
                st = new int[0][0];
                return;
            }
            int log = 32 - Integer.numberOfLeadingZeros(n);
            st = new int[log][n];
            System.arraycopy(nums, 0, st[0], 0, n);
            for (int i = 1; i < log; i++) {
                for (int j = 0; j + (1 << i) <= n; j++) {
                    st[i][j] = Math.max(st[i - 1][j], st[i - 1][j + (1 << (i - 1))]);
                }
            }
        }
        public int query(int l, int r) {
            if (l > r) return 0;
            int i = 32 - Integer.numberOfLeadingZeros(r - l + 1) - 1;
            return Math.max(st[i][l], st[i][r - (1 << i) + 1]);
        }
    }
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        final int n = s.length();
        int totalOnes = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') totalOnes++;
        }
        List<Group> zeroGroups = new ArrayList<>();
        int[] zeroGroupIndex = new int[n];
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                if (i > 0 && s.charAt(i - 1) == '0') {
                    zeroGroups.get(zeroGroups.size() - 1).length++;
                } else {
                    zeroGroups.add(new Group(i, 1));
                }
                zeroGroupIndex[i] = zeroGroups.size() - 1;
            } else {
                zeroGroupIndex[i] = -1;
            }
        }
        int m = zeroGroups.size();
        if (m < 2) {
            List<Integer> ans = new ArrayList<>(queries.length);
            for (int i = 0; i < queries.length; i++) {
                ans.add(totalOnes);
            }
            return ans;
        }
        int[] mergeLengths = new int[m - 1];
        for (int i = 0; i < m - 1; i++) {
            mergeLengths[i] = zeroGroups.get(i).length + zeroGroups.get(i + 1).length;
        }
        SparseTable st = new SparseTable(mergeLengths);
        List<Integer> ans = new ArrayList<>(queries.length);
        for (int[] q : queries) {
            int l = q[0];
            int r = q[1];
            int gL = (s.charAt(l) == '0') ? zeroGroupIndex[l] : -1;
            int gR = (s.charAt(r) == '0') ? zeroGroupIndex[r] : -1;
            int leftLen = (gL != -1) ? (zeroGroups.get(gL).start + zeroGroups.get(gL).length - l) : 0;
            int rightLen = (gR != -1) ? (r - zeroGroups.get(gR).start + 1) : 0;
            int firstFullyContainedZeroGroup = (gL != -1) ? gL + 1 : getNextZeroGroupIdx(zeroGroups, l);
            int lastFullyContainedZeroGroup = (gR != -1) ? gR - 1 : getPrevZeroGroupIdx(zeroGroups, r);
            int maxGain = 0;
            if (firstFullyContainedZeroGroup <= lastFullyContainedZeroGroup) {
                maxGain = Math.max(maxGain, st.query(firstFullyContainedZeroGroup, lastFullyContainedZeroGroup - 1));
            }
            if (s.charAt(l) == '0' && s.charAt(r) == '0' && gL + 1 == gR) {
                maxGain = Math.max(maxGain, leftLen + rightLen);
            }
            if (s.charAt(l) == '0' && gL + 1 < m) {
                int nextGroupIdx = gL + 1;
                int nextLen = (nextGroupIdx == gR) ? rightLen : zeroGroups.get(nextGroupIdx).length;
                if (nextGroupIdx <= (gR != -1 ? gR : getNextZeroGroupIdx(zeroGroups, r + 1) - 1)) {
                    maxGain = Math.max(maxGain, leftLen + nextLen);
                }
            }
            if (s.charAt(r) == '0' && gR - 1 >= 0) {
                int prevGroupIdx = gR - 1;
                int prevLen = (prevGroupIdx == gL) ? leftLen : zeroGroups.get(prevGroupIdx).length;
                if (prevGroupIdx >= (gL != -1 ? gL : getPrevZeroGroupIdx(zeroGroups, l - 1) + 1)) {
                    maxGain = Math.max(maxGain, rightLen + prevLen);
                }
            }
            ans.add(totalOnes + maxGain);
        }
        return ans;
    }
    private int getNextZeroGroupIdx(List<Group> zeroGroups, int pos) {
        int low = 0, high = zeroGroups.size() - 1;
        int ans = zeroGroups.size();
        while (low <= high) {
            int mid = (low + high) >>> 1;
            if (zeroGroups.get(mid).start >= pos) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }
    private int getPrevZeroGroupIdx(List<Group> zeroGroups, int pos) {
        int low = 0, high = zeroGroups.size() - 1;
        int ans = -1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            if (zeroGroups.get(mid).start + zeroGroups.get(mid).length - 1 <= pos) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }
}