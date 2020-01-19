package calculStat;

//import java.lang.instrument.ClassDefinition;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class TypeDeclarationVisitor extends ASTVisitor {
	List<TypeDeclaration> type = new ArrayList<TypeDeclaration>();
	public boolean visit(TypeDeclaration node) {
		if (node.getTypes().getClass() != null) {
			type.add(node);
		}
		return super.visit(node);
	}
	
	public List<TypeDeclaration> getType() {
		return type;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return type.size();
	}
}