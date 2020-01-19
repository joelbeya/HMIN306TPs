package calculStat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;


public class Parser {
	public static final String projectPath = "/home/bj/eclipse-workspace/DesignPatternVisitor";
	public static final String projectSourcePath = projectPath + "/src";
	public static final String jrePath = "/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar";
	public static int totalCLass, codeLinesNumber, totalMethods, avgMethodes, avgFields = 0;
	public static Set<String> totalPackages = new HashSet<String>();
	
	public static void main(String[] args) throws IOException {
		/* read java files */
		final File folder = new File(projectSourcePath);
		ArrayList<File> javaFIles = listJavaFilesForFolder(folder);
		 
		for (File file : javaFIles) {
			String content = FileUtils.readFileToString(file);
//			System.out.println(content); 
			CompilationUnit parse = parse(content.toCharArray());
			
			/* Q1/ Nombre de classes de l'application */
			printCountClassInfo(parse);
			
			/* Q2/ Nombre de lignes de Code de l'application */
			printCountCodeLinesNumberInfo(parse);
			
			/* Q3/ Nombre Total de methodes de l'application */
			printTotalMethodsInfo(parse); 
			
			/* Q4/ Nombre total de packages de l'application */
			printTotalPackagesInfo(parse);
			
			/* Nombre moyen de méthodes par classe */
			printAVGMethodsByClassInfo(parse);
			
			/* Nombre moyen de lignes de code par méthodes */
			printAVGFieldsByClassInfo(parse);
		}
		System.out.println("\tCalculs Statistiques pour une application Orienté-Objet\n");
		System.out.println("Q1/ Nombre Total de Classes de l'application ============================> "
				+ "" + totalCLass);
		System.out.println("Q2/ Nombre de lignes de Code de l'application ===========================> "
				+ "" + "À faire plutard");
		System.out.println("Q3/ Nombre Total de Méthodes de l'application ===========================> "
				+ "" + totalMethods);
		System.out.println("Q4/ Nombre Total de Packages de l'application ===========================> "
				+ "" + totalPackages.size());
		System.out.println("Q5/ Nombre Moyen de Méthodes par classe de l'application ================> "
				+ "" + (avgMethodes / totalCLass));
		System.out.println("Q6/ Nombre moyen de lignes de Code par Méthode de l'application =========> "
				+ "" + "À faire plutard");
		System.out.println("Q7/ Nombre Moyen d'attributs par classe de l'application ================> "
				+ "" + totalMethods / avgFields);
		System.out.println("Q8/ Les 10% des Classes qui possèdent le plus grand nombre de méthodes ==> "
				+ "" + totalMethods / 10);
	}

	private static void printCountCodeLinesNumberInfo(CompilationUnit parse) {
		// TODO Auto-generated method stub
		LineNumberVisitor visitor = new LineNumberVisitor();
		parse.accept(visitor);
		codeLinesNumber += visitor.getNumber();
	}

	private static void printAVGFieldsByClassInfo(CompilationUnit parse) {
		// TODO Auto-generated method stub
		FieldAccessVisitor visitor = new FieldAccessVisitor();
		parse.accept(visitor);
		avgFields += visitor.getAVG();
	}

	private static void printAVGMethodsByClassInfo(CompilationUnit parse) {
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		parse.accept(visitor);
		avgMethodes += visitor.getAVG();
	}

	private static void printCountClassInfo(CompilationUnit parse) {
		TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		parse.accept(visitor);
		totalCLass += visitor.getCount();
	}

	public static void printTotalMethodsInfo(CompilationUnit parse) {
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		parse.accept(visitor);
		totalMethods += visitor.getCount();
	}
 
	private static void printTotalPackagesInfo(CompilationUnit parse) {
		PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
		parse.accept(visitor);
		totalPackages.add(visitor.getPackage().toString());
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
//				 System.out.println(fileEntry.getName());
				javaFiles.add(fileEntry);
			}
		}

		return javaFiles;
	}
}
