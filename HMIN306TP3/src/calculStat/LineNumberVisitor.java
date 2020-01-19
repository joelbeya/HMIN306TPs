package calculStat;

import java.util.ArrayList;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class LineNumberVisitor extends ASTVisitor {
	List<CompilationUnit> lines = new ArrayList<CompilationUnit>();
	
	int cpt = 0;
	public boolean visit(CompilationUnit node) {
		lines.add(node);
		return super.visit(node);
	}
	
	public int getNumber() {
		for (CompilationUnit compilationUnit : lines) {
			cpt = compilationUnit.getLineNumber(compilationUnit.getStartPosition()) - 1;
		}
		return cpt;
	}
}
