package calculStatistique;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class TypeDeclarationVisitor extends ASTVisitor {
	List<TypeDeclaration> types = new ArrayList<TypeDeclaration>();

	public int count;
		
	public int getCount() {
		int cpt = this.count;
		this.count = 0;
		return cpt;
	}
	
	public boolean visit(TypeDeclaration node) {
		types.add(node);
		for (TypeDeclaration typeDeclaration : types) {
			if (typeDeclaration.getClass() != null) {
				this.count += types.size();
			}			
		}
		return super.visit(node);
	}
	
	public List<TypeDeclaration> getTypes() {
		return types;
	}
}
