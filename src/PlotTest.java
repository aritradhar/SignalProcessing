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

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import javax.swing.*;
 
@SuppressWarnings("serial")
public class PlotTest extends JPanel {
	
	public Float[] data = new Float[10000000];
	//Float []data={(float)- 2.0,(float) 2,(float) 3,(float) 4};
	public final int PAD = 80;
	
	
    public static void setData() throws Exception
    {
    		
      //	float []arr=new float[1000000];
    	String infile=new BufferedReader(new InputStreamReader(System.in)).readLine();
    	FileInputStream in_bin_img=new FileInputStream(infile);
    	
    	BufferedReader b_freq=new BufferedReader(new InputStreamReader(new DataInputStream(in_bin_img)));
   		
    	Integer i=0;
   		String read;
   		//creating array of graph points

   		PlotTest p=new PlotTest();
   		while((read=b_freq.readLine())!=null)		//total signal length
   		{
   			//System.out.println(Float.parseFloat(read));
   			//arr[i]=Float.parseFloat(read);
   			p.data[i]=Float.parseFloat(read);
    		i++;
    	}
   		System.out.print(i);
   		
   		for(int a=0;a<i;a++)
   		{
   			//System.out.println(arr[a]);
   			System.out.println(p.data[a]);
   		}
   		
   		
    }
  
    
    public void paintComponent(Graphics g)
    {
    		super.paintComponent(g);
    		Graphics2D g2 = (Graphics2D)g;
    		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    		int w = getWidth();
    		int h = getHeight();
    		g2.drawLine(PAD, PAD, PAD, h-PAD);
    		g2.drawLine(PAD, h-PAD, w-PAD, h-PAD);
    		double xScale = (w - 2*PAD)/(data.length + 1);
    		double maxValue = 100.0;
    		double yScale = (h - 2*PAD)/maxValue;
    		// The origin location.
    		int x0 = PAD;
    		int y0 = h-PAD;
    		g2.setPaint(Color.red);
    		for(int j = 0; j < data.length; j++)
    		{
            int x = x0 + (int)(xScale * (j+1));
            int y = y0 - (int)(yScale * data[j]);
            g2.fillOval(x-2, y-2, 4, 4);
    		}

    }
 
 
    public static void main(String[] args) throws Exception {
    	
    	PlotTest.setData();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new PlotTest());
        f.setSize(600,600);
        f.setLocation(200,100);
        f.setVisible(true);
    }
}
