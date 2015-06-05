package weka.clusterers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Prog {

    public static void main(String[] args) throws IOException {

        //ArffLoader loader = new ArffLoader();ArffLoader loader1 = new ArffLoader();
        //
        //loader.setFile(new File("path_to_train.arff"));
        //loader1.setFile(new File("path_to_train.arff"));


        String line = "";
        StringTokenizer st = null;

        int lineNumber = 0;
        int tokenNumber = 0;
        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/all_data2.arff"));

        HashMap <String, Set<String>> hm = new HashMap<String, Set<String>>();
        //read comma separated file line by line
        while ((line = br.readLine()) != null) {
            lineNumber++;

            //use comma as token separator
            st = new StringTokenizer(line, ",");
            
            while (st.hasMoreTokens()) {
                tokenNumber++;
                	
                
                String value = st.nextToken();
                String key = st.nextToken();
                Set<String> s = new HashSet<String>();
                if(hm.containsKey(key)) {
                    s = hm.get(key);
                }
                s.add(value);
                hm.put(key, s);
           
            }

         
        }
        
        
        
//       int arr[][] = new int[hm.keySet().size()][hm.keySet().size()];
        System.out.println(hm.keySet().size());
        String arr[] = new String[hm.keySet().size()];
        int ui = 0;
    for(String t: hm.keySet())
    {
    arr[ui] = t;	
    ui++;
    }
    Arrays.sort(arr);
//    for(String p:arr)
//    {
//    	System.out.println(p);
//    System.out.println(hm.get(p));}
    int u= 0;
    for(String cmsVerticalOut : arr){
    	u++;
//    	if(u%100==0)
//       	{System.out.println("Key:-------------------------------- ");
//    	System.out.println("Key: "+ arr);
//    	System.out.println(hm.get(cmsVerticalOut));
//    	System.out.println("Key:-------------------------------- ");
//    }
    	}
    FileWriter fileWriter = null;
    try{
    
    fileWriter = new FileWriter("sampleData.arff");
    fileWriter.append("@relation KMeans\n\n");
    for(String abcd2 : arr){
    	fileWriter.append("@attribute "+abcd2+" NUMERIC\n");
    }
    fileWriter.append("@data\n");
    for(String cmsVerticalOut : arr)
    {
    	HashSet<String> ordersSet = (HashSet<String>) hm.get(cmsVerticalOut);
    	String currentKeySet = cmsVerticalOut;
    	
    	String output ="";
    	int y = 0;
    	for(String cmsVerticalIn : arr)
    	{   Set<String> currSet = (HashSet<String>) (((HashSet<String>) hm.get(cmsVerticalOut)).clone());
    	
    		currSet.addAll(hm.get(cmsVerticalIn));
    		if (y>0)
    		{
    			output += ",";
    		}
    		y++;
    		output = output + currSet.size();
    	}
    	
    	fileWriter.append(String.valueOf(output+"\n"));
    	
    }
    }
    catch(Exception e){
    	e.printStackTrace();
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