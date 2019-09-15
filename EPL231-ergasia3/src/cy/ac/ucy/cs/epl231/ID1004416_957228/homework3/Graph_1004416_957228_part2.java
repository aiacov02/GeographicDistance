/*@author Andreas Iacovou
 *@author Andreas Panteli 
 *program 2 
 */

package cy.ac.ucy.cs.epl231.ID1004416_957228.homework3;

import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

public class Graph_1004416_957228_part2 {
	
	/*method that takes the choise of the user
	 * @param scan our scanner
	 * @return choosen the choice of the user
	 */
	public static int choose(Scanner scan) {
		int choosen;
		do {
			System.out.println("Choose one of the following...");
			System.out.println("1. Find the close distans from 2 locations.");
			System.out.println("2. Find the N closest locations from one locations");
			System.out.println("3. Find the smallest weighted tree of the graph.");
			System.out.println("4. EXIT.");
			System.out.print("Choose from 1 - 4: ");
			choosen = scan.nextInt();
			if (choosen <= 0 && choosen >= 5) {
				System.out.println("Error: You have to insert a number from 1 to 4!");
				System.out.println("Please see the chooses and choose again!");
			}
		} while (choosen <= 0 && choosen >= 4);
		return choosen;
	}
	public static void distanceShortest(Neighbor nbor, double d[]) {
		while (nbor != null) {
			if (d[nbor.vertexNum] > nbor.weight) {
				d[nbor.vertexNum] = nbor.weight;
			}
			nbor = nbor.next;
		}
	}
	public static int closes(double d[], boolean v[], double min[], int z) {
		int i = 0, choose;
		while (v[i])
			i++;
		min[z] = d[i];
		choose = i;
		for (int j = i+1; j < d.length; j++)
			if (min[z] > d[j] && !v[j]) {
				min[z] = d[j];
				choose = i;
			}
		return choose;
	}
	/*method that calculates the diastance bewteen two locations
	 * @param g our graph
	 * @param scan the scanner 
	 */
	public static void kmof2locations(Graph_1004416_957228 g, Scanner scan) {
		int start, count, finish;
		boolean visited[] = new boolean[g.adjLists.length];
		double distance[] = new double[g.adjLists.length];
		double pathweight=0;
		double w[] = new double[g.adjLists.length];
		int paths[] = new int[g.adjLists.length];
		System.out.println("Choose a starting point and a finishing point! ");
		System.out.println("Here are your chooses: ");
		for (int i = 0; i < g.adjLists.length; i++) {
			System.out.println(i + ". " + g.adjLists[i].name);
		}
		do {
			System.out.print("Start at (0-" + (g.adjLists.length - 1) + ") : ");
			start = scan.nextInt();
			if (start < 0 || start > g.adjLists.length - 1) {
				System.out.println("Error: You have not insert a number from 0 to " + (g.adjLists.length - 1) + "!");
				System.out.println("Please choose again!");
			}
		} while (start < 0 || start > g.adjLists.length - 1);
		do {
			System.out.print("Finish at (0-" + (g.adjLists.length - 1) + ") : ");
			finish = scan.nextInt();
			if (finish != start && finish < 0 || finish > g.adjLists.length - 1) {
				System.out.println("Error: You have not insert a number from 0 to " + (g.adjLists.length - 1) + "!");
				System.out.println("       Or you have choose the same number with the starting point!");
				System.out.println("Please choose again!");
			}
		} while (finish != start && finish < 0 || finish > g.adjLists.length - 1);

		for (int i = 0; i < visited.length; i++)
			visited[i] = false;
		for (int i = 0; i < distance.length; i++)
			distance[i] = Double.POSITIVE_INFINITY;
		count = 0;
		paths[count] = start;
		visited[start] = true;
		count++;
		while (count < visited.length && !visited[finish]) {
			distanceShortest(g.adjLists[start].adjList, distance);
			start = closes(distance, visited, w, count);
			visited[start] = true;
			paths[count] = start;
			count++;
		}
		System.out.println("The closes distances from " + g.adjLists[paths[0]].name + " is:");
		for (int i = 0; i < count; i++){
			System.out.println(g.adjLists[paths[i]].name);
			pathweight += w[i];
		}
		System.out.println("And the weight is "+ pathweight);
	}
	public static void distancePath(double d[], int p[], double dN[]){
		double min;
		int z;
		z=0;
		while(d[z]!=0)
			z++;
		d[z]=Double.POSITIVE_INFINITY;
		for(int i=0; i<p.length; i++){
			min=d[0];
			z=0;
			for(int j=0; j<d.length; j++)
				if(min>d[j]){
					min=d[j];
					z=j;
				}
			d[z]=Double.POSITIVE_INFINITY;
			p[i]=z;
			dN[i]=min;
		}
	}
	/*method that prints the N closest locations to a place
	 * @param g our graph
	 * @param scan our scanner
	 */
	public static void printNlocations(Graph_1004416_957228 g, Scanner scan) {
		int location, n, start;
		boolean visited[] = new boolean[g.adjLists.length];
		double distance[] = new double[g.adjLists.length];
		Neighbor nbor;
		Queue<Integer> que = new LinkedList<Integer>();
		System.out.print("Please insert the number of plases you want to find: ");
		n = scan.nextInt();
		while (n == 0 || n > g.adjLists.length - 1) {
			System.out.println("Error: Please insert a correct number: ");
			n = scan.nextInt();
		}
		int paths[] = new int[n];
		double distanceN[] = new double[n];
		System.out.println("Choose a location point! ");
		System.out.println("Here are your chooses: ");
		for (int i = 0; i < g.adjLists.length; i++) {
			System.out.println(i + ". " + g.adjLists[i].name);
		}
		do {
			System.out.print("The locations you are choosing is: ");
			location = scan.nextInt();
			if (location < 0 || location > g.adjLists.length - 1) {
				System.out.println("Error: You have to insert a number from 0 to " + (g.adjLists.length - 1) + "!");
				System.out.println("Please choose again!");
			}
		} while (location < 0 || location > g.adjLists.length - 1);
		for (int i = 0; i < g.adjLists.length; i++)
			visited[i] = false;
		for (int i = 0; i < distance.length; i++)
			distance[i] = 0;
		visited[location]=true;
		que.add(location);
		while(!que.isEmpty()){
			start=que.poll();
			nbor=g.adjLists[start].adjList;
			while(nbor!=null){
				if(visited[nbor.vertexNum]==false){
					visited[nbor.vertexNum]=true;
					que.add(nbor.vertexNum);
					distance[nbor.vertexNum]=distance[start]+nbor.weight;
				}
				nbor=nbor.next;
			}
		}
		distancePath(distance,paths,distanceN);
		System.out.println("The " + n + " closes locations from " + g.adjLists[location].name + " are: ");
		for (int i = 0; i < n; i++)
			System.out.println(g.adjLists[paths[i]].name);
	}
	public static void tablesWeight(Graph_1004416_957228 g, int locations[][], double weights[]) {
		Neighbor nbor;
		int l = 0;
		int j, i, tmpi;
		double min, tmpd;
		for (i = 0; i < locations.length; i++) {
			locations[i][0] = -1;
			locations[i][1] = -1;
		}
		for (i = 0; i < g.adjLists.length; i++) {
			nbor = g.adjLists[i].adjList;
			while (nbor != null) {
				j = 0;
				while (j < l && ((locations[j][0] != i && locations[j][1] != nbor.vertexNum)
						|| (locations[j][0] != nbor.vertexNum && locations[j][1] != i))) {
					j++;
				}
				if (j == l && l<locations.length) {
					locations[l][0] = i;
					locations[l][1] = nbor.vertexNum;
					weights[l] = nbor.weight;
					l++;
				}
				nbor = nbor.next;
			}
		}
		for (i = 0; i < weights.length; i++) {
			min = weights[i];
			for (j = i + 1; j < weights.length; j++)
				if (min > weights[j]) {
					tmpd = weights[j];
					weights[j] = weights[i];
					weights[i] = tmpd;
					tmpi = locations[j][0];
					locations[j][0] = locations[i][0];
					locations[i][0] = tmpi;
					tmpi = locations[j][1];
					locations[j][1] = locations[i][1];
					locations[i][1] = tmpi;
				}
		}
	}

