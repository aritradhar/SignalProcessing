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
//1.correlation analyzer  					   //									* *
//2.correlation based signature		  		   //									* *
//3.use relation position					   //									* *	 
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
import java.util.Stack;

public class SmartCorelation 
{
	public static int start=97; //a
	public static int end=122;  //z
	public static int test_char=26,j=0;;
	public static Character [] element={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	public static int dataset=40;  //1-10
	
	public static String[][] database_n=new String[(end-start+1)][dataset];
	public static Integer f_c=0,d_c=0;
	public static int selector;
	public static int[] point_a=new int[dataset];
	public static int[] point_a_ind=new int[test_char];
	
	public static Double[][][] database=new Double[(end-start+1)][(dataset)][(end-start+1)];
	
	public static void BubbleSort(int[] a)
	{
		int temp;
		for (int i = 0; i < dataset ; i++)
		{
			for (int j = 0; j <dataset-1-i; j++)
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
	
	public static void FileNameGen()
	{
		while((f_c*d_c)!=((end-start)*(dataset-1)))
		{
			Integer d_c_a=d_c+1;
			database_n[f_c][d_c]=element[f_c].toString()+d_c_a.toString()+".txt";
			
			//System.out.println(database_n[f_c][d_c]);			
			
			d_c++;
			if(d_c!=0 && d_c%dataset==0)
			{
				d_c=(d_c%10);
				f_c++;
			}
		}
		Integer tm=dataset;
		database_n[(end-start)][dataset-1]=element[(end-start)].toString()+tm.toString()+".txt";
		//System.out.println(database_n[(end-start)][dataset-1]);
	}
	
	public static void DataBaseToMemory() throws NumberFormatException, IOException
	{
	
		
		while(!((f_c==26) && (d_c==0)))
		{
			String fname="C:\\Users\\Aritra\\Desktop\\audio processing\\Audio samples\\Database_Final\\"+database_n[f_c][d_c];
			
			FileInputStream in=new FileInputStream(fname);
			@SuppressWarnings("resource")
			BufferedReader b_in_tst=new BufferedReader(new InputStreamReader(new DataInputStream(in)));		
			String s;
			j=0;
			while((s=b_in_tst.readLine())!=null)
			{
				database[f_c][d_c][j]=Double.parseDouble(s);
				j++;
			}
			
			
			//System.out.println("-------\n"+database_n[f_c][d_c]+"\n-------");
			//for(int i=0;i<j;i++)
				//System.out.println(database[f_c][d_c][i]);
			
			d_c++;
			if(d_c!=0 && d_c%dataset==0)
			{
				d_c=0;
				f_c++;
			}
		}
	}
	
	public static void dataset_a()
	{
		  int cc=0;
	        
	        Double ind_d_a;
	        //selector=0;                  //change this value for different key
			for(int a=0;a<dataset;a++)
			{
				ind_d_a=database[selector][a][selector];
				
				for(int b=0;b<26;b++)
				{
					if(b==selector)             
						continue;
					if(database[selector][a][b]>ind_d_a)
						cc++;
				}
				point_a[a]=cc;
				//System.out.println(point_a[a]);
				cc=0;
			}
	}
		
	public static void main(String[] args) throws IOException
	{		
		//database load to memory
		SmartCorelation.FileNameGen();
		f_c=0;
		d_c=0;
		SmartCorelation.DataBaseToMemory();
	
		
		//define database characteristics 
		//a
        
		for(int t=0;t<test_char;t++)
		{
			selector=t;
			SmartCorelation.dataset_a();
			SmartCorelation.BubbleSort(point_a);
		
			for(int a=0;a<dataset;a++)
			{
				System.out.println(point_a[a]);
			}
		
			FileWriter fw=new FileWriter("Corr_"+element[selector].toString()+".txt");
			for(int i=0;i<dataset;i++)
			{
				fw.append(point_a[i]+"\n");
			}
			fw.close();
		}
		//test case file
		/*System.out.print("Input Test Case File : ");
		String fname=new BufferedReader(new InputStreamReader(System.in)).readLine();
		
		FileInputStream in=new FileInputStream(fname);
		BufferedReader b_in_tst=new BufferedReader(new InputStreamReader(new DataInputStream(in)));
		
		Double[] corr_arr=new Double[27];
		String in_corr=new String();
		int i=0;
		while((in_corr=b_in_tst.readLine())!=null)
		{
			corr_arr[i]=Double.parseDouble(in_corr);
			i++;
		}
		
		//characteristics of a
		Double ind=corr_arr[0];

		int point=0;
		
		int c=0;
		for(j=1;j<i;j++)
		{
			if(corr_arr[j]>ind)
				c++;
		}
		System.out.println(point);
		*/
	}

}
