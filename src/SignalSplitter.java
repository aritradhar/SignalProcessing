import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class SignalSplitter 
{
	public static void main(String[] args) throws IOException
    {
		System.out.println("Enter input file name :: ");
		String infile=new BufferedReader(new InputStreamReader(System.in)).readLine();
		FileInputStream in_bin_img=new FileInputStream(infile);
		BufferedReader b_freq=new BufferedReader(new InputStreamReader(new DataInputStream(in_bin_img)));
		String read;
		int count=0;
		int i=0;

		//creating array of graph points
		i=0;
		while((read=b_freq.readLine())!=null)		//total signal length
		{
			//arr[i]=Float.parseFloat(read);
			i++;
		}
		count=i;
		b_freq.close();
		in_bin_img.close();
		double []arr=new double[count];
		//System.out.print(count);
		
		FileInputStream f_s=new FileInputStream(infile);
		BufferedReader b_freq1=new BufferedReader(new InputStreamReader(new DataInputStream(f_s)));
		i=0;
		//feed signal to an array
		while((read=b_freq1.readLine())!=null)		//total signal length
		{
			arr[i]=Float.parseFloat(read);
			i++;
		}
	  // System.out.println(i);
		
		//detect peaks
		int flag1=0;
		int j=0;
		int []peaks_pos=new int[9000000];				//peaks position (x-axis)
		double []peaks_val=new double[9000000];			//peaks value (y-axis)
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
		int peak_count=j;     //total no of peaks
		System.out.println("peak count:"+j);
		//System.out.println(peaks_val[1]);
		int sig_count=0;
		//detect key press -- detect sequence of peaks
		flag1=0;
		for(i=0;i<j-1;i++)
		{
			//if(peaks_val[i]<0.03)
				//continue;
			if(peaks_val[i]<peaks_val[i+1])
				flag1=1;
			if((flag1==1||flag1==0) && peaks_val[i]>peaks_val[i+1] )
			{
				{
					flag1=2;   						//peaks found
					//peaks_pos[j]=i;					//position of the peaks at x axis
					//peaks_val[j]=arr[i];			//position of the peaks at y axis
					//sig_count++;
					continue;
					//if(peaks_val[i+1]<peaks_val[i+])
				}
			}
			if(flag1==2)
			{
				if(peaks_val[i]<peaks_val[i+1])
				{
					flag1=0;
					sig_count++;
				}
			}
		}
		
		System.out.println("press:"+sig_count);
		
		/*int p=0;
		for(i=0;i<peak_count;i++)
		{
			if(peaks_val[i]>0.1)
				p++;
		}
		System.out.println(p);*/
	}

}