	public static void sameTrees(int t[], int i, int j) {
		int maxi, maxj, fr, to;
		maxi = 0;
		maxj = 0;
		for (int z = 0; z < t.length; z++) {
			if (t[z] == t[i])
				maxi++;
			if (t[z] == t[j])
				maxj++;
		}
		if (maxi == maxj) {
			if (i < j) {
				fr = j;
				to = i;
			} else {
				fr = i;
				to = j;
			}
		} else if (maxi < maxj) {
			fr = i;
			to = j;
		} else {
			fr = j;
			to = i;
		}
		for (int z = 0; z < t.length; z++)
			if (t[z] == fr)
				t[z] = to;
	}

	public static boolean completeTree(int t[]) {
		int i = t[0];
		for (int j = 0; j < t.length; j++)
			if (t[j] != i)
				return false;
		return true;
	}

	public static void genericTree(Graph_1004416_957228 g) {
		int l[][] = new int[g.adjLists.length * 5][2];
		int tree[] = new int[g.adjLists.length];
		double w[] = new double[g.adjLists.length * 5];
		double weightTree = 0;
		tablesWeight(g, l, w);
		for (int i = 0; i < tree.length; i++)
			tree[i] = i;
		for (int i = 0; i < l.length; i++) {
			if (tree[l[i][0]] != tree[l[i][1]]) {
				weightTree += w[i];
				sameTrees(tree, l[i][0], l[i][1]);
			}
			if (completeTree(tree))
				break;
		}
		System.out.println("The generic tree has " + weightTree + " weight!");
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Scanner scan = new Scanner(System.in);
		if (args.length < 1 || args.length > 1) {
			System.out.println("Error: You have to insert only one argument!");
		} else {
			int choosen;
			String fileName = args[0];
			if (fileName.charAt(fileName.length() - 3) != 'b' && fileName.charAt(fileName.length() - 2) != 'i'
					&& fileName.charAt(fileName.length() - 1) != 'n') {
				System.out.println("Error: You insert a wrong file! The file must finish with .bin!");
			} else {
				File fl = new File(fileName);
				FileInputStream fis = new FileInputStream(fl);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Graph_1004416_957228 graph = (Graph_1004416_957228) ois.readObject();
				choosen = choose(scan);
				while (choosen != 4) {
					switch (choosen) {
					case 1:
						kmof2locations(graph,scan);
						break;
					case 2:
						printNlocations(graph,scan);
						break;
					case 3:
						genericTree(graph);
						break;
					}
					choosen = choose(scan);
				}
				System.out.println("Thank you for using our program!");
				scan.close();
			}
		}

	}
}
