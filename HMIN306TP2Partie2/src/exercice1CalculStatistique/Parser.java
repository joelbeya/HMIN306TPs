package exercice1CalculStatistique;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;


public class Parser {
	public static final String projectPath = "/home/bj/eclipse-workspace/HMIN306TP2Partie2";
	public static String projectSourcePath = projectPath + "/src";
	public static final String jrePath = "/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar";
	
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
	
	public Parser(String projectPath2) {
		// TODO Auto-generated constructor stub
		projectSourcePath = projectPath2;
	}

	public static void main(String[] args) throws IOException {
		// read java files
		final String projectSourcePath = projectPath + "/src";
		final File folder = new File(projectSourcePath);
		ArrayList<File> javaFiles = listJavaFilesForFolder(folder);
		
		//
		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			// System.out.println(content);

			CompilationUnit parse = parse(content.toCharArray());
		
			info(parse);
		}
		
		
		printInfo();
	}

	private static void printInfo() {
		
		myPercentOfClassWithManyAttributs();
		myPercentOfClassWithManyMethods();
		myPercentOfClassCombined();
		myPercentOfMethodsWithLargestCode();
		// TODO Auto-generated method stub
		System.out.println("=====================================================================");
		System.out.println("\t\tCalculs Statistiques de l'application ");
		System.out.println("=====================================================================\n");
		
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
		System.out.println("Q11/ Les classes qui possèdent plus de X méthodes (la valeur de X est donnée) :\n\t"
				+ "" + classWithUpXMethods);
		System.out.println("Q12/ Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par classe) :\n\t"
				+ "" + percentMethodsWithLargestCode);
		System.out.println("Q13/ Le nombre maximal de paramètres par rapport à toutes les méthodes de l’application :=> "
				+ "" + maximumMethodParameter);
	}

	private static void info(CompilationUnit parse) {
		// TODO Auto-generated method stub
		
		/* Using Class Declaration Visitor */
		printWithTypeDeclarationVisitor(parse);
		
		/* Using Method Declaration Visitor */
		printWithMethodDeclarationVisitor(parse); 
		
		/* Using Package Declaration Visitor */
		printWithPackageDeclarationVisitor(parse);
		
		/* Using Field Acess Visitor */
		printWithFieldAccessVisitor(parse);
	}

	private static void myPercentOfMethodsWithLargestCode() {
		// TODO Auto-generated method stub
		int selectNumber = (nbCLass * 20) / 100;
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
		int selectNumber = (nbCLass * 20) / 100;
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
		int selectNumber = (nbCLass * 20) / 100;
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
		int selectNumber = (nbCLass * 20) / 100;
		int cpt = 0;
		for (SetType setType : classWithManyMethods) {
			if (cpt != selectNumber) {
				percentClassWithManyMethods.add(setType.toString());
				cpt++;
			} 
		}
	}
	
	private static void printWithFieldAccessVisitor(CompilationUnit parse) {
		// TODO Auto-generated method stub
		FieldAccessVisitor visitor = new FieldAccessVisitor();
		parse.accept(visitor);
		nbFields += visitor.getCount();
		
	}

	private static void printWithPackageDeclarationVisitor(CompilationUnit parse) {
		// TODO Auto-generated method stub
		PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();
		parse.accept(visitor);
		totalPackages.add(visitor.getPackage().toString());
		nbTotalPackages = totalPackages.size();
	}

	private static void printWithMethodDeclarationVisitor(CompilationUnit parse) {
		// TODO Auto-generated method stub
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		parse.accept(visitor);
		nbTotalMethods += visitor.getCount();
	}

	private static void printWithTypeDeclarationVisitor(CompilationUnit parse) {
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
	@SuppressWarnings("rawtypes")
	public static CompilationUnit parse(char[] classSource) {
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

	public static ArrayList<File> listJavaFilesForProject(File file) {
		// TODO Auto-generated method stub
		return null;
	}
}