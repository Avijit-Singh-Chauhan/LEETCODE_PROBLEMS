class Solution {
public int distinctSubseqII(String s) {
long[] last = new long[26];
long total = 0;
long mod = 1000000007L;

    for (char c : s.toCharArray()) {
        int i = c - 'a';
        long add = (total + 1) % mod;
        total = (total + add - last[i] + mod) % mod;
        last[i] = add;
    }

    return (int) total;
}

}
