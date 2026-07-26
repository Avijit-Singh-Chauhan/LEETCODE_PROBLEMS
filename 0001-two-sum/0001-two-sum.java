class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[][] copy=new int[nums.length][2];
        for(int i=0;i<nums.length;i++)
        {
            copy[i][0]=nums[i]; //value
            copy[i][1]=i; //original index
        }
        Arrays.sort(copy,(a,b)->a[0]-b[0]);
        int right=nums.length-1;
        int left=0;
        while(left<right)
        {
            int sum=copy[left][0]+copy[right][0];
            if(sum==target)
            {
                return new int[]{copy[left][1],copy[right][1]};
            }
            else if(sum<target)
            left++;
            else
            right--;
        }
        return new int[]{};
    }
}