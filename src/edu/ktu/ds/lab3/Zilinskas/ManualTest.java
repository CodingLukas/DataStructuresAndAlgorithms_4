package edu.ktu.ds.lab3.Zilinskas;

import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.ParsableHashMap;

import java.util.Locale;

public class ManualTest {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
        executeTest();
    }

    public static void executeTest() {
        Car c1 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c2 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c3 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c4 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c5 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c6 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c7 = new Car("Renault", "Megane", 2001, 20000, 3500);
        Car c8 = new Car("Toyota", "Corolla", 2001, 20000, 8500.8);
        
        // Raktų masyvas
        String[] carsIds = {"TA156", "TA102", "TA178", "TA171", "TA105", "TA106", "TA107", "TA108", "TA109"};
        int id = 0;
        ParsableHashMap<String, Car> carsMap
                = new ParsableHashMap<>(String::new, Car::new, HashType.DIVISION);

        // Reikšmių masyvas
        Car[] cars = {c1, c2, c3, c4, c5, c6, c7, c8};
        for (Car c : cars) {
            carsMap.put(carsIds[id++], c);
        }
        //Mano metodai
        //7
        
        Ks.oun("Pakeičiam visas Lagunas į Meganes");
        carsMap.replaceAll(c1, c7);
        Ks.ounn(carsMap);
        
        //8
        
        Ks.oun("Reikšmių aibė:");
        Ks.oun(carsMap.values());
        
//        Ks.oun("keySet");
//        Ks.oun(carsMap.keySet());
    }
}
