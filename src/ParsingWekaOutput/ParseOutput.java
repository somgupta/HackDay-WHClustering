package ParsingWekaOutput;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.*;
 
public class ParseOutput {
	
	private double [][] getClusterAssignmentWithWeightage(double[][] outputFromWeka, int ClusterSize, int NumberOfVerticals)
	{
		double analyser[][] = new double[NumberOfVerticals][ClusterSize];
		
		for(int i = 0; i < NumberOfVerticals; i++)
		{	int maxForVerticalIndex = -1;
			double maxForVerticalValue = Double.MIN_NORMAL;
			double totalValueForVerticalForAllCluster = 0.0;
			for(int j =1; j<ClusterSize+1;j++)
			{
				totalValueForVerticalForAllCluster+=outputFromWeka[i][j];
				if(maxForVerticalValue < outputFromWeka[i][j])
				{
					maxForVerticalIndex = j;
				}
				
			}
			analyser[i][0] = maxForVerticalIndex;
			for(int j =1; j<ClusterSize+1;j++)
			{
				analyser[i][j]=outputFromWeka[i][j]/totalValueForVerticalForAllCluster;
				
			}
		}
		print2DArray(analyser,NumberOfVerticals,ClusterSize+1);
		return analyser;
		
	}
	private void print2DArray(double arr[][], int rows, int columns)
	{
		for(int i = 0; i< rows; i++)
		{
			for(int j = 0; j< columns; j++){
				
				System.out.print(arr[i][j]+" , ");
			}
			System.out.println();
		}
	}
	public static void main(String s[]) throws Exception{
		String sCurrentLine;

		int NumberOfClusters = 5;
		int TotalCMSVerticals = 531;
		BufferedReader br = null;
		
		br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/Output_som1"));

		int startLine = 1; 
		while ((sCurrentLine = br.readLine()) != null) {
			if(sCurrentLine.contains("Full Data"))
				{
				System.out.println(sCurrentLine);
				break;
			}
			startLine++;
			
		}
		
	
		int currStart = startLine;
		double[][] outputArr = new double[TotalCMSVerticals+1][NumberOfClusters+1];
		double[] rowArr = new double[NumberOfClusters+1];
		
		double[] totalofAllVertical = new double[NumberOfClusters+1];
		String[] cmsVerticals = new String[TotalCMSVerticals];
		int x =0;
		while ((sCurrentLine = br.readLine()) != null && currStart < startLine +2+ TotalCMSVerticals) {
			if(currStart >= startLine+2)
			{	StringTokenizer st = new StringTokenizer(sCurrentLine);
				cmsVerticals[x] = st.nextToken();
				Double totalforCurrVertical = Double.parseDouble(st.nextToken());
				totalofAllVertical[0]+=totalforCurrVertical;
				outputArr[x][0] =totalforCurrVertical;
				
				double max = 0;
				for(int y = 1; y < NumberOfClusters+1; y++){
					String t = st.nextToken();
					totalofAllVertical[y]+=Double.parseDouble(t);
					rowArr[y] = Double.parseDouble(t);
					if(rowArr[y]>max)
						max = rowArr[y];
				}
				
				for(int y = 1; y < NumberOfClusters+1; y++){
					if(rowArr[y] == max)
						outputArr[x][y] = max;
					else
						outputArr[x][y] = 0;
				}
				x++;
			}

			currStart++;
		}
		br.close();
		int y = 0;
		for(Double r:totalofAllVertical){
			outputArr[TotalCMSVerticals][y] = r;
			y++;
		}
		
		createJson(NumberOfClusters, TotalCMSVerticals, outputArr, cmsVerticals, totalofAllVertical);
	}
	private static void createJson(int NumberOfClusters, int TotalCMSVerticals,
			double[][] outputArr, String[] cmsVerticals, double[] totalofAllVertical) {
		FileWriter fileWriter = null;
		
		try{
		    
		    fileWriter = new FileWriter("ClusterMAPData.json");
		
		    fileWriter.append("{\n");
			fileWriter.append("\"name\": \"MAIN START\",\n");
			fileWriter.append("\"children\": [\n");
			
		for(int ivar = 1; ivar < NumberOfClusters+1;ivar++)
		{
			//Print name of cluster
			fileWriter.append("{\n");
			fileWriter.append("\"name\": \"Cluster-"+ivar+"\",\n");
			fileWriter.append("\"children\": [\n");
			System.out.println("CHECK2");
			for(int jvar = 0; jvar < TotalCMSVerticals; jvar++)
			{
				//Print name of Vertical
				//double value = (outputArr[jvar][0]*outputArr[jvar][ivar]*100)/(totalofAllVertical[0]*totalofAllVertical[ivar]);
				double value = (outputArr[jvar][ivar]*100);

//				if(ivar == NumberOfClusters)
//					System.out.println(cmsVerticals[jvar]+" "+outputArr[jvar][0]+" "+outputArr[jvar][ivar]+" "+totalofAllVertical[0]+" "+totalofAllVertical[ivar]);
				fileWriter.append("{\"name\": \""+cmsVerticals[jvar]+"\", \"size\": "+value);
				if(jvar < TotalCMSVerticals-1)
					fileWriter.append(" },\n");
				else
					fileWriter.append(" }\n]\n");
			}
			if(ivar < NumberOfClusters)
				fileWriter.append("},\n");
			else
				fileWriter.append("}\n");
		}
		fileWriter.append("]\n}\n");
		}
		catch(Exception e){
	    	e.printStackTrace();
	    	System.out.println("AAAA");
	    }
	    finally
	    {
	    	try {
	    		
	    		                fileWriter.flush();
	    		
	    		                fileWriter.close();
	    		
	    		            } catch (IOException e) {
	    		
	    		                System.out.println("Error while flushing/closing fileWriter !!!");
	        		
	        		                e.printStackTrace();
	  
	        		            }

	        }
	}
}
