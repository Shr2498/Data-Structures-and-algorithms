import java.io.BufferedReader;
import java.io.FileReader;

class Project3
{
	
	
	static int divideArray(int arr[], int n, int k)
	{
		
		int dp[][] = new int[500][500];
		
		int i, j;
		
		for(i = 0; i < 500; i++)
			for(j = 0; j < 500; j++)
				dp[i][j] = 0;
				
	
		
		for (i = n - 1; i >= 0; i--)
		{
			for (j = 0; j <= k; j++)
			{
				
				
				dp[i][j] = Integer.MAX_VALUE;
	
				
				int max_ = -1, sum = 0;
	
				
				for (int l = i; l < n; l++)
				{
					
					max_ = Math.max(max_, arr[l]);
					sum += arr[l];
	
					int diff = (l - i + 1) * max_ - sum;
	
					if (j > 0)
						dp[i][j] = Math.min(dp[i][j], diff +
											dp[l + 1][j - 1]);
					else
						dp[i][j] = diff;
				}
			}
		}
	 
		
		return dp[0][k];
	}
	
	public static void main (String[] args)
    {
        int arr[] = { 2, 9, 5, 4, 8, 3, 6 };
        int n = arr.length;
        int k = 3;

        System.out.println(divideArray(arr, n, k));
    }
}
