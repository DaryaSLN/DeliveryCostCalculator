package org.example;

public class Main {
    public static void main(String[] args) {
        DeliveryCalculator deliveryCalculator = new DeliveryCalculator(40.0,
                CargoDimension.LARGE, false, DeliveryServiceLoad.VERY_HIGH);
        deliveryCalculator.calculateTotalDeliveryCost();
        System.out.println(deliveryCalculator);
    }
}