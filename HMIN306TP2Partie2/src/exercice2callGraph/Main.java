package exercice2callGraph;

import java.io.IOException;

public class Main {

	public static final String projectPath = "/home/bj/eclipse-workspace/HMIN306TP2Partie2";
	public static final String projectSourcePath = projectPath + "/src";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Here is the dependency graph of "+ projectPath + "'s project.");
		CallGraph graph = CallGraph.constructGraph(projectPath);
		System.out.println(graph);
		System.out.println("Bye bye !");
	}

}
