package ParsingWekaOutput;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
 
public class ParseOutput {
 
	public static void main(String s[]) throws Exception{
		String sCurrentLine;
		int NumberOfClusters = 35;
		int TotalCMSVerticals = 531;
		BufferedReader br = null;
		
		br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/Output_35"));
		int startLine = 1; 
		while ((sCurrentLine = br.readLine()) != null) {
			if(sCurrentLine.contains("Full Data"))
//			{ 	System.out.println(sCurrentLine);
				{
				System.out.println(sCurrentLine);
				break;
			}
			startLine++;
			
		}
		
		//br.close();
	
		int currStart = startLine;
		double[][] outputArr = new double[TotalCMSVerticals+1][NumberOfClusters+1];
		double[] totalofAllVertical = new double[NumberOfClusters+1];
		String[] cmsVerticals = new String[TotalCMSVerticals];
		int x =0;
		while ((sCurrentLine = br.readLine()) != null && currStart < startLine +2+ TotalCMSVerticals) {
//			System.out.println(currStart+",,"+(startLine + TotalCMSVerticals)+",,"+sCurrentLine);
			if(currStart >= startLine+2)
			{	StringTokenizer st = new StringTokenizer(sCurrentLine);
//				for(int x = 0; x < TotalCMSVerticals; x++)
//				{
				cmsVerticals[x] = st.nextToken();
//				System.out.println(st.nextToken());
				Double totalforCurrVertical = Double.parseDouble(st.nextToken());
				totalofAllVertical[0]+=totalforCurrVertical;
				outputArr[x][0] =totalforCurrVertical;
				
				for(int y = 1; y < NumberOfClusters; y++){
					String t = st.nextToken();
					totalofAllVertical[y]+=Double.parseDouble(t);
					outputArr[x][y] = Double.parseDouble(t);
				}
				x++;
//			}
			}

			currStart++;
		}
		int y = 0;
		for(Double r:totalofAllVertical){
			outputArr[TotalCMSVerticals][y] = r;
			y++;
		}
		
		for(int i = 0; i<TotalCMSVerticals+1;i++)
		{
			for(int j = 0; j<NumberOfClusters+1;j++)
			{	
//				if (i<10)
				{System.out.print(outputArr[i][j]+",");}
			}
//			if (i<10)
			System.out.println("");
		}
		
		System.out.println("CHECK");
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
			for(int jvar = 0; jvar < TotalCMSVerticals; jvar++)
			{
				//Print name of Vertical
				double value = (outputArr[jvar][0]*outputArr[jvar][ivar]*100)/(totalofAllVertical[0]*totalofAllVertical[ivar]);
				System.out.println(value);
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
