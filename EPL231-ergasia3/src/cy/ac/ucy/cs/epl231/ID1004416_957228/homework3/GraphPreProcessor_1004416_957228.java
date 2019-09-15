/*@author Andreas Iacovou
 *@author Andreas Panteli 
 *program 1 
 */

package cy.ac.ucy.cs.epl231.ID1004416_957228.homework3;

import java.io.*;
import java.util.Scanner;
import java.math.*;


public class GraphPreProcessor_1004416_957228 {
	
	/* calculates the distances between two places
	 * @param lat1 the lattitude of 1 
	 * @param lon1 the longitude of 1
	 * @param lat2 the lattitude of 2
	 * @param lon2 the longitude of 2
	 * 
	 */
	public static double SphericalLaw(double lat1,double lon1, double lat2, double lon2){
		
		int R = 6371000;
		double d = Math.acos(Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
				Math.cos(Math.toRadians(lon2 - lon1))) * R;
		d= d / 1000;
		return d;
		
//		int R = 6371;
//		double d = Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1)) * R;
//		d = d/1000;
//		
//		return d;
	}
	
	public static void createCSV(Graph_1004416_957228 graph) throws IOException {
		File file2 = new File("file2.csv");
		
			FileWriter writer = new FileWriter(file2,false); 
			BufferedWriter buffer = new BufferedWriter(writer);
			buffer.write(graph.CsvString());
			buffer.flush();
			buffer.close();
		
	}
	
	/*calculates the minimum number in the array
	 * @param distances[]
	 */
	public static int findMin(double distances[]){
		double min = distances[0];
		int minIndex = 0;
		for(int i=0; i<distances.length; i++){
			if(min>distances[i]){
				min = distances[i];
				minIndex = i;
			}
		}
		return minIndex;
		
		
	}
	
	//The main method of the program
	public static void main(String[] args) throws IOException{
		File file = new File("cy.txt");
		if(!file.exists()){//if file does not exist print error
			System.out.println("the map file does not exist");
			return;
		}
		
		
		String Places[];
		double Coordinates[][];
		
		/*
		 * Find the length of the arrays
		 */
		int numofLines = 0;

		Scanner sc = new Scanner(file);
		String line = "";

		line = sc.nextLine();
		while (sc.hasNextLine()) {
			numofLines++;
			sc.nextLine();
		}

		sc.close();

		Places = new String[numofLines];
		for(int i=0; i<numofLines; i++) Places[i] = "";
		Coordinates = new double[numofLines][2];
		
		File file1 = new File("cy.txt");
		/*
		 * Create the arrays that will hold the places and their coordinates
		 */
		String placeName = "";
		double lat = 0; double lon = 0; boolean dontinput = false;
		Scanner s2 = new Scanner(file1);
		 numofLines = 0;
		s2.nextLine();
		while (s2.hasNextLine()) {
			String ln[] = s2.nextLine().split("\t");
				String s;
				s = ln[3];
				double value = Double.parseDouble(s);
				lat = value;
				s = ln[4];
				value = Double.parseDouble(s);
				lon = value;
				s = ln[25];	
				placeName = s;
			
			
				
			for(int j=0; j<Places.length; j++){
				//if(Places[j]!=""){
					double dist = SphericalLaw(lat,lon,Coordinates[j][0],Coordinates[j][1]);
					if(dist <0.5){
						dontinput = true;
						break;
					}
				//}
			}
			if(!dontinput){
				Coordinates[numofLines][0] = lat; 
				Coordinates[numofLines][1] = lon;
				Places[numofLines] = placeName;
				numofLines++;
			}
			
			dontinput=false;
		}
		s2.close();
		
		
		String Places1[] = new String[numofLines];
		double Coordinates1[][] = new double[numofLines][2];
		for(int i=0; i<Places1.length; i++){
			Places1[i]=Places[i];
		}
		for(int i=0; i<Places1.length; i++){
			for(int j=0; j<2; j++){
				Coordinates1[i][j] = Coordinates[i][j];
			}
		}
		
		//Create and initalize the graph
		Graph_1004416_957228 graph = new Graph_1004416_957228(Places1);
		
		//Calculate the distances and create the graph
		double distances[] = new double[numofLines];
		for(int i=0; i<distances.length; i++){
			for(int j=0; j<distances.length; j++){
				distances[j] = SphericalLaw(Coordinates1[i][0],Coordinates1[i][1],Coordinates1[j][0],Coordinates1[j][1]);         
			}
			
			for(int j=0; j<distances.length; j++){
				if(distances[j]<0.5) distances[j] = Integer.MAX_VALUE;
			}
			
			for( int j=0; j<5; j++){
				int minIndex = findMin(distances);
				graph.insert(Places1[i], distances[minIndex], Places1[minIndex]);
				distances[minIndex] = Integer.MAX_VALUE;
			}
			
			for(int j=0; j<distances.length; j++){
				distances[j]=0;
			}
			
		}
		
		
		//graph.print();
		
		
		//create a .bin file of the graph
		File f= new File("file1.bin");
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(graph);
		oos.close();
		
		// create a csv file
		createCSV(graph);
		
	}

}
