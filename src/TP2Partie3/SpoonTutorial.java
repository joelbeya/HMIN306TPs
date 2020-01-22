package TP2Partie3;

public class SpoonTutorial {
	
    public static void main(String[] args) {

        Person BEYA = new Person("BEYA");
        Person NTUMBA = new Person("NTUMBA");
        Person JOEL = new Person("JOEL");

        System.out.println("Avant les achats de voitures :");
        for (Person p :  Person.allPersons){
            System.out.println(p);
        }

        Car c1 = new Car(BEYA);
        Car c2 = new Car(BEYA);
        Car c3 = new Car(NTUMBA);
        Car c4 = new Car(JOEL);

        System.out.println("Après les achats de voitures :");
        for (Person p :  Person.allPersons){
            System.out.println(p);
        }

        c1.setOwner(BEYA);
        c2.setOwner(JOEL);
        c3.setOwner(JOEL);
        c4.setOwner(NTUMBA);

        System.out.println("Après les transferts de voitures :");
        for (Person p :  Person.allPersons){
            System.out.println(p);
        }

        System.out.println(Person.getAllPersons());
    }
    
}