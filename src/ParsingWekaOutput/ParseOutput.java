package ParsingWekaOutput;
import java.io.BufferedReader;
import java.io.FileReader;
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
	
		int currStart = startLine;
		double[][] outputArr = new double[TotalCMSVerticals+1][NumberOfClusters+1];
		double[] totalofAllVertical = new double[NumberOfClusters+1];
		int x =0;
		while ((sCurrentLine = br.readLine()) != null && currStart < startLine +2+ TotalCMSVerticals) {
//			System.out.println(currStart+",,"+(startLine + TotalCMSVerticals)+",,"+sCurrentLine);
			if(currStart >= startLine+2)
			{	StringTokenizer st = new StringTokenizer(sCurrentLine);
//				for(int x = 0; x < TotalCMSVerticals; x++)
//				{
				st.nextToken();
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
		
		
	}
}
