import java.util.HashMap;
class Solution 
{
    public int subarraySum(int[] nums, int k) 
    {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0, count = 0;
        for (int num : nums) 
        {
            sum += num;
            count += map.getOrDefault(sum - k, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
public class Main 
{
    public static void main(String[] args) 
    {
        int[] arr = {3, 1, 2, 4};
        int k = 6;
        Solution sol = new Solution();
        System.out.println(sol.subarraySum(arr, k));
    }
}