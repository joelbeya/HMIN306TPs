package calculStat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class Parser {
	public static final String projectPath = "/home/bj/eclipse-workspace/HMIN306TP2";
	public static final String projectSourcePath = projectPath + "/src";
	public static final String jrePath = "/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar";
	
	public static void main(String[] args) throws IOException {
		/* read java files */
		final File folder = new File(projectSourcePath);
		ArrayList<File> javaFIles = listJavaFilesForFolder(folder);
		 
		for (File file : javaFIles) {
			String content = FileUtils.readFileToString(file);
//			System.out.println(content); 
			CompilationUnit parse = parse(content.toCharArray());
			
			/* Nombre de classes de l'application */
			// printCountClassInfo(parse);
			
			/* Nombre Total de methodes de l'application */
			// printTotalMethodsInfo(parse); 
			
			/* Nombre total de packages */
			printTotalPackagesInfo(parse);
		}
	}


	private static void printTotalPackagesInfo(CompilationUnit parse) {
		// TODO Auto-generated method stub
		PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
		parse.accept(visitor);
		System.out.println(visitor.getCount());
	}

	static int totalClass = 0;
	private static void printCountClassInfo(CompilationUnit parse) {
		// TODO Auto-generated method stub
		TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		parse.accept(visitor);
		
		int count = 0;
		for (TypeDeclaration classes : visitor.getType()) {
			if (classes.getTypes().getClass() != null) {
				count++;
				totalClass += count;
			}
			System.out.println("Class N° : " + count + "\t"
			+ "Class name: " + classes.getName());
		}
		System.out.println("Nombre de Classes de l'application : " + totalClass);
	}

	// navigate method information
	static int total = 0;
	public static void printTotalMethodsInfo(CompilationUnit parse) {
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		parse.accept(visitor);

		int count = 0;
		for (MethodDeclaration method : visitor.getMethods()) {
			if ((method) != null) {
				count++;
				total += count;
			}
//			System.out.println("Méthode N° : " + count + "\t"
//					+ "Method name: " + method.getName());
		}
		System.out.println("Nombre total de Methodes de l'application : " + total);
	}
 
 
	//create AST
	@SuppressWarnings("rawtypes")
	private static CompilationUnit parse(char[] classSource) {
		ASTParser parser = ASTParser.newParser(AST.JLS4); // java +1.6
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		parser.setBindingsRecovery(true);
 
		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
 
		parser.setUnitName("");
 
		String[] sources = { projectSourcePath }; 
		String[] classpath = {jrePath};
 
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
		parser.setSource(classSource);
		
		return (CompilationUnit) parser.createAST(null); // create and parse
	  }
	// read all java files from specific folder
	public static ArrayList<File> listJavaFilesForFolder(final File folder) {
		ArrayList<File> javaFiles = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				javaFiles.addAll(listJavaFilesForFolder(fileEntry));
			} else if (fileEntry.getName().contains(".java")) {
				 System.out.println(fileEntry.getName());
				javaFiles.add(fileEntry);
			}
		}

		return javaFiles;
	}
}
