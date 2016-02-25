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
import java.lang.*;

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
        HashSet<String> setofVerticals = new HashSet<String>();
        
        //read comma separated file line by line
        while ((line = br.readLine()) != null) {

            //use comma as token separator
            st = new StringTokenizer(line, ",");
            
            while (st.hasMoreTokens()) {
                tokenNumber++;
                	
                
                String key = st.nextToken();
                String value = st.nextToken();
                Set<String> s = new HashSet<String>();
                if(hm.containsKey(key)) {
                    s = hm.get(key);
                }
                s.add(value);
                hm.put(key, s);
                
                setofVerticals.add(value);
                
                if(lineNumber%1000==0)
                	System.out.println(hm.get(key));
           
                lineNumber++;
            }

         
        }
        
        System.out.println();
        String arrofVerticals[] = new String[setofVerticals.size()];
        int ui = 0;
	    for(String t: setofVerticals)
	    {
		    arrofVerticals[ui] = t;	
		    ui++;
	    }
	    Arrays.sort(arrofVerticals);
	    
//    for(String p:arr)
//    {
//    	System.out.println(p);
//    System.out.println(hm.get(p));}

    FileWriter fileWriter = null;
    try{
    
    fileWriter = new FileWriter("sampleData.arff");
    fileWriter.append("@relation KMeans\n\n");
    for(String abcd2 : arrofVerticals){
    	fileWriter.append("@attribute "+abcd2+" NUMERIC\n");
    }
    fileWriter.append("@data\n");
    for(String OrderIDRow : hm.keySet())
    {
    	HashSet<String> ordersSet = (HashSet<String>) hm.get(OrderIDRow);
    	String currentKeySet = OrderIDRow;
    	
    	String output ="";
    	int y = 0;
    	
    	Set<String> currSet = (HashSet<String>) (((HashSet<String>) hm.get(OrderIDRow)).clone());
    	
    	for(String cmsVerticalIn : arrofVerticals)
    	{   
    		if (y>0)
    		{
    			output += ",";
    		}
    		y++;
    		
    		if(currSet.contains(cmsVerticalIn))
    			output = output + "1";
    		else
    			output = output + "0";
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