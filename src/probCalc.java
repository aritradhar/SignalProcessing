import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class probCalc 
{
	public static Character []elmnt={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	public static int []outerProb=new int[26]; 
	public static int [][]present_in=new int [26][26];
	public static int testcase=35;     //change the testcase value as required
	public static float [][]inner_prob=new float[26][26];
	public static float [][]bayes_prob=new float[26][26];
	
	public void outer_prob() throws Exception
	{
		
		String fname_partial="Corr_";
		String fname_ext=".txt";
		int flag_present=0;
		int k=0;
		for(int i=0;i<26;i++)
		{
			k=0;
			flag_present=0;
			for(int j=0;j<26;j++)
			{
				flag_present=0;
				BufferedReader br=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(fname_partial+elmnt[j].toString()+fname_ext))));
				String val_s=new String("");
				while((val_s=br.readLine())!=null)
				{
					if(i==Integer.parseInt(val_s))
						flag_present=1;
				}
				if(flag_present==1)
				{
					outerProb[i]++;
					present_in[i][k++]=j;
				}
			}			
		}
		System.out.println("outer count");
		//for(int i=0;i<26;i++)
			//System.out.println(i+": "+outerProb[i]);
		
		for(int i=0;i<26;i++)
		{
			System.out.print(i+" present in the files : ");
			for(int j=0;j<outerProb[i];j++)
			{
				System.out.print(elmnt[present_in[i][j]]+";");      //present[i][j] denotes that corr_j.txt file has i 
			}
			float prob=1/(float)outerProb[i];
			System.out.print("|  total count : "+outerProb[i]+"| prob: "+prob);
			System.out.print("\n");
		}
		System.out.println("-----End of outer calc------\n");
	}
	
	public void inner_prob() throws Exception
	{
		System.out.println("-----Inner probability-----");
		int [][]list=new int[26][26];
		Integer non_z=0;
		for(int i=0;i<26;i++)
		{
			for(int j=0;j<26;j++)
			{
				list[i][j]=0;
			}
		}
		for(int i=0;i<26;i++)
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream("Corr_"+elmnt[i]+".txt"))));
			String val_s=new String("");
			while((val_s=br.readLine())!=null)
			{
				//if(!val_s.equals("\n"))
				list[i][Integer.parseInt(val_s)]++;
			}
			for(int j=0;j<26;j++)
			{
				if(list[i][j]!=0)
					non_z++;
			}
			for(int k=0;k<26;k++)
			inner_prob[i][k]=(float)list[i][k]/testcase;
		}	
		
		for(int i=0;i<26;i++)
		{
			System.out.print("Corr_"+elmnt[i]+".txt probability:: ");
			for(int j=0;j<26;j++)
			{
				if(list[i][j]!=0)
				System.out.print(j+":"+inner_prob[i][j]+";  ");
			}
			System.out.print("\n");
		}
		System.out.println("-------End of inner probability------");
	}
	
	public void bayes_prob() throws Exception
	{
		System.out.println("------------------test file evaluation--------------------");
		// the sample file is Corr_alpha.txt. This file has only one row (0-25) as here is no concept of test case.
		BufferedReader br=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream("Corr_alpha.txt"))));  //input testcase file
		String val_s=new String("");
		Integer sample_pos=new Integer(0);
		while((val_s=br.readLine())!=null)
		{
			sample_pos=Integer.parseInt(val_s);  
		}
		//now we have to look the probability factor of sample_pos in the existing database by using inner and outer prob
		System.out.println(sample_pos+" is present in : ");
		for(int i=0;i<outerProb[sample_pos];i++)
		{
			System.out.print("Corr_"+elmnt[present_in[sample_pos][i]]+";");
		}
		float prob=(float) 1/outerProb[sample_pos];
		System.out.println("  with prob. "+"1/"+outerProb[sample_pos]+" = "+prob);
		

		float denom_sum=0f;
		for(int i=0;i<outerProb[sample_pos];i++)
		{
			System.out.println("in file Corr_"+elmnt[present_in[sample_pos][i]]+" "+sample_pos+" present with prob : "+inner_prob[present_in[sample_pos][i]][sample_pos]);
			denom_sum+=inner_prob[present_in[sample_pos][i]][sample_pos]*prob;
		}
		System.out.println(denom_sum);
		System.out.println("-------------alpha probability share-----------------");
		for(int i=0; i<outerProb[sample_pos];i++)
		{	
			float numer=prob*inner_prob[present_in[sample_pos][i]][sample_pos];
			//System.out.println(numer);
			float bayesprob=numer/denom_sum;
			System.out.println("probability being "+elmnt[present_in[sample_pos][i]]+" is :: "+bayesprob);
		}
	}
	
	public static void main(String []a) throws Exception
	{
		new probCalc().outer_prob();
		new probCalc().inner_prob();
		new probCalc().bayes_prob();
	}

}
