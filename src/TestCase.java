import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class TestCase 
{
	public static double data[]=new double[26];
	public static double data_bk[]=new double[26];
	
	public static void BubbleSort(double[] a)
	{
		double temp;
		for (int i = 0; i < 26 ; i++)
		{
			for (int j = 0; j <26-1-i; j++)
			{
				if (a[j] > a[j+1])
				{
					temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		FileInputStream in=new FileInputStream("C:\\Users\\Aritra\\Desktop\\audio processing\\Audio samples\\Test_Case1\\18.txt");
		BufferedReader b_in_tst=new BufferedReader(new InputStreamReader(new DataInputStream(in)));		
		String s;
		int j=0;
		while((s=b_in_tst.readLine())!=null)
		{
			data[j]=Double.parseDouble(s);
			j++;
		}
		for(int i=0;i<j;i++)
			data_bk[i]=data[i];
		
		TestCase.BubbleSort(data);
		
		for(int i=0;i<j;i++)
			System.out.println(data[i]);
	}
}
