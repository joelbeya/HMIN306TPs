package exercice2callGraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import exercice1CalculStatistique.MethodDeclarationVisitor;
import exercice1CalculStatistique.MethodInvocationVisitor;
import exercice1CalculStatistique.Parser;
import exercice1CalculStatistique.TypeDeclarationVisitor;


public class CallGraph {
	/*attributes*/
	private Set<String> vertices = new TreeSet<>();
	private List<Entry<String, String>> arrows = new ArrayList<>();
	public String projectPath;
	
	/*constructors*/
	private CallGraph(String projectPath) {
		this.projectPath = "/home/bj/eclipse-workspace/HMIN306TP2Partie2/src";;
	}
	
	/*methods*/
	public static CallGraph constructGraph(String path, CompilationUnit parse) 
			throws IOException {
		CallGraph graph = new CallGraph(path);
		TypeDeclarationVisitor classVisitor = new TypeDeclarationVisitor();
		
		parse.accept(classVisitor);
		
		for (TypeDeclaration cls: classVisitor.getType()) {
			if(!cls.isInterface()) {
				MethodDeclarationVisitor methodVisitor = new MethodDeclarationVisitor();
				cls.accept(methodVisitor);
				
				for (MethodDeclaration method: methodVisitor.getMethods()) {
					if(method.getBody() != null) {
						String methodName = cls.getName().toString() + "::" + method.getName().toString();
						graph.vertices.add(methodName);
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
								invocationName = cls.getName().toString() + "::" + invocation.getName().toString();
							
							graph.vertices.add(invocationName);
							graph.arrows.add(Map.entry(methodName, invocationName));
						}
					}
				}
			}
		}
		
		return graph;
	}
	
	@SuppressWarnings("static-access")
	public static CallGraph constructGraph(String projectPath) 
			throws IOException {
		CallGraph graph = new CallGraph(projectPath);
		ArrayList<File> javaFiles = Parser.listJavaFilesForProject(new File(projectPath));
		Parser parser = new Parser(projectPath);
		
		for (File fileEntry: javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			CompilationUnit parse = parser.parse(content.toCharArray());
			
			CallGraph partialGraph = CallGraph.constructGraph(fileEntry.getAbsolutePath(), parse);
			graph.vertices.addAll(partialGraph.vertices);
			graph.arrows.addAll(partialGraph.arrows);
		}
		
		return graph;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		result += "Vertices:\n=========\n";
		for (String vertex: this.vertices)
			result += vertex + "\n";
		
		result += "\nArrows:\n======\n";
		for (Entry<String, String> arrow: this.arrows)
			result += arrow.getKey() + " -> " + arrow.getValue() + "\n";
		
		return result;
	}
}
