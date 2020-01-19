package calculStat;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldAccess;


public class FieldAccessVisitor extends ASTVisitor {
	List<FieldAccess> methods = new ArrayList<FieldAccess>();
	
	public boolean visit(FieldAccess node) {
		methods.add(node);
		return super.visit(node);
	}
	 
	public List<FieldAccess> getMethods() {
		return methods;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return methods.size();
	}

	public int getAVG() {
		// TODO Auto-generated method stub
		return getCount();
	}
}
