//*************************************************************************************
//*********************************************************************************** *
//author Aritra Dhar																* *
//MT12004																			* *
//M.TECH CSE																		* * 
//INFORMATION SECURITY																* *
//IIIT-Delhi																		* *	 
//---------------------------------------------------------------------------------	* *																				* *
/////////////////////////////////////////////////									* *
//The program will do the following::::        //									* *
//1.List probability factors based on  		   //									* *
//	correlation signature          		       //									* *
//				   							   //									* *	 
//				   							   //									* *
/////////////////////////////////////////////////									* *
//version 1.0																		* *
//*********************************************************************************** * 
//*************************************************************************************

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;


public class ProbabilityList 
{
	public static int test_char=26;
	public static Character [] element={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	public static Integer dataset=40;  //1-10
	public static int[][] list_pos=new int[test_char][dataset]; 
	public static String[] fname=new String[test_char];
	
	public static float[][] outer_prob=new float[test_char][test_char];   //pos present in a char or not
	public static float[][] inner_prob=new float[test_char][test_char];  //pos prob in side a char file
	//Each pos value will have probability associated with each possible key press
	public static float[][] prob=new float[test_char][test_char];        //inner*outer acc. to Bayes' Theorem
	
	public static void list_pos() throws IOException 
	{
		int j=0;
		for(int i=0;i<test_char;i++)
		{
			j=0;
			fname[i]="Corr_"+element[i]+".txt";
			FileInputStream in=new FileInputStream(fname[i]);
			@SuppressWarnings("resource")
			BufferedReader br=new BufferedReader(new InputStreamReader(new DataInputStream(in)));		
			String s;
			while((s=br.readLine())!=null)
			{
				list_pos[i][j]=Integer.parseInt(s);
				j++;
			}
		}
		
	}
	
	public static void resetArray(float[][] a)
	{
		for(int i=0;i<test_char;i++)
		{
			for(int j=0;j<test_char;j++)
			a[i][j]=0;
		}
	}
	
	public static void make_prob_list_o()
	{
		ProbabilityList.resetArray(outer_prob);
		int flag=0;
		int i,t,j;
		for(i=0;i<test_char;i++)
		{
			for(t=0;t<test_char;t++)
			{
				flag=0;
				for(j=0;j<dataset;j++)
				{
					if(i!=list_pos[t][j])
						continue;
					if(i==list_pos[t][j])
					{
						flag=1;
					}				
				}
				if(flag==1)
					outer_prob[i][t]++;
			}
		}
		float[] con=new float[test_char];
		
		for(i=0;i<test_char;i++)
		{
			for(j=0;j<test_char;j++)
			{
				con[i]=con[i]+outer_prob[i][j];
			}
			//System.out.println(con[i]);
		}
		
		for(i=0;i<test_char;i++)
		{
			for(j=0;j<test_char;j++)
			{
				outer_prob[i][j]=(float) outer_prob[i][j]/con[i];
			}
		}
		
		/*for(i=0;i<test_char;i++)
		{
			for(t=0;t<test_char;t++)
			{
				System.out.println(i+" "+outer_prob[i][t]);
			}
			System.out.println("");
		}*/
		//fw.close();
		
		for(i=0;i<test_char;i++)
		{
			int count_i=0;
			for(j=0;j<dataset;j++)
			{
				if(j==i)
				{
					count_i++;
				}
			}
		}
	}
	
	public static void make_prob_list_i()
	{
		ProbabilityList.resetArray(inner_prob);
		int i,j,k;
		for(i=0;i<test_char;i++)
		{
			for(j=0;j<dataset;j++)
			{
				(inner_prob[i][list_pos[i][j]])++;
			}
		}
		
		for(i=0;i<test_char;i++)
		{
			for(j=0;j<test_char;j++)
			{
				inner_prob[i][j]=(float) inner_prob[i][j]/dataset;
			}	
		}
		
		/*for(i=0;i<test_char;i++)
		{
			for(j=0;j<test_char;j++)
			{
				System.out.println(i+" "+inner_prob[i][j]);
			}
			System.out.println("");
		}*/
	}
	
	public static void setBayes_Prob()  //
	{
		int i,j,k;
		for(i=0;i<test_char;i++)
		{
			for(j=0;j<test_char;j++)
			{
				//prob[i][j]=(float) inner_prob[i][j]*outer_prob[i][j];
				//prob[i][j]=(float) outer_prob[i][j] * inner_prob[j][i];
				System.out.println("(i) "+i+" "+j+" "+inner_prob[i][j]+"  "+outer_prob[i][j]+"  "+prob[i][j]);
			}
			System.out.println("");
		}
	}
	
	public static void main(String[] args) throws Exception 
	{
		ProbabilityList.list_pos();
		ProbabilityList.make_prob_list_o();
		ProbabilityList.make_prob_list_i();
		ProbabilityList.setBayes_Prob();
	}
}
