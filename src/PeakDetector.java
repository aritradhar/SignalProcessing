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
//1.Detect signal peaks						   //									* *
//2.Bring out top n peaks					   //									* *
//3.bunch peaks								   //									* *	 
//4.Identify signal signature				   //									* *
/////////////////////////////////////////////////									* *
//Use both for test-case and real value												* *
//version 1.0																		* *
//*********************************************************************************** * 
//*************************************************************************************

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DecimalFormat;


public class PeakDetector {

	@SuppressWarnings({"resource" })
	public static void main(String[] args) throws Exception 
	{
		/*
		float fx = 125.254125145645f;
		DecimalFormat form = new DecimalFormat("0.00");
		System.out.println(form.format(fx));
		 */
		System.out.println("Enter input file name :: ");
		//String infile=new BufferedReader(new InputStreamReader(System.in)).readLine();
		//FileInputStream in_bin_img=new FileInputStream(infile);
		FileInputStream in_bin_img=new FileInputStream("C:\\Users\\Aritra\\Desktop\\audio processing\\Audio samples\\new reading\\wavs\\z\\voice148_fft.txt");
		BufferedReader b_freq=new BufferedReader(new InputStreamReader(new DataInputStream(in_bin_img)));
		String read;
		System.out.println("Enter output file name :: ");
		//String outfile=new BufferedReader(new InputStreamReader(System.in)).readLine();
		FileWriter out_bunch=new FileWriter(new File("C:\\Users\\Aritra\\Desktop\\audio processing\\Audio samples\\new reading\\wavs\\z\\z6.txt"));
		//FileWriter out_bunch=new FileWriter(new File(outfile));
		int count=0;
		int i=0;

		float []arr=new float[10000000];
		//creating array of graph points
		i=0;
		while((read=b_freq.readLine())!=null)		//total signal length
		{
			arr[i]=Float.parseFloat(read);
			i++;
		}
		count=i;                                     //for the fft count is needed to be taken
		//System.out.println(arr[0]);				 //as count/2 to take only the 1st half
		
		//detect peaks
		int flag1=0;
		int j=0;
		//System.out.println(count);
		int []peaks_pos=new int[90000];				//peaks position (x-axis)
		float []peaks_val=new float[90000];			//peaks value (y-axis)
		for(i=0;i<count-1;i++)
		{
			if(arr[i]<0)
				continue;
			if(arr[i]<arr[i+1])  				//not a peak
				flag1=1;
			
			if((flag1==1||flag1==0) && arr[i]>arr[i+1])
			{
				flag1=2;   						//peaks found
				peaks_pos[j]=i;					//position of the peaks at x axis
				peaks_val[j]=arr[i];			//position of the peaks at y axis
				j++;
			}
		}
		@SuppressWarnings("unused")
		int peak_count=j;   //no of peaks
		//debug
		//System.out.print(j);
		/*for(i=0;i<j;i++)
		{
			System.out.print(peaks_val[i]);
			System.out.println("      "+peaks_pos[i]);
		}*/
		
		//take out top n peaks
		int n=10;                            //top n peaks
		float []top_peaks=new float[n];		 //top n peaks value
		int []top_peaks_pos=new int[n];		 //top n peaks position
		float temp=0;
		int pos=0,pk_pos=0;
		float []peaks_dup=new float[count];
		for(i=0;i<count;i++)				//back up the peaks array
		{
			peaks_dup[i]=peaks_val[i];
		}
		
		//debug
		/*for(i=0;i<n;i++)
		{
			System.out.println(peaks_pos[i]);
		}*/
		//System.out.println(peaks_pos[0]);
		
		for(i=0;i<n;i++)					//accessing the duplicate peaks array
		{
			temp=peaks_dup[0];
			//debug
			//System.out.println(temp);
			pos=peaks_pos[0];
			pk_pos=0;
			for(j=1;j<peak_count;j++)
			{
				if(peaks_dup[j]==-1.0)
					continue;
				if(temp<peaks_dup[j])
				{
					temp=peaks_dup[j];
					pos=peaks_pos[j];
					pk_pos=j;
					//debug 
					//if(i==0)
						//System.out.println(pos);
				}
				
				
			}
			peaks_dup[pk_pos]=(float) -1.0;
			top_peaks[i]=temp;
			top_peaks_pos[i]=pos;
			
		}
		//debug
		for(i=0;i<n;i++)
		{
			System.out.print(top_peaks[i]);
			System.out.println("          "+top_peaks_pos[i]);
		}
		
		//top n peaks completed
		
		//peak sort  not needed
		/*float temp;
		for (i = 0; i < count-1 ; i++)
		{
			for (j = 0; j <count-1-i; j++)
			{
				if (peaks_val[j] > peaks_val[j+1])
				{
					temp = peaks_val[j];
					peaks_val[j] = peaks_val[j+1];
					peaks_val[j+1] = temp;
				}
			}
		}*/
		
		//take out the frequency bunches
		int bunch_count,bunch_size=9;
		
		/*if(peak_count%bunch_size==0)
			bunch_count=peak_count/bunch_size;
		else
			bunch_count=peak_count/bunch_size+1;
		*/
		
		bunch_count=n;							//total n many top peaks will give n many bunches
		float [][]freq_bunch=new float[bunch_count][bunch_size];
		int lo,hi;
		
		for(i=0;i<n;i++)
		{
			for(j=0;j<bunch_size;j++)
			{
				freq_bunch[i][j]=-9999;      	//initialization
			}
		}
		
		for(i=0;i<n;i++)
		{
			freq_bunch[i][(bunch_size-1)/2]=top_peaks[i];
			lo=hi=0;
			for(j=0;j<(bunch_size-1)/2;j++)
			{
				if(top_peaks_pos[i]-((bunch_size-1)/2)>=0)		//check for the array index lower bound
				{
					freq_bunch[i][((bunch_size-1)/2)-(j+1)]=arr[top_peaks_pos[i]-(j+1)];		 //left side
				}
				else
				{
					if(lo!=top_peaks_pos[i])
					{
						freq_bunch[i][((bunch_size-1)/2)-(j+1)]=arr[top_peaks_pos[i]-(j+1)];
						lo++;
						//debug
						//System.out.print(lo);	
					}
					//debug
					//if(i==0)
						//System.out.println("Enter");
					
				}
				if(top_peaks_pos[i]+((bunch_size-1)/2)<=count)		//check for the array index upper bound
				{
					freq_bunch[i][j+1+((bunch_size-1)/2)]=arr[top_peaks_pos[i]+(j+1)];		     //right side
				}
				else
				{
					if(hi!=count-top_peaks_pos[i])
					{
						freq_bunch[i][j+1+((bunch_size-1)/2)]=arr[top_peaks_pos[i]+(j+1)];
						hi++;
					}
				}
			}
			
		}
		
		DecimalFormat form = new DecimalFormat("0.000000000000000000");
		
		for(i=0;i<n;i++)
		{
			for(j=0;j<bunch_size;j++)
			{
				if(freq_bunch[i][j]!=-9999)
				{
					System.out.println(form.format(freq_bunch[i][j]));
					
					if((i==n-1) && (j==bunch_size-1))
						out_bunch.append(form.format(freq_bunch[i][j]));
					//if(i!=(n-1) && (j!=bunch_size-1))
					else
					out_bunch.append(form.format(freq_bunch[i][j])+"\n");
					//else
					
				}
			}
			//System.out.println("--------------");
			//out_bunch.append("---------------");
			//if(i!=n-1)
			//out_bunch.append("\n");
		}
		out_bunch.close();
		System.out.print("Terminated");
		
	}

}
