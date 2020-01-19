package exercice1CalculStatistique;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldAccess;


public class FieldAccessVisitor extends ASTVisitor {
	List<FieldAccess> attributes = new ArrayList<FieldAccess>();
	
	public boolean visit(FieldAccess node) {
		attributes.add(node);
		return super.visit(node);
	}
	 
	public List<FieldAccess> getMethods() {
		return attributes;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return attributes.size();
	}
}