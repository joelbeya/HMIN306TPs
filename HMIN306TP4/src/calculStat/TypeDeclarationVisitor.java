package calculStat;

//import java.lang.instrument.ClassDefinition;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
//import org.eclipse.jdt.core.dom.CLassDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class TypeDeclarationVisitor extends ASTVisitor {
	List<TypeDeclaration> classes = new ArrayList<TypeDeclaration>();
	public boolean visit(TypeDeclaration node) {
		classes.add(node);
		return super.visit(node);
	}
	
	public List<TypeDeclaration> getType() {
		return classes;
	}
}