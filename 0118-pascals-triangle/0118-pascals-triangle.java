class Solution {
    public List<Integer> generateRows(int rows)
    {
        List<Integer> row=new ArrayList<>();
        int ans=1;
        row.add(1);
        for(int i=1;i<rows;i++)
        {
            ans=ans*(rows-i)/i;
            row.add(ans);
        }
        return row;
    }
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> answer=new ArrayList<>();
        for(int i=1;i<=numRows;i++)
        {
            answer.add(generateRows(i));
        }
        return answer;
    }
}