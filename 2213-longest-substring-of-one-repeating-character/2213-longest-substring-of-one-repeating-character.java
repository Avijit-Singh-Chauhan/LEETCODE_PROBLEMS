class Solution {
    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        int k = queryIndices.length;
        char[] arr = s.toCharArray();
        int[] ans = new int[k];

        SegmentTree st = new SegmentTree(arr);

        for (int i = 0; i < k; i++) {
            arr[queryIndices[i]] = queryCharacters.charAt(i);
            st.update(queryIndices[i], arr[queryIndices[i]]);
            ans[i] = st.tree[1].max;
        }

        return ans;
    }

    static class Node {
        char leftChar, rightChar;
        int leftLen, rightLen, max, len;

        Node() {}

        Node(char c) {
            leftChar = rightChar = c;
            leftLen = rightLen = max = len = 1;
        }
    }

    static class SegmentTree {
        Node[] tree;
        int n;

        SegmentTree(char[] arr) {
            n = arr.length;
            tree = new Node[4 * n];
            build(1, 0, n - 1, arr);
        }

        void build(int node, int l, int r, char[] arr) {
            if (l == r) {
                tree[node] = new Node(arr[l]);
                return;
            }

            int mid = (l + r) / 2;
            build(node * 2, l, mid, arr);
            build(node * 2 + 1, mid + 1, r, arr);
            tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
        }

        void update(int node, int l, int r, int idx, char c) {
            if (l == r) {
                tree[node] = new Node(c);
                return;
            }

            int mid = (l + r) / 2;

            if (idx <= mid)
                update(node * 2, l, mid, idx, c);
            else
                update(node * 2 + 1, mid + 1, r, idx, c);

            tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
        }

        void update(int idx, char c) {
            update(1, 0, n - 1, idx, c);
        }

        Node merge(Node a, Node b) {
            Node res = new Node();

            res.len = a.len + b.len;
            res.leftChar = a.leftChar;
            res.rightChar = b.rightChar;
            res.leftLen = a.leftLen;
            res.rightLen = b.rightLen;
            res.max = Math.max(a.max, b.max);

            if (a.leftLen == a.len && a.rightChar == b.leftChar)
                res.leftLen = a.len + b.leftLen;

            if (b.rightLen == b.len && a.rightChar == b.leftChar)
                res.rightLen = b.len + a.rightLen;

            if (a.rightChar == b.leftChar)
                res.max = Math.max(res.max, a.rightLen + b.leftLen);

            return res;
        }
    }
}