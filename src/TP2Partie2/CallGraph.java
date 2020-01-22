package TP2Partie2;

import java.io.File;
import java.io.IOException;

import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CallGraph {
	/*
	 * Les sommets et arrêtes du graphe
	 */
	private Set<String> sommets = new HashSet<>();
	private Set<Entry<String, String>> arretes = new HashSet<>();
	
	/*
	 * Construction du graphe
	 */
	public static CallGraph create(CompilationUnit parse) {
		CallGraph graphe = new CallGraph();
		TypeDeclarationVisitor classVisitor = new TypeDeclarationVisitor();
		
		parse.accept(classVisitor);
		for (TypeDeclaration classe: classVisitor.getType()) {
			if(!classe.isInterface()) {
				MethodDeclarationVisitor methodVisitor = new MethodDeclarationVisitor();
				classe.accept(methodVisitor);
				
				for (MethodDeclaration method: methodVisitor.getMethods()) {
					if(method.getBody() != null) {
						String methodName = classe.getName().toString() + "::" + method.getName().toString();
						graphe.sommets.add(methodName);
						MethodInvocationVisitor invocationVisitor = new MethodInvocationVisitor();
						method.accept(invocationVisitor);
						
						for (MethodInvocation invocation: invocationVisitor.getMethods()) {
							Expression expr = invocation.getExpression();
							String invocationName = "";
							
							if (expr != null) {
								ITypeBinding type = expr.resolveTypeBinding();
								
								if (type != null) 
									invocationName = type.getName()+ "::" + invocation.getName().toString();
								else
									invocationName = expr + "::" + invocation.getName().toString();
							}
							
							else
								invocationName = classe.getName().toString() + "::" + invocation.getName().toString();
							
							graphe.sommets.add(invocationName);
							graphe.arretes.add(Map.entry(methodName, invocationName));
						}
					}
				}
			}
		}
		return graphe;
	}

	@SuppressWarnings("static-access")
	public static CallGraph create() throws IOException {
		// TODO Auto-generated method stub
		CallGraph graphe = new CallGraph();
		ArrayList<File> javaFiles = Parser.listJavaFilesForFolder(new File(Parser.projectSourcePath));
		Parser parser = new Parser();
		
		for (File fileEntry: javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			CompilationUnit parse = parser.parse(content.toCharArray());
			
			CallGraph partialGraph = CallGraph.create(parse);
			graphe.sommets.addAll(partialGraph.sommets);
			graphe.arretes.addAll(partialGraph.arretes);
		}
		
		return graphe;
	}
	
	
	@Override
	public String toString() {
		String result = "";
		
		result += "\n=========================================================================================================\n"
				+ "\t\t\t\tLes sommets correspondant aux méthodes:\n"
				+ "=========================================================================================================\n\n";
		for (String sommet: this.sommets)
			result += "\t" + sommet + "\n";
		
		result += "\n=========================================================================================================\n"
				+ "\t\tLes arretes entres representant les liens d'appels entre deux méthodes:\n"
				+ "=========================================================================================================\n\n";
		for (Entry<String, String> arrete: this.arretes)
			result += "[ " + arrete.getKey() + "\t<====>\t" + arrete.getValue() + " ]\n";
		
		return result;
	}
}
