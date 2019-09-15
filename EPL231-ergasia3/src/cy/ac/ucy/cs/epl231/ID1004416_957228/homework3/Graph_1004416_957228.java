package cy.ac.ucy.cs.epl231.ID1004416_957228.homework3;

import java.io.Serializable;




class Neighbor implements Serializable {
    public int vertexNum;
    public Neighbor next;
    public double weight;
   
    Neighbor(int vnum, Neighbor nbr, double weight) {
            this.vertexNum = vnum;
            next = nbr;
            this.weight = weight;
    }
}

class Vertex implements Serializable{
    String name;
    Neighbor adjList;
    Vertex(String name, Neighbor neighbors) {
            this.name = name;
            this.adjList = neighbors;
            
    }
}


public class Graph_1004416_957228 implements Serializable {
	
	Vertex[] adjLists;
	
	public Graph_1004416_957228(String[] places){
		adjLists = new Vertex[places.length];
		for(int i=0; i<places.length; i++){
			
			adjLists[i] = new Vertex(places[i], null);
		}
		
	}
	
	public void insert(String placeFrom, double distance, String placeTo) {
         
         // read vertex names and translate to vertex numbers
         int v1 = indexForName(placeFrom);
         int v2 = indexForName(placeTo);
         
         
         for(int i=0; i<adjLists.length; i++){
        	 if(v1==i){
        		 for(Neighbor nbr=adjLists[i].adjList; nbr!=null; nbr=nbr.next){
        			 if(nbr.vertexNum==v2){
        				 return;
        			 }
        		 }
        		 
        	 }
         }
         
         
         
         // add v2 to front of v1's adjacency list and
         // add v1 to front of v2's adjacency list
         adjLists[v1].adjList = new Neighbor(v2, adjLists[v1].adjList,distance);
         adjLists[v2].adjList = new Neighbor(v1, adjLists[v2].adjList,distance);
     }
	
	 public void print() {
	        System.out.println();
	        for (int i=0; i < adjLists.length; i++) {
	            System.out.print(adjLists[i].name + ":");
	            for (Neighbor nbr=adjLists[i].adjList; nbr != null; nbr=nbr.next) {
	                System.out.print(" Distance from " + adjLists[nbr.vertexNum].name + " is: " + nbr.weight + "km");
	                System.out.println();
	            }
	            System.out.println("\n");
	        }
	    }
	 
	 public String CsvString() {
	        String myString = "";
	        for (int i=0; i < adjLists.length; i++) {
	            myString+= (adjLists[i].name + ",");
	            for (Neighbor nbr=adjLists[i].adjList; nbr != null; nbr=nbr.next) {
	                myString += (adjLists[nbr.vertexNum].name + "," + nbr.weight + "km");
	                myString += ",";
	            }
	            myString+= "\n";
	        }
	        return myString;
	    }
	 
	 public void printVertex() {
	        System.out.println(adjLists.length);
	    }
	 
	 int indexForName(String name) {
	        for (int i=0; i < adjLists.length; i++) {
	            if (adjLists[i].name.equals(name)) {
	                return i;
	            }
	        }
	        return -1;
	  }    

}
