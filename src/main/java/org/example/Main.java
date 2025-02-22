package org.example;

public class Main {
    public static void main(String[] args) {
        DeliveryCalculator deliveryCalculator = new DeliveryCalculator(40.0,
                null, false, DeliveryServiceLoad.VERY_HIGH);
        System.out.println(deliveryCalculator);
        // добавить проверки на null, отрицательные, 0
        // обработать максимальные значения
        // вынести в отдельные методы расчёты по каждому параметру.
    }
}