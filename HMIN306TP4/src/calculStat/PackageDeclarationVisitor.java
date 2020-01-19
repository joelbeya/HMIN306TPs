package calculStat;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PackageDeclaration;

public class PackageDeclarationVisitor extends ASTVisitor {
	List<PackageDeclaration> packages = new ArrayList<PackageDeclaration>();
	
	
	
	public boolean visit(PackageDeclaration node) {
		packages.add(node);
		return super.visit(node);
	}
	
	public List<PackageDeclaration> getPackage() {
		// TODO Auto-generated method stub
		return packages;
	}
	
	public int getCount() {
		int count = 0;
		for (PackageDeclaration element : packages) {
			if (element != null) {
				count += packages.size();
			}
		}
		return count;
	}
}
