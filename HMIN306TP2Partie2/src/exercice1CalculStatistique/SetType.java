package exercice1CalculStatistique;

public class SetType implements Comparable<SetType>{

	private int number;
	private String value;
	private String name;
	
	@Override
	public int compareTo(SetType o) {
		if(number == o.number){
			return o.value.compareTo(value);
		}
		return o.number - number;
	}
	
	public SetType(String v, int n){
		this.number = n;
		this.value = v;
		this.name = v;
	}
	
	public SetType(String v, int n, String name){
		this.number = n;
		this.value = v;
		this.name = name;
	}
	
	public void setName(String s){
		this.name = s;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	@Override
	public String toString(){
		return "Nom de l'élément : => " + this.name + "\t| \tNombre d'éléments :=> " + this.number + "\n\t";
	}
	
}