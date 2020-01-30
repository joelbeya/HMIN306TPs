package NodeEntities;

import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassEntity extends TypeEntity {

    private TypeDeclaration typeDeclaration;

    private PackageEntity packageDeclaration;


    public ClassEntity(String n, PackageEntity currentPackage, TypeDeclaration node) {
        super(n);
        setPackageDeclaration(currentPackage);
        setTypeDeclaration(node);
    }

    public boolean equals(Object o){
        return o instanceof ClassEntity && ((ClassEntity)o).getName().equals(getName());
    }

	public TypeDeclaration getTypeDeclaration() {
		return typeDeclaration;
	}

	public void setTypeDeclaration(TypeDeclaration typeDeclaration) {
		this.typeDeclaration = typeDeclaration;
	}

	public PackageEntity getPackageDeclaration() {
		return packageDeclaration;
	}

	public void setPackageDeclaration(PackageEntity packageDeclaration) {
		this.packageDeclaration = packageDeclaration;
	}

}
