class Solution {
    public int maxProduct(int n) {
        int largest=0;
        int slargest=0;
        while(n>0)
        {
            int d=n%10;
            if(d>largest)
            {
                slargest=largest;
                largest=d;
            }
            else if(d>slargest)
            slargest=d;
            n/=10;
        }
        return largest*slargest;
    }
}