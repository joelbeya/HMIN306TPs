package TP2Partie2;

import java.io.File;
import java.io.IOException;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

import java.util.SortedSet;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import tree.SetType;

public class Parser {	
	/*
	 * Vous pouvez modifier ces paramètres pour y mettre ceux de votre projet
	 */
	public static final String projectPath = "/home/bj/eclipse-workspace/HMIN306TP2Partie2";
	public static String projectSourcePath = projectPath + "/src";
	public static final String jrePath = "/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar";

	/*
	 * Attributs de classes à ne pas modifier
	 */
	public static int nbCLass, nbFields, nbCodeLines, nbTotalMethods, nbTotalPackages = 0;
	public static int X = 3;
	
	public static Set<String> totalPackages = new HashSet<String>();
	public static SortedSet<String> percentClassWithManyAttributes = new TreeSet<String>();
	public static SortedSet<String> percentClassWithManyMethods = new TreeSet<String>();
	public static SortedSet<String> percentOfClassCombined = new TreeSet<String>();
	public static SortedSet<String> classWithUpXMethods = new TreeSet<String>();
	public static SortedSet<String> percentMethodsWithLargestCode = new TreeSet<String>();
	
	public static SortedSet<SetType> methodsWithLargestCode = new TreeSet<SetType>();
	public static SortedSet<SetType> classWithManyAttributes = new TreeSet<SetType>();
	public static SortedSet<SetType> classWithManyMethods = new TreeSet<SetType>();
	
	public static int maximumMethodParameter = 0;
	
	public static void main(String[] args) throws IOException {
		// read java files
		final File folder = new File(projectSourcePath);
		ArrayList<File> javaFiles = listJavaFilesForFolder(folder);
		
		//
		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			// System.out.println(content);

			CompilationUnit parse = parse(content.toCharArray());

			// print methods info
			printASTInfo(parse);			
		}
		// Affichage des resultats des métriques
		displayASTInfo();

