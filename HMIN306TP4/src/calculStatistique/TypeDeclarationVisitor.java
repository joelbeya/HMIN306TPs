package calculStatistique;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class TypeDeclarationVisitor extends ASTVisitor {
	List<TypeDeclaration> classe = new ArrayList<TypeDeclaration>();

	public boolean visit(TypeDeclaration node) {
		classe.add(node);
		for (TypeDeclaration typeDeclaration : classe) {
			typeDeclaration.accept(this);
		}
		return super.visit(node);
	}
	
	public int getCountClassNumber() {
		// TODO Auto-generated method stub
		return this.classe.size();
	}

}
