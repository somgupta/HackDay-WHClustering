package Cluster;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.clusterers.SimpleKMeans;
import weka.core.DistanceFunction;
import weka.core.Instance;
import weka.core.Instances;
 
public class Cluster {
 
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
	public static void main(String[] args) throws Exception {
		SimpleKMeans kmeans = new SimpleKMeans();
 
		kmeans.setSeed(10);
 
		//important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(13);
		System.out.println(kmeans.distanceFunctionTipText());
		BufferedReader datafile = readDataFile(System.getProperty("user.dir")+"/one.arff"); 
		Instances data = new Instances(datafile);
 
	
		
		kmeans.buildClusterer(data);
		
		// This array returns the cluster number (starting with 0) for each instance
		// The array has as many elements as the number of instances
		
		Instances ii = kmeans.getClusterCentroids();
		for(int i=0;i<3;i++) {
			System.out.println(ii.instance(i));	
		}
		
		for(int i = 0; i < data.numInstances(); i++)
		{	
			
			{DistanceFunction d = kmeans.getDistanceFunction();
			System.out.println("************************data_instance: "+data.instance(i)+"**************************************");
			for(int j=0;j<3;j++) {
		
			System.out.println("Distance from Centroid" +j+": = "+ "Centroid instance:"+ ii.instance(j) +"::::"+ d.distance(data.instance(i), ii.instance(j)));
			}
			System.out.println("**************************************************************");
		}
			}
//		
		int[] assignments = kmeans.getAssignments();
		int i=0;
//		for(int clusterNum : assignments) {
//			
//		    System.out.printf("Instance %d -> Cluster %d \n", i, clusterNum);
//		    i++;
//		}
	}
}