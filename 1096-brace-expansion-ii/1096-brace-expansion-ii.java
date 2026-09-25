import java.util.*;

class Solution {
    int i;
    String s;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        i = 0;
        return new ArrayList<>(parseExpression());
    }

    private TreeSet<String> parseExpression() {
        TreeSet<String> res = parseTerm();

        while (i < s.length() && s.charAt(i) == ',') {
            i++;
            res.addAll(parseTerm());
        }

        return res;
    }

    private TreeSet<String> parseTerm() {
        TreeSet<String> res = new TreeSet<>();
        res.add("");

        while (i < s.length() && s.charAt(i) != '}' && s.charAt(i) != ',') {
            TreeSet<String> next = parseFactor();
            TreeSet<String> temp = new TreeSet<>();

            for (String a : res)
                for (String b : next)
                    temp.add(a + b);

            res = temp;
        }

        return res;
    }

    private TreeSet<String> parseFactor() {
        TreeSet<String> res = new TreeSet<>();

        if (s.charAt(i) == '{') {
            i++;
            res = parseExpression();
            i++;
        } else {
            res.add(String.valueOf(s.charAt(i++)));
        }

        return res;
    }
}