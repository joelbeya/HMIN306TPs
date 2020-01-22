package TP2Partie3;

import java.util.HashSet;

public class Car {

    private static HashSet<Car> allCars = new HashSet<>();

    private Person owner;

    @SuppressWarnings("unchecked")
	public static HashSet<Car> getAllCars(){
        return (HashSet<Car>) allCars.clone();
    }
    Car(Person p){
        this();
        this.owner = p;
        owner.addCar(this);
    }



    private Car() {
        allCars.add(this);
    }



    void setOwner(Person person) {
        owner.removeCar(this);
        owner = person;
        owner.addCar(this);
    }

    public Person getOwner() {
        return owner;
    }

    @Override
    public String toString(){
        return "Car (owner: " + owner.getName() + ")";
    }
}