class Solution {
    public String reverseParentheses(String s) {
        int n = s.length();
        int[] pair = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack[++top] = i;
            } else if (s.charAt(i) == ')') {
                int j = stack[top--];
                pair[i] = j;
                pair[j] = i;
            }
        }

        StringBuilder ans = new StringBuilder();
        int direction = 1;

        for (int i = 0; i < n; i += direction) {
            char c = s.charAt(i);

            if (c == '(' || c == ')') {
                i = pair[i];
                direction = -direction;
            } else {
                ans.append(c);
            }
        }

        return ans.toString();
    }
}