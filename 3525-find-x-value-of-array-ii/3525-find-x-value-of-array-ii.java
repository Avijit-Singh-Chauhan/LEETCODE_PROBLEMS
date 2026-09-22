class Solution {
    static class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            prod = 1 % k;
            cnt = new int[k];
        }
    }

    int k;
    Node[] tree;

    Node merge(Node a, Node b) {
        Node res = new Node(k);
        res.prod = (a.prod * b.prod) % k;

        for (int r = 0; r < k; r++) {
            res.cnt[r] += a.cnt[r];
        }

        for (int r = 0; r < k; r++) {
            int nr = (a.prod * r) % k;
            res.cnt[nr] += b.cnt[r];
        }

        return res;
    }

    void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            tree[node] = new Node(k);
            int v = nums[l] % k;
            tree[node].prod = v;
            tree[node].cnt[v] = 1;
            return;
        }

        int mid = (l + r) >>> 1;

        build(node << 1, l, mid, nums);
        build(node << 1 | 1, mid + 1, r, nums);

        tree[node] = merge(tree[node << 1], tree[node << 1 | 1]);
    }

    void update(int node, int l, int r, int idx, int val) {
        if (l == r) {
            tree[node] = new Node(k);
            int v = val % k;
            tree[node].prod = v;
            tree[node].cnt[v] = 1;
            return;
        }

        int mid = (l + r) >>> 1;

        if (idx <= mid) {
            update(node << 1, l, mid, idx, val);
        } else {
            update(node << 1 | 1, mid + 1, r, idx, val);
        }

        tree[node] = merge(tree[node << 1], tree[node << 1 | 1]);
    }

    Node query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = (l + r) >>> 1;

        if (qr <= mid) {
            return query(node << 1, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node << 1 | 1, mid + 1, r, ql, qr);
        }

        Node left = query(node << 1, l, mid, ql, qr);
        Node right = query(node << 1 | 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;

        int n = nums.length;
        tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            update(1, 0, n - 1, index, value);

            Node res = query(1, 0, n - 1, start, n - 1);

            result[i] = res.cnt[x];
        }

        return result;
    }
}