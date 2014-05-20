//*************************************************************************************
//*********************************************************************************** *
//author Aritra Dhar																* *
//MT12004																			* *
//M.TECH CSE																		* * 
//INFORMATION SECURITY																* *
//IIIT-Delhi																		* *
//																					* *
//*********************************************************************************** *
//*************************************************************************************
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class FFTPeaks 
{

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception 
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter character:: ");
		String input=br.readLine();
		File outfile=new File("C:\\Users\\Aritra\\Desktop\\audio processing\\codes\\group-"+input+".txt");
		File outfile_sort=new File("C:\\Users\\Aritra\\Desktop\\audio processing\\codes\\groupsort-"+input+".txt");
		outfile.createNewFile();
		outfile_sort.createNewFile();
		FileWriter fwrite=new FileWriter(outfile);
		FileWriter fwrite_sort=new FileWriter(outfile_sort);
		FileInputStream in_bin_img=new FileInputStream("C:\\Users\\Aritra\\Desktop\\audio processing\\codes\\savefile.txt");
		BufferedReader b_freq=new BufferedReader(new InputStreamReader(new DataInputStream(in_bin_img)));
		@SuppressWarnings("unused")
		String read;
		
		int count=0;
		int i=0;
		while((read=b_freq.readLine())!=null)
		{
			//arr[i]=Float.parseFloat(read);
			count++;
		}
		float []arr=new float[count];
		//peaks
		
		//System.out.print(i);
		int j = 0;
		int countgroup=count/4;
		float []grouparr=new float[countgroup];
		
		for(i=0;i<count;i=i+4)
		{
			grouparr[j]=arr[i]+arr[i+1]+arr[i+2]+arr[i+3];
			fwrite.append(grouparr[j]+" ");
			j++;
			
		}
		System.out.print(j);
		//sort
		float temp=0;
		int n=countgroup;
		for (i = 0; i < n-1 ; i++)
		{
			for (j = 0; j <n-1-i; j++)
			{
				if (grouparr[j] > grouparr[j+1])
				{
					temp = grouparr[j];
					grouparr[j] = grouparr[j+1];
					grouparr[j+1] = temp;
				}
			}
		}
		for (i = 0; i < n ; i++)
		{
			fwrite_sort.append(grouparr[i]+" ");
		}
		fwrite.close();
		fwrite_sort.close();
		System.out.print("Generated successfully");
		//System.out.print(j);
	}

}
