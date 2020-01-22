package TP2Partie2;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tree.SetType;
import tree.ClassTree;

public class TypeDeclarationVisitor extends ASTVisitor {
	public List<TypeDeclaration> type = new ArrayList<TypeDeclaration>();
	public static SortedSet<SetType> classWithManyAttributes = new TreeSet<SetType>();
	public static SortedSet<SetType> classWithManyMethods = new TreeSet<SetType>();
	public static SortedSet<SetType> methodsWithLargestCode = new TreeSet<SetType>();
	public static SortedSet<SetType> maximum = new TreeSet<SetType>();
	public static ClassTree classTree = new ClassTree();
	
	public static int maximumMethodParameter, methodLineCounter = 0;

	int cpt = 0;
	int nblines = 0;
	public boolean visit(TypeDeclaration node) {
		String className = node.getName().toString();
		cpt = node.toString().length() - 
				node.toString().replace(System.getProperty("line.separator"), "").length();
		if (cpt == 0) {
			cpt += node.toString().length() - node.toString().replace("\n", "").length();
		}
		nblines += cpt;
		if (node.getTypes().getClass() != null) {
			
			type.add(node);
			classWithManyAttributes.add(new SetType(className.toString(), node.getFields().length));
			classWithManyMethods.add(new SetType(className.toString(), node.getMethods().length));
		}
		
		for (MethodDeclaration methodDeclaration : node.getMethods()) {
			classTree.addMethodDeclaration(className, methodDeclaration.getName().toString());
			if (methodDeclaration.parameters().size() > maximumMethodParameter)
			{
				maximumMethodParameter = methodDeclaration.parameters().size();

			}
			
			cpt = methodDeclaration.getBody().toString().length()
					- methodDeclaration.getBody().toString().replace(System.getProperty("line.separator"), "").length();

			if (cpt == 0)
				cpt += methodDeclaration.getBody().toString().length()
						- methodDeclaration.getBody().toString().replace("\n", "").length();

			methodLineCounter += cpt;

			methodsWithLargestCode.add(new SetType((methodDeclaration.getName() + 
					" - " + methodDeclaration.getReturnType2() + 
					" - " + methodDeclaration.parameters()),
					cpt, methodDeclaration.getName().toString()));
		}
		
		return super.visit(node);
	}
	
	public List<TypeDeclaration> getType() {
		return type;
	}

	public int getNbClass() {
		// TODO Auto-generated method stub
		return type.size();
	}
	
	public int getnNCodeLines() {
		// TODO Auto-generated method stub
		return nblines;
	}
	
	public SortedSet<SetType> getClassWithManyAttribute()
	{
		return classWithManyAttributes;
	}
	
	public SortedSet<SetType> getClassWithManyMethods()
	{
		return classWithManyMethods;
	}
	
	public SortedSet<SetType> getMethodsWithLargestCode()
	{
		return methodsWithLargestCode;
	}
	
	public int getMaximumMethodParameter() {
		return maximumMethodParameter;
	}
}