		// afficher le graphe d'appel de méthode
		displayCallGraph();
	}

	private static void displayCallGraph() throws IOException {
		// TODO Auto-generated method stub
		System.out.println("=========================================================================================================");
		System.out.println("\t\tExercice 2 / Construction du graphe d'appel de l'application");
		System.out.println("=========================================================================================================\n");
		
		CallGraph graphe = CallGraph.create();
		System.out.println(graphe);
	}

	private static void displayASTInfo() {
		// TODO Auto-generated method stub
		/*
		 * Métrique de 10% pourcent d'élements sur des paramètres données
		 */
		metriqueDeDixPourCent();
		
		// TODO Auto-generated method stub
		System.out.println("=========================================================================================================");
		System.out.println("\tExercice 1 / Calculs Statistiques de l'application ");
		System.out.println("=========================================================================================================\n");
		
		System.out.println("Q1/ Nombre Total de Classes  :=======================================> "
				+ "" + nbCLass);
		System.out.println("Q2/ Nombre de lignes de Code :=======================================> "
				+ "" + nbCodeLines);
		System.out.println("Q3/ Nombre Total de Méthodes :=======================================> "
				+ "" + nbTotalMethods);
		System.out.println("Q4/ Nombre Total de Packages :=======================================> "
				+ "" + nbTotalPackages);
		System.out.println("Q5/ Nombre Moyen de Méthodes par classe de l'application :===========> "
				+ "" + (nbTotalMethods / nbCLass));
		System.out.println("Q6/ Nombre moyen de lignes de Code par Méthode de l'application :====> "
				+ "" + (nbCodeLines / nbTotalMethods));
		System.out.println("Q7/ Nombre Moyen d'attributs par classe de l'application :===========> "
				+ "" + (nbFields / nbCLass));
		System.out.println("Q8/ Les 10% des Classes qui possèdent le plus grand nombre de méthodes :\n\t"
				+ "" + percentClassWithManyMethods);
		System.out.println("Q9/ Les 10% des Classes qui possèdent le plus grand nombre d'attributs :\n\t"
				+ "" + percentClassWithManyAttributes);
		System.out.println("Q10/ Les classes qui font partie en même temps des deux catégories précédentes :\n\t"
				+ "" + percentOfClassCombined);
		System.out.println("Q11/ Les classes qui possèdent plus de X méthodes (la valeur de X choisi ici est 3) :\n\t"
				+ "" + classWithUpXMethods);
		System.out.println("Q12/ Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par classe) :\n\t"
				+ "" + percentMethodsWithLargestCode);
		System.out.println("Q13/ Le nombre maximal de paramètres par rapport à toutes les méthodes de l’application :=> "
				+ "" + maximumMethodParameter + "\n");
	}

	private static void metriqueDeDixPourCent() {
		// TODO Auto-generated method stub
		myPercentOfClassWithManyAttributs();
		myPercentOfClassWithManyMethods();
		myPercentOfClassCombined();
		myPercentOfMethodsWithLargestCode();
	}


	private static void printASTInfo(CompilationUnit parse) {
		// TODO Auto-generated method stub
		
		// print type info
		printTypeInfo(parse);
		
		// print methods info
		printMethodInfo(parse);
		
		// print package info
		printPackageInfo(parse);
		
		// print variables info
		printVariableInfo(parse);
	}

	// read all java files from specific folder
	public static ArrayList<File> listJavaFilesForFolder(final File folder) {
		ArrayList<File> javaFiles = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				javaFiles.addAll(listJavaFilesForFolder(fileEntry));
			} else if (fileEntry.getName().contains(".java")) {
				// System.out.println(fileEntry.getName());
				javaFiles.add(fileEntry);
			}
		}

		return javaFiles;
	}

	// create AST
	static CompilationUnit parse(char[] classSource) {
		ASTParser parser = ASTParser.newParser(AST.JLS4); // java +1.6
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		parser.setBindingsRecovery(true);
 
		
		
		Map<?, ?> options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
 
		parser.setUnitName("");
 
		String[] sources = { projectSourcePath }; 
		String[] classpath = {jrePath};
 
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
		parser.setSource(classSource);
		
		return (CompilationUnit) parser.createAST(null); // create and parse
	}
	
	private static void printVariableInfo(CompilationUnit parse) {
		// TODO Auto-generated method stub
		FieldAccessVisitor visitor = new FieldAccessVisitor();
		parse.accept(visitor);
		nbFields += visitor.getCount();
		
	}

	private static void printPackageInfo(CompilationUnit parse) {
		// TODO Auto-generated method stub
		PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
		parse.accept(visitor);
		totalPackages.add(visitor.getPackage().toString());
		nbTotalPackages = totalPackages.size();
	}

	private static void printMethodInfo(CompilationUnit parse) {
		// TODO Auto-generated method stub
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		parse.accept(visitor);
		nbTotalMethods += visitor.getCount();
	}

	private static void printTypeInfo(CompilationUnit parse) {
		// TODO Auto-generated method stub
		TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
		parse.accept(visitor);
		nbCodeLines += visitor.getnNCodeLines();
		nbCLass += visitor.getNbClass();
		maximumMethodParameter += visitor.getMaximumMethodParameter();
		for (SetType setType : visitor.getClassWithManyAttribute()) {
			classWithManyAttributes.add(setType);
		}
		for (SetType setType : visitor.getClassWithManyMethods()) {
			classWithManyMethods.add(setType);
		}
		for (SetType setType : visitor.getMethodsWithLargestCode()) {
			methodsWithLargestCode.add(setType);
		}
	}
	
	private static void myPercentOfMethodsWithLargestCode() {
		// TODO Auto-generated method stub
		int selectNumber = (nbCLass * 10) / 100;
		int cpt = 0;
		for (SetType setType : methodsWithLargestCode) {
			if (cpt != selectNumber) {
				percentMethodsWithLargestCode.add(setType.toString());
				cpt++;
			}
		}
	}
	
	private static void myPercentOfClassCombined() {
		// TODO Auto-generated method stub
		int selectNumber = (nbCLass * 10) / 100;
		int cpt = 0;
		for (SetType setType : classWithManyMethods) {
			for (SetType setType2 : classWithManyAttributes) {
				if (cpt != selectNumber && setType.getName().equals(setType2.getName())) {
					percentOfClassCombined.add(setType.toString());
					cpt++;
				}	
			}
		}
	}
	
	private static void myPercentOfClassWithManyAttributs() {
		// TODO Auto-generated method stub
		int selectNumber = (nbCLass * 10) / 100;
		int cpt = 0;
		for (SetType setType : classWithManyAttributes) {
			if (cpt != selectNumber) {
				percentClassWithManyAttributes.add(setType.toString());
				cpt++;
			}
			if (setType.getNumber() > X) {
				classWithUpXMethods.add(setType.toString());
			}
		}
	}

	private static void myPercentOfClassWithManyMethods() {
		// TODO Auto-generated method stub
		int selectNumber = (nbCLass * 10) / 100;
		int cpt = 0;
		for (SetType setType : classWithManyMethods) {
			if (cpt != selectNumber) {
				percentClassWithManyMethods.add(setType.toString());
				cpt++;
			} 
		}
	}
}
